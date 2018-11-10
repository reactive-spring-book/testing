package rsb.testing.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import rsb.testing.producer.Customer;
import rsb.testing.producer.CustomerRepository;

@DataMongoTest
@RunWith(SpringRunner.class)
public class CustomerRepositoryTest {

	private final Customer one = new Customer("1", "Jane");

	private final Customer two = new Customer("2", "John");

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	public void query() throws Exception {
		Publisher<Customer> setup = this.customerRepository.deleteAll()
				.thenMany(this.customerRepository.saveAll(Flux.just(this.one, this.two)))
				.thenMany(this.customerRepository.findAll());
		Publisher<Customer> composite = Flux.from(setup).thenMany(setup);
		StepVerifier.create(composite).expectNext(this.one, this.two).verifyComplete();
	}

}
