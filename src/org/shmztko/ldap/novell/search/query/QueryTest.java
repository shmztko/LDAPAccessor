package org.shmztko.ldap.novell.search.query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.shmztko.ldap.novell.search.query.QueryBuilder.*;

public class QueryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Condition c1 = new Condition("uid", "shimizu");
		Condition c2 = new Condition("mail", "st0098@gmail.com");

		Condition c3 = new Condition("hoge", "ほげ");
		Condition c4 = new Condition("fuga", "ふが");


		Query query = make(
			or(
				not(and(c1,c2)),
				or(not(and(c3,c4)))
			)
		);

		System.out.println(query.toString());
	}

}
