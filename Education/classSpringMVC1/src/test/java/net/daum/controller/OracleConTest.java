package net.daum.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class OracleConTest {
	
	private static final String DRIVER = "oracle.jdbc.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private static final String USER = "night";
	private static final String PW = "123456";
	
	@Test // JUnit 연습 테스트용 애노테이션
	public void testCon() throws Exception{
		Class.forName(DRIVER);
		
		try(Connection con=DriverManager.getConnection(URL,USER,PW)){
			/*
			 * java 7(1.7) 이후부터 AutoCloseable 인터페이스를 상속받은 자식은 try() 내에서
			 * con을 생성하면 finally문에서 명시적으로 close()하지 않아도 자동으로 닫힌다.
			 */
			System.out.println(con);
		}catch(Exception e) {e.printStackTrace();}
	}
	
}
