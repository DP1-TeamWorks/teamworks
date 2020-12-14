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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
	List<Interest> interests;

}
