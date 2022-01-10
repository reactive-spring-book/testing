package rsb.testing.consumer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
@DirtiesContext
@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.MOCK, //
		classes = ConsumerApplication.class //
)
@AutoConfigureStubRunner(//
		ids = StubRunnerCustomerClientTest.PRODUCER_ARTIFACT_ID, // <1>
		stubsMode = StubRunnerProperties.StubsMode.LOCAL // <2>
)
public class StubRunnerCustomerClientTest {

	final static String PRODUCER_ARTIFACT_ID = "rsb:producer";

	@Autowired
	private CustomerClient client;

	@StubRunnerPort(StubRunnerCustomerClientTest.PRODUCER_ARTIFACT_ID)
	private int portOfProducerService; // <3>

	@Test
	public void getAllCustomers() {

		var base = "localhost:" + this.portOfProducerService;
		this.client.setBase(base);
		log.info("setBase( " + base + ')');

		Flux<Customer> customers = this.client.getAllCustomers();
		StepVerifier //
				.create(customers) //
				.expectNext(new Customer("1", "Jane")) //
				.expectNext(new Customer("2", "John")) //
				.verifyComplete();
	}

}