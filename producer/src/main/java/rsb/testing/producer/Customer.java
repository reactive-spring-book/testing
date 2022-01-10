package rsb.testing.producer;

import org.springframework.data.annotation.Id;

// <1>
record Customer(@Id String id, String name) {
}