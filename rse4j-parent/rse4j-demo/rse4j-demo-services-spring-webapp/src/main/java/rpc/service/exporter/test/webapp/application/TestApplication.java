package rpc.service.exporter.test.webapp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import rpc.service.exporter.test.webapp.servlet.RPCServiceServlet;
import rpc.service.exporter.test.webapp.servlet.security.CheckLoginServlet;


@SpringBootApplication
@ServletComponentScan(basePackageClasses = { RPCServiceServlet.class, CheckLoginServlet.class })
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	
}
