
package com.mycompany.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = {MyApplication.class);
   properties = {"camel.springboot.java-routes-include-pattern=**/Foo*"})
@SpringBootApplication
public class MyCamelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCamelApplication.class, args);
	}

} // CHECKSTYLE:ON
