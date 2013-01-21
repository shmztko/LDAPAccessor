package org.shmztko.ldap.novell.search.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPEntry;

public class LDAPSearchResultEntry {
	private String id;

	private List<LDAPSearchResultAttribute> attributes;

	public LDAPSearchResultEntry(LDAPEntry entry) {
		this.id = entry.getDN();

		attributes = new ArrayList<LDAPSearchResultAttribute>();

		LDAPAttributeSet attributeSet = entry.getAttributeSet();
		for (Iterator<com.novell.ldap.LDAPAttribute> i = attributeSet.iterator(); i.hasNext();) {
			com.novell.ldap.LDAPAttribute attribute = i.next();
			attributes.add(new LDAPSearchResultAttribute(attribute));
		}
	}

	public String getID() {
		return id;
	}

	public List<LDAPSearchResultAttribute> getAttributes() {
		return attributes;
	}

}
