package com.sj.termproject;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TermprojectApplicationTests {
	@Autowired private DataSource ds; // 작성
	
	@Test public void testConnection() throws Exception { // 작성
		System.out.println("ds : " + ds);
		Connection con = ds.getConnection(); // ds(DataSource)에서 Connection을 얻어내고
		System.out.println("Url : "+ con.getMetaData().getURL());
		System.out.println("UserName :"+con.getMetaData().getUserName());
		con.close(); // close
	}
}