package com.hrbb.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *<h1></h1>
 *@author Johnson Song
 *@version Id: UnitTest.java, v 1.0 2015-3-16 下午6:59:30 Johnson Song Exp
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath*:dao-config/spring-context-dao.xml",
		"classpath:config/context/web-config.xml", 
		"classpath:config/context/jms-context.xml",
		"classpath*:service-config/spring-context-service.xml",
		"classpath*:integreation-config/spring-context-integration.xml",
		"classpath*:service-config/spring-context-transaction.xml",
		"classpath*:biz-config/spring-context-biz.xml"})
public class UnitTest {
	

}
