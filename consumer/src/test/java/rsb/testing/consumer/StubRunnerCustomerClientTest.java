package rsb.testing.consumer;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(//
		webEnvironment = SpringBootTest.WebEnvironment.MOCK, //
		classes = ConsumerApplication.class //
)
@RunWith(SpringRunner.class)
@Log4j2
@DirtiesContext
@AutoConfigureStubRunner(//
		ids = "rsb:producer", // <1>
		stubsMode = StubRunnerProperties.StubsMode.LOCAL // <2>
)
public class StubRunnerCustomerClientTest {

	@Autowired
	private CustomerClient client;

	@StubRunnerPort("rsb:producer")
	private int portOfProducerService;

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