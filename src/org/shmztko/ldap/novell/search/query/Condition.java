package org.shmztko.ldap.novell.search.query;

public class Condition implements Query {
	private String key;
	private String value;

	private String getKey() {
		return key;
	}

	private String getValue() {
		return value;
	}

	public String getQuery() {
		return new StringBuilder()
		.append("(").append(getKey())
		.append("=").append(getValue()).append(")").toString();
	}

	public Condition(String key, String value) {
		this.key = key;
		this.value = value;
	}


	@Override
	public String toString() {
		return getQuery();
	}
}
