package lk.ijse.gdse67.springposbackend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "lk.ijse.gdse67.springposbackend")
@EnableWebMvc
public class WebAppConfig {
}
