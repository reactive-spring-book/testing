package com.example.producer;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CustomerTest {

	@Test
	public void create() throws Exception {
		Customer customer = new Customer("123", "foo");
		Assertions.assertThat(customer.getId()).isEqualToIgnoringWhitespace("123");
		Assertions.assertThat(customer.getName()).isEqualToIgnoringWhitespace("foo");
	}

}
