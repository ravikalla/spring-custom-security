package in.ravikalla.security.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import in.ravikalla.security.model.Role;
import in.ravikalla.security.model.User;

public class CustomAuthenticationFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("29 : Start : CustomAuthenticationFilter.doFilterInternal(...)");
//        String xAuth = request.getHeader("X-Authorization");

//        // validate the value in xAuth
//        if(isValid(xAuth) == false){
//            throw new SecurityException();
//        }

		String id = "DemoTokenID";
		Role objRole = new Role();
		objRole.setRole("ADMIN");
		Set<Role> setRole = new HashSet<Role>();
		setRole.add(objRole);
		User objUsers = new User();
		objUsers.setActive(1);
		objUsers.setEmail("test@test.com");
		objUsers.setRoles(setRole);
		objUsers.setName("test");
		objUsers.setLastName("test");
		objUsers.setId(1);
		objUsers.setPassword("test");
		objUsers.setAuthenticated(true);

		// Create our Authentication and let Spring know about it
		Authentication auth = new CustomAuthenticationToken(setRole, objUsers, id);
		SecurityContextHolder.getContext().setAuthentication(auth);

		filterChain.doFilter(request, response);

		logger.info("56 : End : CustomAuthenticationFilter.doFilterInternal(...)");
	}
}
