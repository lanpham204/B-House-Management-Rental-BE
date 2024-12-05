package com.datn.boarding_house_management_rental_website.services;

import com.datn.boarding_house_management_rental_website.entity.enums.ContractStatus;
import com.datn.boarding_house_management_rental_website.entity.payload.response.ContractResponse;
import com.datn.boarding_house_management_rental_website.entity.payload.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface ContractService {
	MessageResponse addContract(String name, Long roomId, String nameRentHome, Long numOfPeople, String phone,
			String deadline, ContractStatus contractStatus, String createdAt, String file);

	MessageResponse deleteContract(Long id);

	Page<ContractResponse> getAllContractOfRentaler(String name, String phone, ContractStatus status, Integer pageNo,
			Integer pageSize);

	MessageResponse checkoutRoom(Long id);

	ContractResponse getContractById(Long id);

	MessageResponse editContractInfo(Long id, String name, Long roomId, String nameOfRent, Long numOfPeople,
			String phone, String deadlineContract, ContractStatus contractStatus, String createdAt,
			String file);

	Page<ContractResponse> getAllContractOfCustomer(Long userId, Integer pageNo, Integer pageSize);
}
