package com.mycompany.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class CamelRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		System.out.println("calling camel route..");
		 from("{{route.from}}?period={{timer.period}}").routeId("hello-route-id")//.routeGroup("hello-group")
         .transform().method("myBean", "saySomething")
         .filter(simple("${body} contains 'foo'"))
             .to("log:foo11221")
         .end()
         .to("stream:out");
		 
		 
		from("file:work/cbr/input?noop=true").routeId("file-route-id")
			.log("reading file")
			.log("Receiving order ${file:name}")
			.choice()
				.when().xpath("//order/customer/country[text() = 'UK']")
					.log("Sending order ${file:name} to the UK" )
					//.validator()
				    //.type("xml")
				    //.withUri("validator:xsd/schema.xsd")
					.to("file:work/cbr/output/uk")
					.choice()
						.when().xpath("//order/customer/country[text() = 'U']")
						.log("Sending order ${file:name} to the UK")
						.to("file:work/cbr/output/otherall")
				.when().xpath("//order/customer/country[text() = 'US']")
					.log("Sending order ${file:name} to the US")
					.to("file:work/cbr/output/us")
				.otherwise()
					.log("Sending order ${file:name} to another country")
					.to("file:work/cbr/output/others")
			.end()
			.log("Done processing ${file:name}");
	}

}
