package com.ucoban.medsoft.accountserver.util;

import com.ucoban.medsoft.accountserver.dao.client.IDepartmentFeignClient;
import com.ucoban.medsoft.accountserver.dao.client.IDocumentFeignClient;
import com.ucoban.medsoft.accountserver.dao.implementation.AccountServiceImpl;
import com.ucoban.medsoft.accountserver.dto.DepartmentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountMapperHelper {
	private final IDepartmentFeignClient departmentFeignClient;
	
	private final IDocumentFeignClient documentFeignClient;
	
	public AccountMapperHelper(IDepartmentFeignClient departmentFeignClient, IDocumentFeignClient documentFeignClient) {
		this.departmentFeignClient = departmentFeignClient;
		this.documentFeignClient = documentFeignClient;
	}
	
	private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	public String getPhoto(String userId) {
		try {
			var response = documentFeignClient.getUserProfilPhoto(userId).getBody();
			return response != null ? response.data() : null;
		} catch (Exception ex) {
			System.out.println("getPhoto client error: " + ex.getMessage());
			return null;
		}
	}
	
	public List<DepartmentDto> getDepartments(String userId) {
		try {
			var response = departmentFeignClient.getDepartmentsByUserId(userId);
			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody().data();
			} else {
				return List.of();
			}
		} catch (Exception ex) {
			System.out.println("getDepartments client error: " + ex.getMessage());
			return List.of();
		}
	}
}
