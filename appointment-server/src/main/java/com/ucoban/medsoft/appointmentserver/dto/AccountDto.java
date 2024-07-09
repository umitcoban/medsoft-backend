package com.ucoban.medsoft.appointmentserver.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class AccountDto {
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private LocalDate birthDate;
	private double weight;
	private short height;
	private int age;
	private String photo;
	private List<DepartmentDto> departments;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public AccountDto(String id, String firstName, String lastName, String email, String phone, LocalDate birthDate, double weight, short height, int age, String photo, List<DepartmentDto> departments, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.birthDate = birthDate;
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.photo = photo;
		this.departments = departments;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public AccountDto() {
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public short getHeight() {
		return height;
	}
	
	public void setHeight(short height) {
		this.height = height;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getPhoto() {
		return photo;
	}
	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public List<DepartmentDto> getDepartments() {
		return departments;
	}
	
	public void setDepartments(List<DepartmentDto> departments) {
		this.departments = departments;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
