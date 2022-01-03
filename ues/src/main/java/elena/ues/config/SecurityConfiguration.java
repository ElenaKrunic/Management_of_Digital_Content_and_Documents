package elena.ues.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	  protected void configure(HttpSecurity http) throws Exception{
	        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers("/api/search/**", "/api/articles/**").permitAll();
	    }
	  
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {
		  System.out.println(">>>>>>>>>>>>>>>>> doslo ovde >>>>>>>>>>>>>>>>>>>>>>");
		  registry.addResourceHandler("/static/**").addResourceLocations("file:///C:/Users/lenovo/Desktop/UES_Project/projekat/ues/src/main/resources/static/");		  
	    }
}
