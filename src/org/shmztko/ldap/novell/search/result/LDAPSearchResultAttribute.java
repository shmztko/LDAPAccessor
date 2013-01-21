package org.shmztko.ldap.novell.search.result;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.novell.ldap.LDAPAttribute;

public class LDAPSearchResultAttribute {

	private String name;

	private List<String> values;

	public LDAPSearchResultAttribute(LDAPAttribute attribute) {

		this.name = attribute.getName();

		Enumeration<String> attributeValues = attribute.getStringValues();
		this.values = new ArrayList<String>();
		while (attributeValues.hasMoreElements()) {
			String attributeValue = attributeValues.nextElement();
			this.values.add(attributeValue);
		}
	}

	public String getName() {
		return name;
	}

	public List<String> getValues() {
		return values;
	}

}
