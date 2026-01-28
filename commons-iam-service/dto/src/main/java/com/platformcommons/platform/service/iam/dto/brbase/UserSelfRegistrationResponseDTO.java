package com.platformcommons.platform.service.iam.dto.brbase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSelfRegistrationResponseDTO {

	private String otpSentMasg;

	private String modKey;

	private String responseMsg;

	private String modKeyForEmail;

	/**
	 * @return the otpSentMasg
	 */
	public String getOtpSentMasg() {
		return otpSentMasg;
	}

	/**
	 * @param otpSentMasg
	 *            the otpSentMasg to set
	 */
	public void setOtpSentMasg(String otpSentMasg) {
		this.otpSentMasg = otpSentMasg;
	}

	/**
	 * @return the modKey
	 */
	public String getModKey() {
		return modKey;
	}

	/**
	 * @param modKey
	 *            the modKey to set
	 */
	public void setModKey(String modKey) {
		this.modKey = modKey;
	}

	/**
	 * @return the responseMsg
	 */
	public String getResponseMsg() {
		return responseMsg;
	}

	/**
	 * @param responseMsg
	 *            the responseMsg to set
	 */
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	/**
	 *
	 * @return modKey for email
	 */
	public String getModKeyForEmail() {
		return modKeyForEmail;
	}

	/**
	 *
	 * @param modKeyForEmail to set the value
	 */
	public void setModKeyForEmail(String modKeyForEmail) {
		this.modKeyForEmail = modKeyForEmail;
	}


	@Override
	public String toString() {
		return "UserSelfRegistrationResponseDTO [otpSentMasg = " + otpSentMasg + ",modKey = " + modKey
				+ ",modKeyForEmail = " + modKeyForEmail + ",responseMsg = " + responseMsg + "]";
	}

}
