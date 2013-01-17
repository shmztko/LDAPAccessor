package org.shmztko.ldap.access;

import java.util.List;

import javax.naming.directory.SearchControls;

import org.shmztko.ldap.access.model.LdapAttribute;
import org.shmztko.ldap.access.model.LdapSearchResult;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;

public class Main {

	private static final String IP = "";
	private static final String PRINCIPAL = "";
	private static final String CREDENTIAL = "";

	public static void main(String[] args) {
		accessToLdap2();
	}

	private static void accessToLdap2() {
		LDAPConnection lc = new LDAPConnection();
		try {
			lc.connect(IP, LDAPConnection.DEFAULT_PORT);
			lc.bind(3, PRINCIPAL, CREDENTIAL.getBytes());

			if (lc.isBound()) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}

		} catch (LDAPException e) {
			e.printStackTrace();
		} finally {
			try {
				lc.disconnect();
			} catch (LDAPException e) {
				e.printStackTrace();
			}
		}


	}

	private static void accessToLdap() {
		JNDILdapConnector ldapConnector = null;
		try {
			ldapConnector = new JNDILdapConnector(IP, PORT, PRINCIPAL, CREDENTIAL);

			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			List<LdapSearchResult> searchResults = ldapConnector.search("ou=People,dc=takewo,dc=org", "uid=shimizu", searchControls);

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
