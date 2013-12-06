package springconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.spring.configuration.RootContextConfiguration;


@Configuration
@ImportResource("classpath:/springconfig/*.xml")
//@Import(RootContextConfiguration.class)
public class RestApiContextConfiguration {
}
