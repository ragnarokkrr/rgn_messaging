package org.rgn.jms.richards.ch09.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleJmsSender {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("app-context.xml");
		
		
		System.out.println("My Only friend, the end");
		
	}
	
}
