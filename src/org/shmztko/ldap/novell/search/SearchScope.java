package org.shmztko.ldap.novell.search;

import com.novell.ldap.LDAPConnection;

public enum SearchScope {
	BASE(LDAPConnection.SCOPE_BASE),
	ONE(LDAPConnection.SCOPE_ONE),
	SUB(LDAPConnection.SCOPE_SUB),
	SUBORDINATESUBTREE(LDAPConnection.SCOPE_SUBORDINATESUBTREE);

	private int scopeValue;

	private SearchScope(int scopeValue) {
		 this.scopeValue = scopeValue;
	}

	public int getScopeValue() {
		return scopeValue;
	}

}
