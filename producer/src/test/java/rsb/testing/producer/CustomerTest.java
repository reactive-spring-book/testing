package rsb.testing.producer;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTest {

	@Test
	public void create() {
		Customer customer = new Customer("123", "foo"); // <1>
		Assertions.assertEquals(customer.id(), "123"); // <2>
		org.hamcrest.MatcherAssert.assertThat(customer.id(), Matchers.is("123")); // <3>
		Assertions.assertEquals(customer.name(), "foo"); // <4>
	}

}
