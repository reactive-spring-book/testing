package rsb.testing.consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
class CustomerClient {

	private final WebClient webClient;

	CustomerClient(WebClient webClient) {
		this.webClient = webClient;
	}

	Flux<Customer> getAllCustomers() {
		return this.webClient.get().uri("http://localhost:8080/customers").retrieve()
				.bodyToFlux(Customer.class);
	}

}
