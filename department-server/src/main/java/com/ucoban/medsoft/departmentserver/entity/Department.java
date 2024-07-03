package com.ucoban.medsoft.departmentserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@NoArgsConstructor
public class Department extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	private String description;
	
	@OneToMany( mappedBy = "department", targetEntity = UserDepartment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserDepartment> userDepartments;
}
