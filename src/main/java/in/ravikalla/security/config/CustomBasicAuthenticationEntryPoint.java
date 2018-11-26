package in.ravikalla.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CustomBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	private Logger logger = LoggerFactory.getLogger(CustomBasicAuthenticationEntryPoint.class);

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {
		logger.info("23 : Start : CustomBasicAuthenticationEntryPoint.commence(...)");

		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		final PrintWriter writer = response.getWriter();
		writer.println("HTTP Status " + HttpServletResponse.SC_UNAUTHORIZED + " - " + authException.getMessage());

		logger.info("30 : End : CustomBasicAuthenticationEntryPoint.commence(...)");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("35 : Start : CustomBasicAuthenticationEntryPoint.afterPropertiesSet()");

		setRealmName("Ravi Realm");
		super.afterPropertiesSet();

		logger.info("40 : End : CustomBasicAuthenticationEntryPoint.afterPropertiesSet()");
	}

}
