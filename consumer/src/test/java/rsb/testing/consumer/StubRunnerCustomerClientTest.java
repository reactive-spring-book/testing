package rsb.testing.consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ConsumerApplication.class)
@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "rsb:producer:+:stubs:8000") //
public class StubRunnerCustomerClientTest {

	@Autowired
	private CustomerClient client;

	@Autowired
	private Environment environment;

	@StubRunnerPort("rsb:producer")
	int producerPort;

	@Test
	public void getAllCustomers() {

		this.client.setBase("localhost:" + this.producerPort);

		Flux<Customer> customers = this.client.getAllCustomers();
		StepVerifier //
				.create(customers) //
				.expectNext(new Customer("1", "Jane")) //
				.expectNext(new Customer("2", "John")) //
				.verifyComplete();
	}

}