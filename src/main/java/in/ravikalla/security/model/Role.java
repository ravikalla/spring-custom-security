package in.ravikalla.security.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	private static final long serialVersionUID = 8263752555419067036L;

	private String role;

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

	@Override
	public String getAuthority() {
		return role;
	}
}
