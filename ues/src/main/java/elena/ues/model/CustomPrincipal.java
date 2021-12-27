package elena.ues.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.util.Assert;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomPrincipal implements UserDetails {
	
	   private String password;
	   private final Long id;
	   private final String username;
	   private final String firstName;
	   private final String lastName;
	   private final HashSet<GrantedAuthority> authorities;
	   private final boolean accountNonExpired;
	   private final boolean accountNonLocked;
	   private final boolean credentialsNonExpired;
	   private final boolean enabled;
	   
	   public CustomPrincipal(Long id, String username, String firstName, String lastName, String password, Collection<? extends GrantedAuthority> authorities) {
	        this(id, username, firstName, lastName,  password, (HashSet<GrantedAuthority>) authorities, true, true, true, true);
	    }

	    public CustomPrincipal(Long id, String username, String firstName, String lastName, String password, HashSet<GrantedAuthority> authorities,
	                           boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
	        Assert.isTrue(id != null && username != null && !"".equals(username) && password != null,
	                "Cannot pass null or empty values to constructor");

	        this.id = id;
	        this.username = username;
	        this.password = password;
	        this.firstName = firstName;
	        this.lastName = lastName;
	        this.authorities = authorities;
	        this.accountNonExpired = accountNonExpired;
	        this.accountNonLocked = accountNonLocked;
	        this.credentialsNonExpired = credentialsNonExpired;
	        this.enabled = enabled;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return this.authorities;
	    }

	    public Long getId() {
	        return this.id;
	    }

	    @Override
	    public String getPassword() {
	        return this.password;
	    }

	    @Override
	    public String getUsername() {
	        return this.username;
	    }

	    public String getFirstName() {
	        return this.firstName;
	    }

	    public String getLastName() {
	        return this.lastName;
	    }

	    @Override
	    public String toString() {
	        return "CustomPrincipal{" +
	                "password='" + password + '\'' +
	                ", id=" + id +
	                ", username='" + username + '\'' +
	                ", firstName='" + firstName + '\'' +
	                ", lastName='" + lastName + '\'' +
	                ", authorities=" + authorities +
	                ", accountNonExpired=" + accountNonExpired +
	                ", accountNonLocked=" + accountNonLocked +
	                ", credentialsNonExpired=" + credentialsNonExpired +
	                ", enabled=" + enabled +
	                '}';
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return this.accountNonExpired;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return this.accountNonLocked;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return this.credentialsNonExpired;
	    }

	    @Override
	    public boolean isEnabled() {
	        return this.enabled;
	    }


}
