package rsb.testing.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
class CustomerWebConfiguration {

	@Bean
	RouterFunction<ServerResponse> routes(CustomerRepository cr) {
		return route(GET("/customers"),
				r -> ServerResponse.ok().body(cr.findAll(), Customer.class));
	}

}
