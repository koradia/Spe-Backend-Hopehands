package com.example.hopehands;

//import com.example.noidea2.model.admin.Admin;
//import com.example.noidea2.repo.admin.AdminRepo;
import com.example.hopehands.model.auth.Creds;
import com.example.hopehands.repo.auth.CredsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Noidea2Application {
	@Autowired
	private CredsRepo credsRepo;

//	@Autowired
//	private AdminRepo adminRepo;
//
	@PostConstruct
	public void initAdmin(){
		Creds a=new Creds(1,"admin","admin",0);
		credsRepo.save(a);
	}
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("*");
//			}
//		};
//	}
//	@PostConstruct
//	public void initUser(){
//		List<DocCreds> users= Stream.of(
//				new DocCreds(101,"santhil","santhil@gmail.com","123"),
//				new DocCreds(102,"doc  tor1","doctor1@gmail.com","123")
//		).collect(Collectors.toList());
//		docCredsRepo.saveAll(users);
//	}
	public static void main(String[] args) {
		SpringApplication.run(Noidea2Application.class, args);
	}

}
