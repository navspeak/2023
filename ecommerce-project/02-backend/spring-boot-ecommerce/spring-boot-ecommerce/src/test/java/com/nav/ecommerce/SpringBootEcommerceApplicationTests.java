package com.nav.ecommerce;

import com.nav.ecommerce.junitdemo.PrototypeBean;
import com.nav.ecommerce.junitdemo.SingletonBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes= SpringBootEcommerceApplication.class)
// classes =... not needed if test package is same as code package
class SpringBootEcommerceApplicationTests {

	@Value("${spring.datasource.url}")
	private String datasourceUrl;

	@Autowired
	ApplicationContext context;
	@Autowired
	SingletonBean singletonBean1;
	@Autowired
	PrototypeBean prototypeBean1;

	@Test
	@DisplayName("Test JDBC URL")
	void testDataBaseUrl(){
		String expected = "ecommercedb";
		assertTrue(datasourceUrl.contains(expected));
	}

	@Test
	@DisplayName("Test Only One Singleton Existis")
	void singletonTest(){
		SingletonBean singletonBean = context.getBean("singletonBean", SingletonBean.class);
		assertSame(singletonBean, singletonBean1 );
		singletonBean.setProperty1("P1");
		assertEquals("P1",singletonBean1.getProperty1());

	}

	@Test
	@DisplayName("Test two prototype beans are different")
	void protoTypeTest(){
		PrototypeBean prototypeBean = context.getBean("prototypeBean", PrototypeBean.class);
		assertNotSame(prototypeBean, prototypeBean1 );
		prototypeBean.setProperty1("P1");
		assertNull(prototypeBean1.getProperty1());

	}

	@Test
	void contextLoads() {
	}

}
