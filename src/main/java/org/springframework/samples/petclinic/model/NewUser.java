package org.springframework.samples.petclinic.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "newusers")
public class NewUser {
	@Id
	String username;

	String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	List<Interest> interests;

	/*boolean enabled;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;*/
}
