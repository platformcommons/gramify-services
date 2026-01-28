package com.platformcommons.platform.service.iam.dto.brbase;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;

@JsonInclude(value = Include.NON_NULL)
@NoArgsConstructor
public class AddressDTO extends BaseDTO implements Serializable{

	@NotNull(message = "AddressDTO# id cannot be null")
	@ApiModelProperty(required = true)
	private Integer id;

	@NotNull(message = "AddressDTO# addressLine1 cannot be null")
	@ApiModelProperty(required = true)
	private String addressLine1;

	private String addressLine2;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.CITY")
	private GlobalRefDataDTO city;

	@Valid
	@NotNull(message = "AddressDTO# country cannot be null")
	@ApiModelProperty(value = "classCode=GREF.COUNTRY", required = true)
	private GlobalRefDataDTO country;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.DISTRICT")
	private GlobalRefDataDTO district;

	private String identifierText;

	private String pinCode;

	@Valid
	@NotNull(message = "AddressDTO# state cannot be null")
	@ApiModelProperty(value = "classCode=GREF.STATE", required = true)
	private GlobalRefDataDTO state;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.TOWN")
	private GlobalRefDataDTO town;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.VILLAGE")
	private GlobalRefDataDTO village;

	@ApiModelProperty(required = false, value = "Is address verified; default is false")
	private Boolean verified;

	private Double latitude;

	private Double longitude;

	@Valid
	private LandmarkDTO landmark;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.PANCHAYAT")
	private GlobalRefDataDTO panchayat;

	@Valid
	@ApiModelProperty(value = "classCode=GREF.TALUK")
	private GlobalRefDataDTO taluk;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String PINCode) {
		this.pinCode = PINCode;
	}

	public GlobalRefDataDTO getCity() {
		return city;
	}

	public void setCity(GlobalRefDataDTO city) {
		this.city = city;
	}

	public GlobalRefDataDTO getCountry() {
		return country;
	}

	public void setCountry(GlobalRefDataDTO country) {
		this.country = country;
	}

	public GlobalRefDataDTO getDistrict() {
		return district;
	}

	public void setDistrict(GlobalRefDataDTO district) {
		this.district = district;
	}

	public String getIdentifierText() {
		return identifierText;
	}

	public void setIdentifierText(String identifierText) {
		this.identifierText = identifierText;
	}

	public GlobalRefDataDTO getState() {
		return state;
	}

	public void setState(GlobalRefDataDTO state) {
		this.state = state;
	}

	public GlobalRefDataDTO getTown() {
		return town;
	}

	public void setTown(GlobalRefDataDTO town) {
		this.town = town;
	}

	public GlobalRefDataDTO getVillage() {
		return village;
	}

	public void setVillage(GlobalRefDataDTO village) {
		this.village = village;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public LandmarkDTO getLandmark() {
		return landmark;
	}

	public void setLandmark(LandmarkDTO landmark) {
		this.landmark = landmark;
	}



	/**
	 * @return the panchayat
	 */
	public GlobalRefDataDTO getPanchayat() {
		return panchayat;
	}

	/**
	 * @param panchayat
	 *            the panchayat to set
	 */
	public void setPanchayat(GlobalRefDataDTO panchayat) {
		this.panchayat = panchayat;
	}

	/**
	 * @return the taluk
	 */
	public GlobalRefDataDTO getTaluk() {
		return taluk;
	}

	/**
	 * @param taluk
	 *            the taluk to set
	 */
	public void setTaluk(GlobalRefDataDTO taluk) {
		this.taluk = taluk;
	}

	@Builder
	public AddressDTO(String aliasId, String notes, Integer id, String addressLine1, String addressLine2, GlobalRefDataDTO city,
					  GlobalRefDataDTO country, GlobalRefDataDTO district, String identifierText, String pinCode, GlobalRefDataDTO state,
					  GlobalRefDataDTO town, GlobalRefDataDTO village, Boolean verified, Double latitude, Double longitude, LandmarkDTO landmark,
					  GlobalRefDataDTO panchayat, GlobalRefDataDTO taluk) {
		super(aliasId, notes);
		this.id = id;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.district = district;
		this.identifierText = identifierText;
		this.pinCode = pinCode;
		this.state = state;
		this.town = town;
		this.village = village;
		this.verified = verified;
		this.latitude = latitude;
		this.longitude = longitude;
		this.landmark = landmark;
		this.panchayat = panchayat;
		this.taluk = taluk;
	}

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", addressLine1=" + addressLine1
				+ ", addressLine2=" + addressLine2 + ", city=" + city
				+ ", country=" + country + ", district=" + district
				+ ", identifierText=" + identifierText + ", pinCode=" + pinCode
				+ ", state=" + state + ", town=" + town + ", village="
				+ village + ", verified=" + verified + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", landmark=" + landmark
			    + "]";
	}

}
