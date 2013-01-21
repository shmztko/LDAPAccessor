package org.shmztko.ldap.jndi.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

public class LdapAttributes {

	private Attributes attributes;

	public LdapAttributes(Attributes attributes) {
		this.attributes = attributes;
	}

	public List<LdapAttribute> getAttributes() {
		try {
			List<LdapAttribute> result = new ArrayList<LdapAttribute>();
			NamingEnumeration<? extends Attribute> attributeList = attributes.getAll();
			while (attributeList.hasMore()) {
				Attribute attribute = attributeList.next();
				result.add(new LdapAttribute(attribute));
			}
			return result;
		} catch (NamingException e) {
			throw new RuntimeException("failed to get attributes", e);
		}

	}
}
