package org.shmztko.ldap.access.model;

import java.util.List;

import javax.naming.directory.SearchResult;

public class LdapSearchResult {
	private SearchResult searchResult;

	public LdapSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	public String getQualifiedName() {
		return searchResult.getNameInNamespace();
	}

	public String getName() {
		return searchResult.getName();
	}

	public List<LdapAttribute> getAttributes() {
		return new LdapAttributes(searchResult.getAttributes()).getAttributes();
	}
}
