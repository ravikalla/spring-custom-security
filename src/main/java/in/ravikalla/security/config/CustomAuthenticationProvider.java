package in.ravikalla.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import in.ravikalla.security.exception.UnknownUserException;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		CustomAuthenticationToken customAuthentication = (CustomAuthenticationToken) authentication;
		logger.info("21 : Start : CustomAuthenticationProvider.authenticate(...) : Principal = " + customAuthentication.getPrincipal());

		if (customAuthentication.getPrincipal() == null) {
			throw new UnknownUserException("Could not find user");
		}

		logger.info("27 : End : CustomAuthenticationProvider.authenticate(...)");
		return customAuthentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
