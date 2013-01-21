package org.shmztko.ldap.novell;

import java.util.ArrayList;
import java.util.List;

import org.shmztko.ldap.novell.search.SearchScope;
import org.shmztko.ldap.novell.search.query.Query;
import org.shmztko.ldap.novell.search.result.LDAPSearchResultEntry;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPSearchResults;

public class NovellLdapConnector {

	private LDAPConnection connection = null;

	private int ldapVersion;
	private String host;
	private int port;

	private String baseDn;

	private String principal;
	private String credentials;

	public NovellLdapConnector(String host, String baseDn, String principal, String credentials) {
		this(LDAPConnection.LDAP_V3, host, LDAPConnection.DEFAULT_PORT, baseDn, principal, credentials);
	}

	public NovellLdapConnector(String host, int port, String baseDn, String principal, String credentials) {
		this(LDAPConnection.LDAP_V3, host, port, baseDn, principal, credentials);
	}

	public NovellLdapConnector(int ldapVersion, String host, int port, String baseDn, String principal, String credentials) {
		connection = new LDAPConnection();
		this.ldapVersion = ldapVersion;
		this.host = host;
		this.port = port;

		this.baseDn = baseDn;

		this.principal = principal;
		this.credentials = credentials;
	}

	public void open() {
		connect();
		bind();
	}

	public void close() {
		disconnect();
	}

	public boolean isAuthenticated() {
		return connection.isBound();
	}

	public List<LDAPSearchResultEntry> search(SearchScope scope, Query filter, String[] attribute, boolean includeValue) {
		LDAPSearchResults results = null;
		try {
			results = connection.search(
					baseDn,
					scope.getScopeValue(),
					filter.getQuery(),
					attribute,
					!includeValue
			);
			return convertSearchResult(results);

		} catch (LDAPException e) {
			throw new RuntimeException("Search failed.", e);
		}
	}

	public void add(LDAPEntry entry) {
		try {
			connection.add(entry);
		} catch (LDAPException e) {
			throw new RuntimeException("Failed to add entry.", e);
		}
	}

	public void delete(String dn) {
		try {
			connection.delete(dn);
		} catch (LDAPException e) {
			throw new RuntimeException("Failed to delete entry.", e);
		}
	}


	private List<LDAPSearchResultEntry> convertSearchResult(LDAPSearchResults searchResults) {
		List<LDAPSearchResultEntry> result = new ArrayList<LDAPSearchResultEntry>();
		while (searchResults.hasMore()) {
			try {
				com.novell.ldap.LDAPEntry entry = searchResults.next();
				result.add(new LDAPSearchResultEntry(entry));

			} catch (LDAPException e) {
				throw new RuntimeException("Failed to convert search result.", e);
			}
		}
		return result;
	}

	private void connect() {
		if (connection.isConnected()) {
			return;
		}
		try {
			connection.connect(host, port);

		} catch (LDAPException e) {
			throw new RuntimeException("Failed to connect. host:" + host + " port:" + port, e);
		}
	}

	private void bind() {
		if (connection.isBound()) {
			return;
		}
		try {
			connection.bind(ldapVersion, principal, credentials.getBytes());
		} catch (LDAPException e) {
			throw new RuntimeException("Failed to bind ldap", e);
		}
	}


	private void disconnect() {
		if (!connection.isConnected()) {
			return;
		}
		try {
			connection.disconnect();
		} catch (LDAPException e) {
			throw new RuntimeException("Failed to disconnect from ldap.", e);
		}
	}
}
