package rsb.testing.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@Testcontainers
@DataMongoTest // <2>
public class CustomerRepositoryTest {

	@Container
	static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:5.0.5");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDbContainer::getReplicaSetUrl);
	}

	// <3>
	@Autowired
	private CustomerRepository customerRepository;

	// <4>
	@Test
	public void findByName() {
		var commonName = "Jane";
		var one = new Customer("1", commonName);
		var two = new Customer("2", "John");
		var three = new Customer("3", commonName);
		var setupPublisher = this.customerRepository //
				.deleteAll() //
				.thenMany(this.customerRepository.saveAll(Flux.just(one, two, three))) //
				.thenMany(this.customerRepository.findByName(commonName));
		var customerPredicate = (Predicate<Customer>) customer -> commonName.equalsIgnoreCase(customer.name());// <5>
		StepVerifier // <6>
				.create(setupPublisher) //
				.expectNextMatches(customerPredicate) //
				.expectNextMatches(customerPredicate) //
				.verifyComplete();

	}

}
