package org.rgn.messaging.poc.springremoting.service;

import org.springframework.stereotype.Service;

@Service(value = "HelloService")
public class HelloServiceImpl implements HelloService {

	public String sayHello(Person person) {
		String res = String.format("Hello, %s %s!", person.getFirstName(),
				person.getLastName());
		return res;

	}

	public String sayGoodBye(Person person) {
		String res = String.format("Goodbye, %s %s!", person.getFirstName(),
				person.getLastName());
		return res;
	}

}
