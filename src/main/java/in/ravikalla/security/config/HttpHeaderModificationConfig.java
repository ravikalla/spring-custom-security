package in.ravikalla.security.config;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import static in.ravikalla.security.util.Constants.CUSTOM_HEADER_NAME;
@Configuration
public class HttpHeaderModificationConfig implements Filter {

	private Logger logger = LoggerFactory.getLogger(HttpHeaderModificationConfig.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("Start : HttpHeaderModificationConfig.doFilter(...)");
		final HttpServletRequest req = (HttpServletRequest) request;
		// modify HTTP Request Header
		final HttpServletRequestWrapper reqWrapper = new HttpServletRequestWrapper(req) {

			@Override
			public String getHeader(String name) {
				if (CUSTOM_HEADER_NAME.equals(name)) {
					logger.info("41 : HttpHeaderModificationConfig.getHeader(...) : name = {}", name);
					for (Cookie cookie : req.getCookies())
						if (CUSTOM_HEADER_NAME.equals(cookie.getName()))
							return (cookie.getValue());
					return null;
				}
				return super.getHeader(name);
			}
			@Override
		    public Enumeration<String> getHeaderNames() {
		        List<String> names = Collections.list(super.getHeaderNames());
		        names.add(CUSTOM_HEADER_NAME);
		        return Collections.enumeration(names);
		    }
		};
		logger.info("End : HttpHeaderModificationConfig.doFilter(...) : After Changed with Name {}", reqWrapper.getHeader(CUSTOM_HEADER_NAME));
		chain.doFilter(reqWrapper, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("HttpHeaderModificationConfig.init(...)");
	}

	@Override
	public void destroy() {
		logger.info("HttpHeaderModificationConfig.destroy(...)");
	}
}