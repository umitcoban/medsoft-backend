package com.ucoban.medsoft.departmentserver.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(
		name = "user_departments",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"user_id", "department_id"})
		})
@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
public class UserDepartment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String userId;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Department.class)
	@JoinColumn(name = "department_id", referencedColumnName = "id")
	private Department department;
}
