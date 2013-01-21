package org.shmztko.ldap;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.shmztko.ldap.jndi.JNDILdapConnector;
import org.shmztko.ldap.jndi.model.LdapAttribute;
import org.shmztko.ldap.jndi.model.LdapSearchResult;
import org.shmztko.ldap.novell.NovellLdapConnector;
import org.shmztko.ldap.novell.search.SearchScope;
import org.shmztko.ldap.novell.search.query.Condition;
import org.shmztko.ldap.novell.search.query.QueryBuilder;
import org.shmztko.ldap.novell.search.result.LDAPSearchResultAttribute;
import org.shmztko.ldap.novell.search.result.LDAPSearchResultEntry;

import com.novell.ldap.LDAPConnection;

public class Main {

	private static final String IP = "172.27.194.9";
	private static final String BASE_DN = "dc=www,dc=olu2,dc=com";
	private static final String PRINCIPAL = "cn=Manager," + BASE_DN;
	private static final String CREDENTIAL = "master";

	public static void main(String[] args) {
		accessToLdap2();
	}

	private static void accessToLdap2() {
		NovellLdapConnector connector = new NovellLdapConnector(IP, BASE_DN, PRINCIPAL, CREDENTIAL);
		try {
			connector.open();

			List<LDAPSearchResultEntry> result = connector.search(
					SearchScope.SUB,
					QueryBuilder.make(new Condition("cn", "e2")),
					null,
					true
			);

			for (LDAPSearchResultEntry entry : result) {
				System.out.println("=================");
				System.out.println(entry.getID());
				for (LDAPSearchResultAttribute attribute : entry.getAttributes()) {
					System.out.println(attribute.getName());
					System.out.println(attribute.getValues());
				}
			}

		} finally {
			if (connector != null) {
				connector.close();
			}
		}
	}

	private static void accessToLdap() {
		JNDILdapConnector ldapConnector = null;
		try {
			ldapConnector = new JNDILdapConnector(IP, LDAPConnection.DEFAULT_PORT, PRINCIPAL, CREDENTIAL);

			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			List<LdapSearchResult> searchResults = ldapConnector.search("ou=People,dc=www,dc=olu2,dc=com", "uid=shimizu", searchControls);

			for (LdapSearchResult searchResult : searchResults) {
				System.out.println("==========");
				for (LdapAttribute attr : searchResult.getAttributes()) {
					System.out.println("----------");
					System.out.println("ID :" + attr.getID());
					for (Object attrValue : attr.getValues()) {
						System.out.println(attrValue);
					}
				}
			}
		} finally {
			if (ldapConnector != null) {
				ldapConnector.close();
			}
		}
	}
}
