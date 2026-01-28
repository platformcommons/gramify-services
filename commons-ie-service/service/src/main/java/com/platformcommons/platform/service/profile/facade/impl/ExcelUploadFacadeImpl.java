package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.dto.commons.UoMValueDTO;
import com.platformcommons.platform.service.person.dto.ptld.GlobalRefDataDTO;
import com.platformcommons.platform.service.person.dto.ptld.UserContactDTO;
import com.platformcommons.platform.service.person.dto.ptld.UserDTO;
import com.platformcommons.platform.service.person.dto.ptld.UserWrapperDTO;
import com.platformcommons.platform.service.profile.application.utility.PortfolioLineItemDTOMap;
import com.platformcommons.platform.service.profile.domain.vo.*;
import com.platformcommons.platform.service.profile.dto.*;
import com.platformcommons.platform.service.profile.facade.ExcelUploadFacade;
import com.platformcommons.platform.service.profile.facade.IeFacade;
import com.platformcommons.platform.service.profile.facade.client.DataSetClient;
import com.platformcommons.platform.service.profile.facade.client.TldClient;
import com.platformcommons.platform.service.profile.facade.client.WorkNodeClient;
import com.platformcommons.platform.service.sdk.asset.dto.AssetDTO;
import com.platformcommons.platform.service.sdk.asset.dto.AssetSpecsDTO;
import com.platformcommons.platform.service.sdk.asset.facade.AssetFacade;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.PoiExcelHelper;
import com.platformcommons.platform.service.sdk.portfolio.domain.repo.PortfolioItemRepository;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioDTO;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioLineItemDTO;
import com.platformcommons.platform.service.sdk.portfolio.dto.PortfolioProductLineItemDTO;
import com.platformcommons.platform.service.sdk.portfolio.facade.PortfolioFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ExcelUploadFacadeImpl implements ExcelUploadFacade {
    private final PortfolioItemRepository portfolioItemRepository;


    private final IeFacade ieFacade;
    private final AssetFacade assetFacade;
    private final PortfolioFacade portfolioFacade;
    private final DataSetClient dataSetClient;
    private final TldClient tldClient;
    private final WorkNodeClient workNodeClient;

    private final String FARMER_ADDRESS = "ADDRESS_TYPE.PERMANENT";
    private final String ASSET_SPEC_LAND_HOLDER_TYPE = "asset.spec.land.land_holder_type";
    private final String ASSET_SPEC_LAND_NAME = "asset.spec.land.land_name";
    private final String GET_WORKNODE_ID_DEFAULT_CENTER = "DATASET.IE_SERVICE.GET_WORKNODE_ID_FOR_DEFAULT_CENTER";
    private final String GET_USER_ROLES_MARKIFY = "GET_USER_ROLES_MARKIFY";
    private final String GET_WORK_NODE_ID_DATASET = "DATASET.IE_SERVICE.GET_WORK_NODE_IDS";
    private final String GET_GENERIC_PRODUCT_MAP = "DATASET.IE_SERVICE.GET_GENERIC_PRODUCT_MAP";
    private final String GET_IE_ID_MAP = "DATASET.IE_SERVICE.GET_IE_ID_MAP";
    private final String LAND_OWNED_IRRIGATED = "LAND_OWNED_IRRIGATED";
    private final String LAND_LEASED_IRRIGATED = "LAND_LEASED_IRRIGATED";
    private final String LAND_LEASED_RAIN_FED = "LAND_LEASED_RAIN_FED";
    private final String LAND_OWNED_RAIN_FED = "LAND_OWNED_RAIN_FED";
    private final String ASSET_FUNCTIONALITY_IRRIGATED = "ASSET_FUNCTIONALITY.IRRIGATED";
    private final String ASSET_FUNCTIONALITY_RAIN_FED = "ASSET_FUNCTIONALITY.RAIN_FED";
    private final String ASSET_SUBCATEGORY_LAND_OWNED = "ASSET_SUBCATEGORY.LAND_OWNED";
    private final String ASSET_SUBCATEGORY_LAND_LEASED = "ASSET_SUBCATEGORY.LAND_LEASED";
    private final String ASSET_CATEGORY_LAND = "ASSET_CATEGORY.LAND";
    private final String ENTITY_TYPE_IE = "ENTITY_TYPE.IE";
    private final String UOM_ACRE = "ACRE";
    private final String IE_TYPE_FARMER = "IE_TYPE.FARMER";
    private final String CREATION_MODE_EXCEL = "CREATION_MODE.EXCEL";


    @Override
    public void uploadIE(MultipartFile file) throws Exception {
        if (file == null) throw new InvalidInputException("File can not be null");
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<IEExcelVo> ieExcelVos = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), IEExcelVo.class);
        ieFacade.saveAll(getIeList(ieExcelVos));
    }

    @Override
    public void uploadIEV2(MultipartFile file) throws Exception {
        if (file == null) throw new InvalidInputException("File can not be null");
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<IEExcelVoV2> ieExcelVos = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), IEExcelVoV2.class);
        ieFacade.saveAll(getIeListV2(ieExcelVos));
    }


    @Override
    public void uploadAsset(MultipartFile file) throws Exception {
        if (file == null) throw new InvalidInputException("File can not be null");
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<AssetExcelVO> assetExcelVOS = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), AssetExcelVO.class);
        List<AssetDTO> assetDTOS = getAssetList(assetExcelVOS);
        assetDTOS.forEach(assetFacade::createAsset);
    }

    @Override
    public void uploadPortfolio(MultipartFile file) throws Exception {
        if (file == null) throw new InvalidInputException("File can not be null");
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<CropPlanExcelVo> cropPlanExcelVos = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), CropPlanExcelVo.class);
        Map<String, Set<PortfolioLineItemDTO>> portfolioLineItems = getPortfolioLineItemList(cropPlanExcelVos);
        Map<String, Long> map = getIeIdMap(portfolioLineItems.keySet());
        for (Map.Entry<String, Set<PortfolioLineItemDTO>> entrySet : portfolioLineItems.entrySet()) {
            String ieSerialNumber = entrySet.getKey();
            Long actorId = map.get(ieSerialNumber);
            if(actorId==null) actorId=Long.parseLong(ieSerialNumber);
            Set<PortfolioLineItemDTO> portfolioLineItemDTOS = entrySet.getValue();
            PortfolioDTO portfolioDTO = PortfolioDTO.builder()
                    .ownerEntityId(actorId)
                    .ownerEntityType(ENTITY_TYPE_IE)
                    .portfolioLineItemList(new HashSet<>())
                    .build();
            portfolioDTO = portfolioFacade.createPortfolio(portfolioDTO);
            portfolioDTO.getPortfolioLineItemList().addAll(portfolioLineItemDTOS);
            portfolioFacade.createPortfolioLineItem(actorId, ENTITY_TYPE_IE, portfolioDTO);
        }
    }

    @Override
    public void uploadABA(MultipartFile file) throws Exception {
        if (file == null) throw new InvalidInputException("File can not be null");
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        List<ABAExcelVo> abaExcelVos = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), ABAExcelVo.class);
        Long workNodeId = getWorkNodeId();
        final String token = PlatformSecurityUtil.getToken();
        final String roleCode = String.format("role.%s.aba", PlatformSecurityUtil.getCurrentTenantLogin());
        abaExcelVos.parallelStream()
                .forEach(abaExcelVo -> {
                    try {
                        UserWrapperDTO userWrapperDTO = tldClient.postAba(getUserWrapperDto(abaExcelVo, roleCode), token);
                        Long userId = userWrapperDTO.getUserDTO().getId();
                        Long roleId = getRoleId(userId, token);
                        workNodeClient.mapABAToWorkForce(workNodeId, getABAWorkForceMapDTO(roleId, workNodeId, userId), token);
                    } catch (Exception e) {
                        log.error("Aba Upload Failed for excel row no {} cause {}", abaExcelVo.getRowNumber(), e.getMessage());
                    }
                });
    }

    @Override
    public void uploadIEV3(MultipartFile file) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            List<IEExcelVoV3> excelData = PoiExcelHelper.sheetToPOJO(workbook.getSheetAt(0), IEExcelVoV3.class);
            List<IeDTO> ieList = getIeList3(excelData);
            ieFacade.saveAllWithoutQR(ieList);
        } catch (Exception e) {
            log.error("Error while uploading IE data from Excel", e);
            throw new RuntimeException("Failed to upload IE data from Excel", e);
        }
    }

    private List<IeDTO> getIeList3(List<IEExcelVoV3> excelVos) {
        Set<String> serialNumbers = Collections.newSetFromMap(new ConcurrentHashMap<>());
        Queue<IeDTO> ieQueue = new ConcurrentLinkedQueue<>();

        excelVos.parallelStream().forEach(row -> {
            try {
                String serial = row.getSerialNumber();
                if (serial != null && !serialNumbers.add(serial)) {
                    throw new DuplicateResourceException("Duplicate SERIAL_NUMBER found: " + serial);
                }

                Set<MetaDataDTO> metaDataList = new HashSet<>();
                Optional.ofNullable(row.getFarmerDropOut())
                        .map(String::trim)
                        .filter(val -> !val.isEmpty())
                        .map(val -> "DROP_OUT." + val.toUpperCase())
                        .ifPresent(mappedVal -> metaDataList.add(
                                MetaDataDTO.builder()
                                        .metaKey("KEY_DROP_OUT")
                                        .metaValue(mappedVal)
                                        .build()
                        ));


                ContactDTO contactDTO = null;
                if(row.getMobileNumber()!=null && !row.getMobileNumber().trim().isEmpty()) {
                    contactDTO = ContactDTO.builder()
                            .contactType("CONTACT_TYPE.MOBILE")
                            .contactValue(row.getMobileNumber())
                            .isVerified(false)
                            .build();
                }


                String gender =  row.getGender() == null ? null : row.getGender().trim().toUpperCase();

                Set<IeSubTypeDTO> subTypes = null;
                if(row.getFarmerType()!=null && !row.getFarmerType().trim().isEmpty()) {
                    subTypes = Optional.of(row.getFarmerType())
                            .map(type -> Collections.singleton(
                                    IeSubTypeDTO.builder()
                                            .code(type.trim().toUpperCase())
                                            .build()))
                            .orElse(Collections.emptySet());
                }


                PersonDTO person = PersonDTO.builder()
                        .personProfile(PersonProfileDTO.builder()
                                .displayName(row.getFarmerName())
                                .firstName(row.getFarmerName())
                                .gender(gender)
                                .age(row.getAge()!=null ? ( row.getAge() == 0L ? null : row.getAge()) : null)
                                .metaDataList(metaDataList)
                                .build())
                        .contactList(contactDTO!=null ? Collections.singleton(contactDTO): Collections.emptySet())
                        .build();

                IeDTO ieDTO = IeDTO.builder()
                        .uuid(row.getFarmerUniqueIdentifier())
                        .ieUniqueIdentifier(row.getFarmerUniqueIdentifier())
                        .onBoardDateTime(row.getOnBoardedDate())
                        .taggedToServiceAreaCode("LFS_CODE."+row.getServiceAreaCode())
                        .taggedToServiceAreaType("GREF.LFS_CODE")
                        .ieSubTypeList(subTypes!=null ? subTypes : Collections.emptySet())
                        .taggedToWorkforceId(StringUtils.isEmpty(row.getWorkforceId()) ? null : Long.parseLong(row.getWorkforceId()))
                        .taggedToWorknodeId(StringUtils.isEmpty(row.getWorknodeId()) ? null: Long.parseLong(row.getWorknodeId()))
                        .person(person)
                        .creationMode("CREATION_MODE_EXCEL")
                        .ieType("IE_TYPE.FARMER")
                        .ieSubType(row.getFarmerType()!=null && !row.getFarmerType().trim().isEmpty() ? row.getFarmerType().trim() : null)
                        .build();

                ieQueue.add(ieDTO);

            } catch (Exception e) {
                log.error("Error while processing row number {}", row.getRowNumber(), e);
            }
        });

        return new ArrayList<>(ieQueue);
    }


    private Map<String, Object> getABAWorkForceMapDTO(Long roleId, Long workNodeId, Long userId) {

        Map<String, Object> aBAWorkForceMapDTO = new HashMap<>();
        aBAWorkForceMapDTO.put("accessType", "WRITE");
        aBAWorkForceMapDTO.put("context", "MARKIFY_V2");
        aBAWorkForceMapDTO.put("id", 0L);
        aBAWorkForceMapDTO.put("roleId", roleId);
        aBAWorkForceMapDTO.put("userId", userId);
        aBAWorkForceMapDTO.put("worknodeId", workNodeId);

        Map<String, Object> workNodeDTO = new HashMap<>();
        workNodeDTO.put("id", workNodeId);
        aBAWorkForceMapDTO.put("worknodeDTO", workNodeDTO);
        return aBAWorkForceMapDTO;
    }

    private UserWrapperDTO getUserWrapperDto(ABAExcelVo abaExcelVo, String roleCode) {
        return UserWrapperDTO.builder()
                .userDTO(getUserDTO(abaExcelVo))
                .userContactDTOList(getContact(abaExcelVo.getContact()))
                .functionName("vms")
                .password(abaExcelVo.getPassword())
                .confirmPassword(abaExcelVo.getPassword())
                .userRoleCode(roleCode)
                .build();
    }

    private UserDTO getUserDTO(ABAExcelVo abaExcelVo) {
        return UserDTO.builder()
                .firstName(abaExcelVo.getFirstName())
                .lastName(abaExcelVo.getLastName())
                .login(abaExcelVo.getContact())
                .person(getPerson(abaExcelVo))
                .build();
    }

    private com.platformcommons.platform.service.person.dto.ptld.PersonDTO getPerson(ABAExcelVo abaExcelVo) {
        return com.platformcommons.platform.service.person.dto.ptld.PersonDTO
                .builder()
                .id(0)
                .personContacts(getContact(abaExcelVo.getContact()))
                .personProfile(com.platformcommons.platform.service.person.dto.ptld.PersonProfileDTO.builder()
                        .id(0)
                        .firstName(abaExcelVo.getFirstName())
                        .build())
                .build();
    }

    private List<UserContactDTO> getContact(String contact) {
        return Collections.singletonList(UserContactDTO.builder()
                .id(0)
                .contact(com.platformcommons.platform.service.person.dto.ptld.ContactDTO.builder()
                        .contactValue(contact)
                        .id(0)
                        .verified(false)
                        .contactType(GlobalRefDataDTO.builder().dataCode("CONTACT_TYPE.MOBILE").build())
                        .build())
                .primaryContact(false)
                .build());
    }


    private Long getRoleId(Long id, String token) {
        String params = String.format("USER_ID=%s", id);
        return ((Number) dataSetClient.executeQueryV3(GET_USER_ROLES_MARKIFY, params, 0, 0, token).get(0).get("roleMapId")).longValue();
    }

    private Long getWorkNodeId() {
        return ((Number) dataSetClient.executeQueryV3(GET_WORKNODE_ID_DEFAULT_CENTER, null, 0, 0, PlatformSecurityUtil.getToken()).get(0).get("id")).longValue();
    }

    private Map<String, Map<String, Object>> getGenericProductMap(List<CropPlanExcelVo> cropPlanExcelVos) {
        String params = String.format("IN_PARAM_PRODUCT_CODE=%s", String.join(",", cropPlanExcelVos.stream().map(CropPlanExcelVo::getGenericProductVarietyCode).collect(Collectors.toSet())));
        return dataSetClient.executeQueryV3(GET_GENERIC_PRODUCT_MAP, params, 0, 0, PlatformSecurityUtil.getToken())
                .stream()
                .collect(Collectors.toMap(map -> map.get("code").toString(), map -> {
                    HashMap<String, Object> product = new HashMap<>();
                    product.put("genericProduct", map.get("genericProduct"));
                    product.put("genericProductVariety", map.get("genericProductVariety"));
                    product.put("genericProductCode", map.get("genericProductCode"));
                    return product;
                }));
    }

    private Map<String, Set<PortfolioLineItemDTO>> getPortfolioLineItemList(List<CropPlanExcelVo> cropPlanExcelVos) {
        PortfolioLineItemDTOMap portfolioLineItemDTOMap = new PortfolioLineItemDTOMap();
        Map<String, Map<String, Object>> map = getGenericProductMap(cropPlanExcelVos);
        for (CropPlanExcelVo cropPlanExcelVo : cropPlanExcelVos) {
            try {
                Map<String, Object> productData = map.get(cropPlanExcelVo.getGenericProductVarietyCode());
                final Long genericProductId = productData.get("genericProduct") == null ? null : ((Number) productData.get("genericProduct")).longValue();
                final String genericProductCode = productData.get("genericProductCode") == null ? null : productData.get("genericProductCode").toString();
                final Long genericProductVarietyId = productData.get("genericProductVariety") == null ? null : ((Number) productData.get("genericProductVariety")).longValue();
                final String genericProductVarietyCode = cropPlanExcelVo.getGenericProductVarietyCode();
                portfolioLineItemDTOMap.createPortfolioLineItemIfNotPresent(cropPlanExcelVo);
                PortfolioProductLineItemDTO portfolioProductLineItemDTO = PortfolioProductLineItemDTO.builder()
                        .genericProductId(genericProductId)
                        .genericProductCode(genericProductCode)
                        .genericProductVarietyCode(genericProductVarietyCode)
                        .genericProductVarietyId(genericProductVarietyId)
                        .assetProportion(UoMValueDTO.builder().uoM(UOM_ACRE).value(cropPlanExcelVo.getAcersUsed()).build())
                        .harvestStartDate(cropPlanExcelVo.getExpectedHarvestDate())
                        .expectedOutput(UoMValueDTO.builder().uoM(cropPlanExcelVo.getTotalQuantityUOM()).value(cropPlanExcelVo.getTotalQuantity()).build())
                        .surplusOutput(UoMValueDTO.builder().uoM(cropPlanExcelVo.getIntentToSellUOM()).value(cropPlanExcelVo.getIntentToSell()).build())
                        .build();
                portfolioLineItemDTOMap.add(cropPlanExcelVo.getFarmerSerialNumber(),cropPlanExcelVo.getLandSerialNumber(),portfolioProductLineItemDTO);
            }
            catch (Exception e){
                log.error("Crop Plan Upload Failed for excel row number {}",cropPlanExcelVo.getRowNumber(),e);
            }
        }
        return portfolioLineItemDTOMap.getResult();
    }

    private Optional<AssetDTO> buildAssetDTO(AssetExcelVO vo, String assetSubCategory, String assetFunctionality, String owningEntityId, String landCode) {
        Double value=  vo.getAcre();
        String name =  vo.getName();
        if (value == null) return Optional.empty();
        HashSet<AssetSpecsDTO> specs = new HashSet<>();
        if (name != null) {
            specs.add(AssetSpecsDTO.builder().id(null).specName(ASSET_SPEC_LAND_NAME).specValue(name).build());
        }
        if(vo.getLandHolderType()!=null && !vo.getLandHolderType().trim().isEmpty()){
            specs.add(AssetSpecsDTO.builder().id(null).specName(ASSET_SPEC_LAND_HOLDER_TYPE).specValue(vo.getLandHolderType()).build());
        }
        return Optional.of(AssetDTO.builder()
                .id(null)
                .assetCode(landCode)
                .assetCategory(getRefDataDTO(ASSET_CATEGORY_LAND))
                .assetSubCategory(getRefDataDTO(assetSubCategory))
                .assetFunctionality(getRefDataDTO(assetFunctionality))
                .totalQuantity(UoMValueDTO.builder().value(value).uoM(UOM_ACRE).build())
                .owningEntityId(owningEntityId)
                .assetSpecsList(specs)
                .ownerEntityType(getRefDataDTO(ENTITY_TYPE_IE))
                .build());
    }

    private List<AssetDTO> getAssetList(List<AssetExcelVO> assetExcelVOS) {
        Map<String, Long> uuidMap = getIeIdMap(assetExcelVOS.stream()
                .map(AssetExcelVO::getFarmerSerialNumber)
                .collect(Collectors.toSet()));
        List<AssetDTO> assetDTOS = new ArrayList<>();
        for (AssetExcelVO vo : assetExcelVOS) {
            String landOwnedType = vo.getLandOwnedType();
            String subCategory = null;
            String functionality = null;
            String owningEntityId = Objects.toString(uuidMap.get(vo.getFarmerSerialNumber()));
            String landCode = vo.getCode();
            if (owningEntityId.equals("null")) owningEntityId = vo.getFarmerSerialNumber();
            switch (landOwnedType) {
                case LAND_OWNED_IRRIGATED:
                    subCategory = ASSET_SUBCATEGORY_LAND_OWNED;
                    functionality = ASSET_FUNCTIONALITY_IRRIGATED;
                    break;
                case LAND_OWNED_RAIN_FED:
                    subCategory = ASSET_SUBCATEGORY_LAND_OWNED;
                    functionality = ASSET_FUNCTIONALITY_RAIN_FED;
                    break;
                case LAND_LEASED_IRRIGATED:
                    subCategory = ASSET_SUBCATEGORY_LAND_LEASED;
                    functionality = ASSET_FUNCTIONALITY_IRRIGATED;
                    break;
                case LAND_LEASED_RAIN_FED:
                    subCategory = ASSET_SUBCATEGORY_LAND_LEASED;
                    functionality = ASSET_FUNCTIONALITY_RAIN_FED;
                    break;
            }
            buildAssetDTO(vo, subCategory, functionality, owningEntityId, landCode).ifPresent(assetDTOS::add);
        }
        return assetDTOS;
    }

    private Map<String, Long> getIeIdMap(Set<String> collect) {
        String params = String.format("IN_PARAM_UUID=%s", String.join(",", collect));
        return dataSetClient.executeQueryV3(GET_IE_ID_MAP, params, 0, 0, PlatformSecurityUtil.getToken())
                .stream()
                .collect(Collectors.toMap(map -> map.get("uuid").toString(), map -> (Long.parseLong(map.get("id").toString()))));
    }

    private RefDataDTO getRefDataDTO(String category) {
        if (category == null) return null;
        return RefDataDTO.builder().code(category).build();
    }

    private List<IeDTO> getIeList(List<IEExcelVo> ieExcelVos) {
        ConcurrentHashMap<String, Boolean> uuids = new ConcurrentHashMap<>();
        List<IeDTO> ieDTOS = Collections.synchronizedList(new ArrayList<>());
        /*
        TODO:
            Creation Mode To Add
        */
        Map<String, Map<String, Long>> taggedWorkNodeId = getTaggedWorkNodeIds(ieExcelVos);
        ieExcelVos.parallelStream().forEach(ieExcelVo -> {
            try {
                if (ieExcelVo.getSerialNumber() != null) {
                    if (uuids.containsKey(ieExcelVo.getSerialNumber())) throw new DuplicateResourceException("Duplicate UUIDS found");
                    uuids.put(ieExcelVo.getSerialNumber(),true);
                }
                ieDTOS.add(IeDTO.builder()
                        .id(null)
                        .uuid(ieExcelVo.getSerialNumber())
                        .creationMode(CREATION_MODE_EXCEL)
                        .deliveryModeList(getDeliveryModeList(ieExcelVo.getDeliveryModes()))
                        .ieType(IE_TYPE_FARMER)
                        .taggedToWorkforceId(taggedWorkNodeId.get(ieExcelVo.getAbaLogin()).get("workForceId"))
                        .taggedToWorknodeId(taggedWorkNodeId.get(ieExcelVo.getAbaLogin()).get("workNodeId"))
                        .person(getPerson(ieExcelVo))
                        .onBoardDateTime(new Date())
                        .build());
            } catch (Exception e) {
                log.error("Error while processing row number {}", ieExcelVo.getRowNumber(), e);
            }
        });
        return ieDTOS;
    }


    private List<IeDTO> getIeListV2(List<IEExcelVoV2> ieExcelVos) {
        List<IeDTO> ieDTOS = Collections.synchronizedList(new ArrayList<>());
        ieExcelVos.parallelStream().forEach(ieExcelVo -> {
            try {

                ieDTOS.add(IeDTO.builder()
                        .id(null)
                        .creationMode(CREATION_MODE_EXCEL)
                        .deliveryModeList(getDeliveryModeList(ieExcelVo.getDeliveryModes()))
                        .ieType(ieExcelVo.getIeType() == null || ieExcelVo.getIeType().trim().isEmpty() ? IE_TYPE_FARMER : ieExcelVo.getIeType())
                        .ieSubType(ieExcelVo.getIeSubType() != null && !ieExcelVo.getIeSubType().trim().isEmpty() ? ieExcelVo.getIeSubType() : null)
                        .taggedToWorkforceId(Long.parseLong(ieExcelVo.getWorkforceId()))
                        .taggedToWorknodeId(Long.parseLong(ieExcelVo.getWorknodeId()))
                        .linkedIeId(ieExcelVo.getIeReferenceId()!=null && !ieExcelVo.getIeReferenceId().trim().isEmpty() ? Long.parseLong(ieExcelVo.getIeReferenceId()):null)
                        .person(getPerson(ieExcelVo))
                        .onBoardDateTime(new Date())
                        .taggedToServiceAreaType(ieExcelVo.getTaggedToServiceAreaType() != null && !ieExcelVo.getTaggedToServiceAreaType().trim().isEmpty() ? ieExcelVo.getTaggedToServiceAreaType() : null)
                        .taggedToServiceAreaCode(ieExcelVo.getTaggedToServiceAreaType() != null && !ieExcelVo.getTaggedToServiceAreaCode().trim().isEmpty() ? ieExcelVo.getTaggedToServiceAreaCode() : null)

                                .build());
            } catch (Exception e) {
                log.error("Error while processing row number {}", ieExcelVo.getRowNumber(), e);
            }
        });
        return ieDTOS;
    }




    private PersonDTO getPerson(IEExcelVo ieExcelVo) {
        return PersonDTO.builder()
                .id(null)
                .contactList(getContactList(ieExcelVo))
                .personProfile(getPersonProfile(ieExcelVo))
                .addressList(getAddressList(ieExcelVo))
                .personFamilyList(getFamilyList(ieExcelVo))
                .build();
    }

    private PersonDTO getPerson(IEExcelVoV2 ieExcelVo) {
        return PersonDTO.builder()
                .id(null)
                .contactList(getContactList(ieExcelVo))
                .personProfile(getPersonProfile(ieExcelVo))
                .addressList(getAddressList(ieExcelVo))
                .personFamilyList(getFamilyList(ieExcelVo))
                .build();
    }

    private Set<AddressDTO> getAddressList(IEExcelVo ieExcelVo) {
        Set<AddressDTO> addressDTOS = new LinkedHashSet<>();
        if (ieExcelVo.getVillage() != null)
            addressDTOS.add(AddressDTO.builder()
                    .addressType("ADDRESS_TYPE.CURRENT")
                    .village(ieExcelVo.getVillage())
                    .panchayat(ieExcelVo.getPanchayat())
                    .mandal(ieExcelVo.getMandal())
                    .district(ieExcelVo.getDistrict())
                    .state(ieExcelVo.getState())
                    .pinCode(ieExcelVo.getPincode())
                    .build());
        return addressDTOS;
    }

    private Set<AddressDTO> getAddressList(IEExcelVoV2 ieExcelVo) {
        Set<AddressDTO> addressDTOS = new LinkedHashSet<>();
        addressDTOS.add(AddressDTO.builder()
                .addressLine1(ieExcelVo.getAddressLine1())
                .addressType("ADDRESS_TYPE.PERMANENT")
                .district(ieExcelVo.getDistrict())
                .state(ieExcelVo.getState())
                .pinCode(ieExcelVo.getPincode())
                .build());
        return addressDTOS;
    }

    private PersonProfileDTO getPersonProfile(IEExcelVo ieExcelVo) {
        return PersonProfileDTO.builder()
                .gender(ieExcelVo.getGender())
                .firstName(ieExcelVo.getFirstName())
                .displayName(ieExcelVo.getFirstName())
                .middleName(ieExcelVo.getFatherName())
                .spouseName(ieExcelVo.getSpouseName())
                .build();
    }

    private PersonProfileDTO getPersonProfile(IEExcelVoV2 ieExcelVo) {
        return PersonProfileDTO.builder()
                .gender(ieExcelVo.getGender())
                .firstName(ieExcelVo.getFirstName())
                .displayName(ieExcelVo.getFirstName())
                .middleName(ieExcelVo.getFatherName())
                .spouseName(ieExcelVo.getSpouseName())
                .build();
    }

    private Set<ContactDTO> getContactList(IEExcelVo ieExcelVo) {
        Set<ContactDTO> set = new HashSet<>();
        if (ieExcelVo.hasContact()) {
            set.add(ContactDTO.builder().contactType("CONTACT_TYPE.MOBILE").contactValue(ieExcelVo.getContactNumber()).build());
        }
        return set;
    }

    private Set<ContactDTO> getContactList(IEExcelVoV2 ieExcelVo) {
        Set<ContactDTO> set = new HashSet<>();
        if (ieExcelVo.hasContact()) {
            set.add(ContactDTO.builder().contactType("CONTACT_TYPE.MOBILE").contactValue(ieExcelVo.getContactNumber()).build());
        }
        return set;
    }

    private Map<String, Map<String, Long>> getTaggedWorkNodeIds(List<IEExcelVo> ieExcelVos) {

        Set<String> abaLogins = new HashSet<>();
        Map<String, Map<String, Long>> loginWFAndWNMap = new ConcurrentHashMap<>();
        for (IEExcelVo ieExcelVo : ieExcelVos) {
            if (ieExcelVo.getAbaLogin() != null) {
                abaLogins.add(ieExcelVo.getAbaLogin());
            }
        }
        if (abaLogins.isEmpty()) return new ConcurrentHashMap<>();
        String params = String.format("IN_PARAM_ABA_LOGIN=%s", String.join(",", abaLogins));
        List<Map<String, Object>> result = dataSetClient.executeQueryV3(GET_WORK_NODE_ID_DATASET, params, 0, 0, PlatformSecurityUtil.getToken());
        for (Map<String, Object> row : result) {
            loginWFAndWNMap.put(row.get("login").toString(), new ConcurrentHashMap<String, Long>() {{
                put("workNodeId", ((Number) row.get("workNodeId")).longValue());
                put("workForceId", ((Number) row.get("workForceId")).longValue());
            }});
        }
        return loginWFAndWNMap;
    }

    private Set<DeliveryModeDTO> getDeliveryModeList(String deliveryMode) {
        if (deliveryMode == null || deliveryMode.isEmpty()) return new LinkedHashSet<>();
        return Arrays.stream(deliveryMode.split(","))
                .map(code -> DeliveryModeDTO.builder().id(null).code(code).build())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    private Set<PersonFamilyDTO> getFamilyList(IEExcelVo ieExcelVo) {
        Set<PersonFamilyDTO> personFamilyDTOS = new HashSet<>();
        if(ieExcelVo.getFatherName()!=null && !ieExcelVo.getFatherName().trim().isEmpty()){
            personFamilyDTOS.add(PersonFamilyDTO.builder()
                    .id(0L)
                    .memberName(ieExcelVo.getFatherName())
                    .gender("GENDER.MALE")
                    .relationship("RELATIONSHIP.FATHER")
                    .build());
        }
        if(ieExcelVo.getSpouseName()!=null && !ieExcelVo.getSpouseName().trim().isEmpty()){
            personFamilyDTOS.add(PersonFamilyDTO.builder()
                    .id(0L)
                    .memberName(ieExcelVo.getSpouseName())
                    .relationship("RELATIONSHIP.SPOUSE")
                    .build());
        }
        return personFamilyDTOS;
    }
    private Set<PersonFamilyDTO> getFamilyList(IEExcelVoV2 ieExcelVo) {
        Set<PersonFamilyDTO> personFamilyDTOS = new HashSet<>();
        if(ieExcelVo.getFatherName()!=null && !ieExcelVo.getFatherName().trim().isEmpty()){
            personFamilyDTOS.add(PersonFamilyDTO.builder()
                    .id(null)
                    .memberName(ieExcelVo.getFatherName())
                    .gender("GENDER.MALE")
                    .relationship("RELATIONSHIP.FATHER")
                    .build());
        }
        if(ieExcelVo.getSpouseName()!=null && !ieExcelVo.getSpouseName().trim().isEmpty()){
            personFamilyDTOS.add(PersonFamilyDTO.builder()
                    .id(null)
                    .memberName(ieExcelVo.getSpouseName())
                    .relationship("RELATIONSHIP.SPOUSE")
                    .build());
        }
        return personFamilyDTOS;
    }

}