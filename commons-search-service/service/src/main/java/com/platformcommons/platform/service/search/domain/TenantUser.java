package com.platformcommons.platform.service.search.domain;


import lombok.*;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SolrDocument(collection = "tenantuser")
public class TenantUser{
	@Id
	@Field
	private Long id;
	@Field
	private String user_login;
	@Field
	@Indexed(name = "name",type = "string")
	private String completeName;
	@Field
	private Long mobile;
	@Field
	@Indexed(name = "email",type = "string")
	private String email;
	@Field
	private String gender;
	@Field
	private String city;
	@Field
	private String state;

	@Field
	private Long pincode;

	@Field
	private Long tenant_id;

	@Field
	private String tenant_login;

	@Field
	private String roles_codes;

	@Builder
	public TenantUser(Long id, String user_login, String completeName, Long mobile, String email, String gender, String city, String state, Long pincode, Long tenant_id, String tenant_login, String roles_codes) {
		this.id = id;
		this.user_login = user_login;
		this.completeName = completeName;
		this.mobile = mobile;
		this.email = email;
		this.gender = gender;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.tenant_id = tenant_id;
		this.tenant_login = tenant_login;
		this.roles_codes = roles_codes;
	}
}
