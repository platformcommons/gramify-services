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
@SolrDocument(collection = "companyMasterData")
public class CompanyMasterData {
	
	@Id
	@Field
	private Long id;

	@Field
	@Indexed(name = "name",type = "string")
	private String name;

	@Field
	@Indexed(name = "alias",type = "string")
	private String alias;

	@Field
	private String state;

	@Field
	private String city;

	@Field
	private String domain;

	@Field
	private String category;


	@Builder
	public CompanyMasterData(Long id, String name, String alias, String state, String city, String domain, String category) {
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.state = state;
		this.city = city;
		this.domain = domain;
		this.category = category;
	}
}
