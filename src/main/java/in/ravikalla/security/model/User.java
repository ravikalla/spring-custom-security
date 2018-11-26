package in.ravikalla.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class User implements Authentication{

	private static final long serialVersionUID = -851208307835968647L;

	private boolean isAuthenticated;
    private int id;
    private String email;
    private String password;
    private String name;
    private String lastName;
    private int active;
    private Set<Role> roles;

    public User(boolean isAuthenticated, int id, String email, String password, String name, String lastName, int active, Set<Role> roles) {
    	this();

    	this.isAuthenticated = isAuthenticated;
    	this.id = id;
    	this.email = email;
    	this.password = password;
    	this.name = name;
    	this.lastName = lastName;
    	this.active = active;
    	this.roles = roles;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<Role>(roles);
	}

	@Override
	public Object getCredentials() {
		return password;
	}

	@Override
	public Object getDetails() {
		return this;
	}

	@Override
	public Object getPrincipal() {
		return this;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

	public String toString() {
		StringBuffer strRoles = new StringBuffer();
		roles.forEach(role -> {
			strRoles.append(":").append(role);
		});
		return "User Object Content : " + isAuthenticated + " : " + id + " : " + email + " : " + name + " : " + lastName + " : " + active + " : " + strRoles.toString();
	}
}
