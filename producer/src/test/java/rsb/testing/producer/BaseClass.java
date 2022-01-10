package rsb.testing.producer;

import io.restassured.module.webtestclient.RestAssuredWebTestClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;

// <1>
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
public class BaseClass {

	// <3>
	@LocalServerPort
	private int port;

	// <4>
	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private RouterFunction<?>[] routerFunctions;

	@BeforeEach
	public void before() throws Exception {
		log.info("the embedded test web server is available on port " + this.port);

		// <5>
		Mockito.when(this.customerRepository.findAll())
				.thenReturn(Flux.just(new Customer("1", "Jane"), new Customer("2", "John")));

		// <6>
		RestAssuredWebTestClient.standaloneSetup(this.routerFunctions);
	}

	// <7>
	@Configuration
	@Import(ProducerApplication.class)
	public static class TestConfiguration {

	}

}
