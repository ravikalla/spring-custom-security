package in.ravikalla.security.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import in.ravikalla.security.model.User;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -1949976839306453197L;

	private User user;
	private String uid;

	public CustomAuthenticationToken(String uid) {
		super(Arrays.asList());
		this.uid = uid;
	}

	public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities, User authenticatedUser,
			String uid) {
		super(authorities);
		this.uid = uid;
		this.user = authenticatedUser;
	}

	@Override
	public Object getCredentials() {
		return user.getPassword();
	}

	@Override
	public Object getPrincipal() {
		return user;
	}

	public String getUid() {
		return uid;
	}

	public String toString() {
		return uid + " : " + user;
	}
}