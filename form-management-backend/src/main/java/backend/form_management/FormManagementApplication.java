package backend.form_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FormManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FormManagementApplication.class, args);
	}
<<<<<<< HEAD
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:5175") // Địa chỉ của ứng dụng Vue
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}

=======
>>>>>>> 693ee56747d3a26c6b354eedadc6c13c600c919d
}
