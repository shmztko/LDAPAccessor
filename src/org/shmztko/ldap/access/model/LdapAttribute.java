package org.shmztko.ldap.access.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;

public class LdapAttribute {

	private Attribute attribute;

	public LdapAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public String getID() {
		return attribute.getID();
	}

	public List<Object> getValues() {
		try {
			List<Object> result = new ArrayList<Object>();
			NamingEnumeration<?> values = attribute.getAll();
			while (values.hasMore()) {
				result.add(values.next());
			}
			return result;
		} catch (NamingException e) {
			throw new RuntimeException("failed to get attributes", e);
		}
	}

}
