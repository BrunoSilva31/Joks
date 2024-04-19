package com.WorkJoks.Joks.config;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.WorkJoks.Joks.entities.User;
import com.WorkJoks.Joks.repositories.UserRepository;


@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		User u1 = new User(null, "Vero", "vero@email.com", "7894569", "999999", new Date(), "mezanino");
		
		userRepository.saveAll(Arrays.asList(u1));
	}
	
	
}