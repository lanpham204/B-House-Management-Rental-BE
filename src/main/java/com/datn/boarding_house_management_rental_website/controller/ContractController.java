package com.datn.boarding_house_management_rental_website.controller;

import com.datn.boarding_house_management_rental_website.entity.enums.ContractStatus;
import com.datn.boarding_house_management_rental_website.services.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/contract")
@RequiredArgsConstructor
public class ContractController {
	private final ContractService contractService;

	@PostMapping
	private ResponseEntity<?> addContract(@RequestParam String name, @RequestParam Long roomId,
			@RequestParam String nameOfRent, @RequestParam Long numOfPeople, @RequestParam String phone,
			@RequestParam String deadlineContract, @RequestParam ContractStatus contractStatus,
			@RequestParam String createdAt, @RequestParam(required = false) String file) {
		return ResponseEntity.ok(contractService.addContract(name, roomId, nameOfRent, numOfPeople, phone,
				deadlineContract, contractStatus, createdAt, file));
	}

	@GetMapping
	private ResponseEntity<?> getAllContract(@RequestParam(required = false) String name,
			@RequestParam(required = false) String phone, @RequestParam(required = false) ContractStatus status,
			@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
		return ResponseEntity.ok(contractService.getAllContractOfRentaler(name, phone, status, pageNo, pageSize));
	}

	@GetMapping("/customer")
	private ResponseEntity<?> getAllContractForCustomer(@RequestParam(required = false) Long userId,
			@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
		return ResponseEntity.ok(contractService.getAllContractOfCustomer(userId, pageNo, pageSize));
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> getContractById(@PathVariable Long id) {
		return ResponseEntity.ok(contractService.getContractById(id));
	}

	@PutMapping("/{id}")
	private ResponseEntity<?> updateContractInfo(@PathVariable Long id, @RequestParam String name,
			@RequestParam Long roomId, @RequestParam String nameOfRent, @RequestParam Long numOfPeople,
			@RequestParam String phone, @RequestParam String deadlineContract, @RequestParam String createdAt,
			@RequestParam ContractStatus contractStatus, @RequestParam(required = false) String file) {
		return ResponseEntity.ok(contractService.editContractInfo(id, name, roomId, nameOfRent, numOfPeople, phone,
				deadlineContract, contractStatus, createdAt, file));
	}

	@DeleteMapping("/{id}")
	private ResponseEntity<?> deleteContract(@PathVariable Long id) {
		return ResponseEntity.ok(contractService.deleteContract(id));
	}

	@PostMapping("/{id}/checkout")
	public ResponseEntity<?> checkoutRoom(@PathVariable Long id) {
		return ResponseEntity.ok(contractService.checkoutRoom(id));
	}
}
