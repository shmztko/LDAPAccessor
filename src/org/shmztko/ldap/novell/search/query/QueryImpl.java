package org.shmztko.ldap.novell.search.query;

public class QueryImpl implements Query {

	private String query;

	private QueryImpl(String query) {
		this.query = query;
	}

	public static Query create(String query) {
		return new QueryImpl(query);
	}

	public String getQuery() {
		return query;
	}

	@Override
	public String toString() {
		return query.toString();
	}

}
