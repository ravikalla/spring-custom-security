package in.ravikalla.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import in.ravikalla.security.config.CustomBasicAuthenticationEntryPoint;
import in.ravikalla.security.config.CustomAuthenticationProvider;

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
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(demoAuthenticationProvider);
        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("43 : Start : SecurityConfiguration.filterChain(...)");

        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("**/secured/**").permitAll()
                .anyRequest().authenticated());
        http.httpBasic(basic -> basic.authenticationEntryPoint(customBasicAuthenticationEntryPoint));
        http.addFilterBefore(new CustomAuthenticationFilter(), BasicAuthenticationFilter.class);

        logger.info("53 : End : SecurityConfiguration.filterChain(...)");
        return http.build();
    }
}
