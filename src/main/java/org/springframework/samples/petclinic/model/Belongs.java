package org.springframework.samples.petclinic.model;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "belongs")

public class Belongs extends BaseEntity{
	
	
	
	@NotNull
	@NotEmpty
	@Column(name = "initialDate")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate initialDate;
	
	@NotNull
	@NotEmpty
	@Column(name = "finalDate")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate finalDate;
	
	@NotNull
	@NotEmpty
	@Column(name = "isDepartmentManager")
    private Boolean isDepartmentManager;
	
	//*@ManyToOne(optional=false )
	//@JoinColumn(name = "projects_id")
	//@JsonBackReference
	//private Project projects;
	
	//@ManyToOne
	//@MapsId("userId")
	//@JoinColumn(name = "userId")
	//@JsonBackReference
	//UserTW userTW;
	
	//@ManyToOne
	//@MapsId("departmentId")
	//@JoinColumn(name = "departmentId")
	//@JsonBackReference
	//Department department;

}
