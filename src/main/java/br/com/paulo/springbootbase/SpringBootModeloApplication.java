package br.com.paulo.springbootbase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootModeloApplication extends SpringBootServletInitializer  {

	private static final Logger log = LoggerFactory.getLogger(SpringBootModeloApplication.class);

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringBootModeloApplication.class, "--spring.profiles.active=dev");
		
        log.info("Contexto: {}", ctx.getApplicationName());
        log.info("H2: {}/{}", ctx.getApplicationName(), "h2-console");
        log.info("Docs api: {}/{}", ctx.getApplicationName(), "swagger-ui.html");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringBootModeloApplication.class);
	}
}
