package in.ravikalla.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(demoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	logger.info("43 : Start : SecurityConfiguration.configure(...)");

        http.csrf().disable();
        http.authorizeRequests().antMatchers("**/secured/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(customBasicAuthenticationEntryPoint);
        http.addFilterBefore(new CustomAuthenticationFilter(), BasicAuthenticationFilter.class);

        logger.info("53 : End : SecurityConfiguration.configure(...)");
    }
}
