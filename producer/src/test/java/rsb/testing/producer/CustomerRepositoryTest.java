package rsb.testing.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.function.Predicate;

@DataMongoTest
@RunWith(SpringRunner.class)
public class CustomerRepositoryTest {

	private String commonName = "Jane";

	private final Customer one = new Customer("1", this.commonName);

	private final Customer two = new Customer("2", "John");

	private final Customer three = new Customer("3", this.commonName);

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void findByName() {
		Publisher<Customer> setup = this.customerRepository //
				.deleteAll() //
				.thenMany(this.customerRepository
						.saveAll(Flux.just(this.one, this.two, this.three))) //
				.thenMany(this.customerRepository.findByName(this.commonName));

		Predicate<Customer> customerPredicate = customer -> this.commonName
				.equalsIgnoreCase(customer.getName());

		StepVerifier //
				.create(setup) //
				.expectNextMatches(customerPredicate) //
				.expectNextMatches(customerPredicate) //
				.verifyComplete();

	}

}
