package org.shmztko.ldap.jndi;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.shmztko.ldap.jndi.model.LdapSearchResult;

/**
 * JNDIでのLDAP接続用クラス
 * @author ShimizuTakeo
 */
public class JNDILdapConnector {

	private InitialDirContext directoryContext;

	private final String DEFAULT_CONTEXT_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
	private final String LDAP_PROVIDER_URL_FORMAT = "ldap://{0}:{1}";

	public JNDILdapConnector(Hashtable<String, String> environmentProps) {
		directoryContext = createContext(environmentProps);
	}

	public JNDILdapConnector( String host, int port, String securityPrincial, String securityCredentials) {
		Hashtable<String, String> envProps = new Hashtable<String, String>();
		envProps.put(Context.INITIAL_CONTEXT_FACTORY, DEFAULT_CONTEXT_FACTORY);
		envProps.put(Context.PROVIDER_URL, MessageFormat.format(LDAP_PROVIDER_URL_FORMAT, host, port));
		envProps.put(Context.SECURITY_AUTHENTICATION, "simple");

		envProps.put(Context.SECURITY_PRINCIPAL, securityPrincial);
		envProps.put(Context.SECURITY_CREDENTIALS, securityCredentials);
		directoryContext = createContext(envProps);
	}

	public List<LdapSearchResult> search(String name, String filter, SearchControls controls) {
		try {
			List<LdapSearchResult> result = new ArrayList<LdapSearchResult>();

			NamingEnumeration<SearchResult> searchResultEntries = directoryContext.search(name, filter, controls);
			while (searchResultEntries.hasMore()) {
				SearchResult searchResult = searchResultEntries.next();
				result.add(new LdapSearchResult(searchResult));
			}
			return result;

		} catch (NamingException e) {
			throw new RuntimeException("Search failed. name=" + name + ",filter=" + filter + ",controls=" + controls, e);
		}
	}

	public void add() {
	}



	public void close() {
		try {
			directoryContext.close();
		} catch (NamingException e) {
			throw new RuntimeException("failed to close InitialDirContext", e);
		}
	}

	private InitialDirContext createContext(Hashtable<String, String> environmentProps) {
		try {
			return new InitialDirContext(environmentProps);
		} catch (NamingException e) {
			throw new RuntimeException("Failed to create InitialDirContext. props -> " + environmentProps, e);
		}
	}
}
