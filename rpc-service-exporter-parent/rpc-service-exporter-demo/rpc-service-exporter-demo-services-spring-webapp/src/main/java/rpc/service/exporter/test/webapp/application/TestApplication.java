package rpc.service.exporter.test.webapp.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import rpc.service.exporter.test.webapp.servlet.RPCServiceServlet;

@SpringBootApplication
@ServletComponentScan(basePackageClasses = {RPCServiceServlet.class })
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
