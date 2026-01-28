package com.platformcommons.platform.service.iam.application.utility;

import com.platformcommons.platform.exception.ErrorDTO;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.iam.dto.TFIUserVerificationDTO;
import com.platformcommons.platform.service.iam.dto.TFIResponseDTO;
import com.platformcommons.platform.service.iam.facade.client.TFIClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TFIClientUtil {

    @Value("${commons.platform.tfi.authorization-key:AuthorizationToken}")
    private String AUTHORIZATION_KEY;

    @Value("${commons.platform.tfi.authorization-value:test-token-Tfi@alumni-1234}")
    private String AUTHORIZATION_VALUE;

    @Autowired
    private TFIClient tfiClient;

    public TFIResponseDTO tfiUserVerification(TFIUserVerificationDTO tfiUserVerificationDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(AUTHORIZATION_KEY,AUTHORIZATION_VALUE);
        Map<String,String> responseBodyMap = tfiClient.tfiUserVerification(tfiUserVerificationDTO,headers).getBody();
        TFIResponseDTO tfiResponseDTO = convertMapToTFIResponseDTO(responseBodyMap);
        if(tfiResponseDTO != null) {
            if(tfiResponseDTO.getErrorCode() != null && tfiResponseDTO.getErrorCode().startsWith("4")) {
                ErrorDTO errorDTO = new ErrorDTO();
                errorDTO.setErrorMessage(tfiResponseDTO.getErrorMessage());
                errorDTO.setErrorCode(tfiResponseDTO.getErrorCode());
                throw new InvalidInputException(errorDTO.getErrorMessage());
            }
        }
        else {
            throw new InvalidInputException("Response given from Salas is null");
        }
        return tfiResponseDTO;
    }

    public TFIResponseDTO convertMapToTFIResponseDTO(Map<String,String> responseMap) {
        if(responseMap != null && !responseMap.isEmpty())  {
            TFIResponseDTO tfiResponseDTO =  new TFIResponseDTO();
            responseMap.forEach((key,value) -> {
                switch (key) {
                    case "ReturnStatus":
                        tfiResponseDTO.setReturnStatus(value);
                        break;
                    case "ReturnCode":
                        tfiResponseDTO.setReturnCode(value);
                        break;
                    case "ErrorMessage":
                        tfiResponseDTO.setErrorMessage(value);
                        break;
                    case "ErrorCode":
                        tfiResponseDTO.setErrorCode(value);
                        break;
                    default:
                        throw new IllegalStateException(String.format("Unexpected Key -%s in the salesforce api ",key));
                }
            });
            return tfiResponseDTO;
        }
        return null;
    }
}
