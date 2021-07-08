package my.project.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import my.project.model.ResponseObject;
import my.project.processors.T24ResponseProcessor;

@Component
public class MyRoute extends RouteBuilder{

	@Autowired
	private T24ResponseProcessor t24ResponseProcessor;
	
	@Override
	public void configure() throws Exception {
		// configures REST DSL to use servlet component and in JSON mode
		/*
        restConfiguration()
          .component("servlet")
          .bindingMode(RestBindingMode.json);
        */

        // REST DSL with a single GET /hello service
        rest()
          .bindingMode(RestBindingMode.json)
          .get("/hello").description("Hello GET REST Service")
              .consumes("application/json")
              .produces("application/json")
    	      .to("direct:hello");
        // route called from REST service that builds a response message
        from("direct:hello")
          .log(LoggingLevel.INFO, "Hello World Service call")
          .bean(this, "createResponse");
        

		// Rest Endpoints
		rest("/T24KCBAccountBalStmt")
			.bindingMode(RestBindingMode.off)
			.post("/services").description("T24 Balance Enquiry Post REST Service")
				.consumes("application/xml")
				.produces("application/xml")
				.to("direct:dispatchT24Post");
		/* Routes Configuration */
		from("direct:dispatchT24Post").routeId("b2c.request.dispatchT24Post").noStreamCaching()
			    .log(LoggingLevel.INFO, "Balance Inquiry Mock Request:: Headers: \n${in.headers} -Payload: \n${body}")
				.removeHeaders("Camel*")
				.process(t24ResponseProcessor)
			    .log(LoggingLevel.INFO, "Balance Inquiry Mock Response :: Headers: \n${in.headers} -Payload: \n${body}");
        
	}
	
	 public ResponseObject createResponse() {
	        ResponseObject response = new ResponseObject();
	        response.setResponse("Hello World");
	        response.setName("your name");
	        return response;
	    }
}
