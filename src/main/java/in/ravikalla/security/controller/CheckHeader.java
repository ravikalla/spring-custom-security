package in.ravikalla.security.controller;


import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ravikalla.security.model.User;
import in.ravikalla.security.util.Constants;

@RequestMapping(Constants.CONTROLLER_CHECK_HEADER)
@RestController
public class CheckHeader {

	private Logger logger = LoggerFactory.getLogger(CheckHeader.class);

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

    @GetMapping(Constants.METHOD_HEADER_LOOKUP + "/{headerLookupKey}")
    public String checkHeader(@PathVariable("headerLookupKey") String headerLookupKey, @RequestHeader Map<String, String> headers) {
    	logger.info("43 : Start : HelloResource.checkHeader() : headerKey = {}", headerLookupKey);
    	String headerVal = null;
    	for (Entry<String, String> entry : headers.entrySet()) {
    		logger.info("46 : Header Key = {}, Value = {}", entry.getKey(), entry.getValue());
    		if (entry.getKey().equals(headerLookupKey))
    			headerVal = entry.getValue();
    	}
    	logger.info("50 : End : HelloResource.checkHeader() : headerKey = {}, headerVal = {}", headerLookupKey, headerVal);
        return headerVal;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN1')")
    @GetMapping("/secured/alternate")
    public String alternate() {
    	logger.info("28 : Start : HelloResource.alternate()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        if (null != user) {
        	Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        	authorities.forEach(authority -> {
            	logger.info("57 : HelloResource.alternate() : authority.getAuthority() = " + authority.getAuthority());
        	});
        	return "Alternate : " + user;
        }
        else
        	return "Alternate";
    }
}
