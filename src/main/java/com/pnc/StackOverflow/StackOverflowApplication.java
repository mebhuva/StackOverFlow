package com.pnc.StackOverflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootApplication
public class StackOverflowApplication  {
	public static void main(String[] args) {
		SpringApplication.run(StackOverflowApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		context.registerBean("com.pnc.StackOverflow.Event.CascadeSaveMongoEventListener",
//				CascadeSaveMongoEventListener.class, () -> new CascadeSaveMongoEventListener());
//
//
//		//context.registerShutdownHook();
//	}

}
