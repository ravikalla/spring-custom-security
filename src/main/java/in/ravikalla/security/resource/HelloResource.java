package in.ravikalla.security.resource;


import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ravikalla.security.model.User;

@RequestMapping("/rest/hello")
@RestController
public class HelloResource {

	private Logger logger = LoggerFactory.getLogger(HelloResource.class);

    @GetMapping("/all")
    public String hello() {
    	logger.info("27 : Start : HelloResource.hello()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (null != user)
        	return "All : " + user;
        else
        	return "All";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secured/all")
    public String securedHello() {
    	logger.info("22 : Start : HelloResource.securedHello()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        if (null != user)
        	return "Secured All : " + user;
        else
        	return "Secured All";
    }

    @GetMapping("/secured/alternate")
    public String alternate() {
    	logger.info("28 : Start : HelloResource.alternate()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null != user) {
        	Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        	authorities.forEach(authority -> {
            	System.out.println("53 : authority.getAuthority() = " + authority.getAuthority());
        	});
        	return "Alternate : " + user;
        }
        else
        	return "Alternate";
    }
}
