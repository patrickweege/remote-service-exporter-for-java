package rse4j.demo.webapp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import rse4j.demo.webapp.servlet.RSE4JDemoServlet;


@SpringBootApplication
@ServletComponentScan(basePackageClasses = { RSE4JDemoServlet.class})
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	
}
