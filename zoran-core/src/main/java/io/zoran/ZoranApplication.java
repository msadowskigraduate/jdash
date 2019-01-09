package io.zoran;

import io.zoran.infrastructure.configuration.ZoranConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "io.zoran")
@EnableFeignClients
@Import(ZoranConfiguration.class)
public class ZoranApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoranApplication.class, args);
	}
}
