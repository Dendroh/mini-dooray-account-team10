package com.example.minidoorayaccount;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiniDoorayAccountApplicationTests {


	@Test
	void contextLoads() {
		Assertions.assertDoesNotThrow(() -> MiniDoorayAccountApplication.main(new String[] {}));
	}

}
