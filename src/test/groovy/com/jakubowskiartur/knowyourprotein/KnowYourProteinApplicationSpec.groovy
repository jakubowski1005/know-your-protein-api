package com.jakubowskiartur.knowyourprotein

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class KnowYourProteinApplicationSpec extends Specification {


	void contextLoads() {
		expect:
			2 + 2 == 4
	}

}
