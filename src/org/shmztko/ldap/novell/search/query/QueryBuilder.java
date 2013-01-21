package org.shmztko.ldap.novell.search.query;

public class QueryBuilder {

	public static Query make(Query ... queries) {
		return QueryImpl.create(toConditionQuery(queries));
	}

	public static Query and(Query ... queries) {
		StringBuilder sb = new StringBuilder();
		return QueryImpl.create(sb.append("(").append(Query.AND).append(toConditionQuery(queries)).append(")").toString());
	}

	public static Query or(Query ... queries) {
		StringBuilder sb = new StringBuilder();
		return QueryImpl.create(sb.append("(").append(Query.OR).append(toConditionQuery(queries)).append(")").toString());
	}

	public static Query not(Query query) {
		StringBuilder sb = new StringBuilder();
		return QueryImpl.create(sb.append("(").append(Query.NOT).append(query.getQuery()).append(")").toString());
	}

	private static String toConditionQuery(Query[] queries) {
		StringBuilder sb = new StringBuilder();
		for (Query q : queries) {
			sb.append(q.getQuery());
		}
		return sb.toString();
	}

}
