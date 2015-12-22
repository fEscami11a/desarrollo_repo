package mx.com.invex.msi.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AUTHORITIES database table.
 * 
 */
@Entity
@Table(name="AUTHORITIES")
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	private String authority;

	//bi-directional many-to-one association to User
	@Id
    @ManyToOne
	@JoinColumn(name="USERNAME")
	private User user;

    public Authority() {
    }

	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}