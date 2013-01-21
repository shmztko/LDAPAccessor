package org.shmztko.ldap.novell.search.query;

public interface Query {

	public static final String AND = "&";
	public static final String NOT = "!";
	public static final String OR = "|";
	public static final String WILDCARD = "*";

	public String getQuery();
}
