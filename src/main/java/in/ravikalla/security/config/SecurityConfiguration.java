package in.ravikalla.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	private Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

	private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
	@Autowired
    public void setCustomBasicAuthenticationEntryPoint(CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint) {
		this.customBasicAuthenticationEntryPoint = customBasicAuthenticationEntryPoint;
	}

	@Autowired
    private CustomAuthenticationProvider demoAuthenticationProvider;
	public void setDemoAuthenticationProvider(CustomAuthenticationProvider demoAuthenticationProvider) {
		this.demoAuthenticationProvider = demoAuthenticationProvider;
	}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	logger.info("43 : Start : SecurityConfiguration.configure(...)");

        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authz -> authz
            .requestMatchers("/checkHeader/**").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic(basic -> basic
            .authenticationEntryPoint(customBasicAuthenticationEntryPoint)
        );
        http.addFilterBefore(new CustomAuthenticationFilter(), BasicAuthenticationFilter.class);

        logger.info("53 : End : SecurityConfiguration.configure(...)");
        return http.build();
    }
}
