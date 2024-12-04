package com.datn.boarding_house_management_rental_website.services.impl;

import com.datn.boarding_house_management_rental_website.entity.enums.ContractStatus;
import com.datn.boarding_house_management_rental_website.entity.enums.LockedStatus;
import com.datn.boarding_house_management_rental_website.entity.enums.RoomStatus;
import com.datn.boarding_house_management_rental_website.entity.models.Contract;
import com.datn.boarding_house_management_rental_website.entity.models.Room;
import com.datn.boarding_house_management_rental_website.entity.models.User;
import com.datn.boarding_house_management_rental_website.entity.payload.response.ContractResponse;
import com.datn.boarding_house_management_rental_website.entity.payload.response.MessageResponse;
import com.datn.boarding_house_management_rental_website.exception.BadRequestException;
import com.datn.boarding_house_management_rental_website.repository.ContractRepository;
import com.datn.boarding_house_management_rental_website.repository.RoomRepository;
import com.datn.boarding_house_management_rental_website.repository.UserRepository;
import com.datn.boarding_house_management_rental_website.services.BaseService;
import com.datn.boarding_house_management_rental_website.services.ContractService;
import com.datn.boarding_house_management_rental_website.services.FileStorageService;
import com.datn.boarding_house_management_rental_website.utils.MapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl extends BaseService implements ContractService {

	private final ContractRepository contractRepository;
	private final RoomRepository roomRepository;
	private final FileStorageService fileStorageService;
	private final MapperUtils mapperUtils;

	@Override
	public MessageResponse addContract(String name, Long roomId, String nameRentHome, Long numOfPeople, String phone,
									   String deadline, ContractStatus contractStatus,String createdAt, List<MultipartFile> files) {
		Room room = roomRepository.findById(roomId)
				.orElseThrow(() -> new BadRequestException("Phòng đã không tồn tại"));
		User user = getUserRepository().findByPhone(phone).orElseThrow(() -> new BadRequestException("User không tồn tại"));
		if (room.getIsLocked().equals(LockedStatus.DISABLE)) {
			throw new BadRequestException("Phòng đã bị khóa");
		}
		Contract contract;
		if(files != null) {
			String file = fileStorageService.storeFile(files.get(0)).replace("photographer/files/", "");
			contract = new Contract(name, "http://localhost:8080/document/" + file, nameRentHome,
					LocalDateTime.parse(deadline), user.getEmail(), user.getEmail(), numOfPeople,
					phone,contractStatus,LocalDateTime.parse(createdAt), room, user);
		} else {
			contract = new Contract(name, "", nameRentHome,
					LocalDateTime.parse(deadline), user.getEmail(), user.getEmail(), numOfPeople,
					phone,contractStatus,LocalDateTime.parse(createdAt), room, user);
		}
		contractRepository.save(contract);
		return MessageResponse.builder().message("Thêm hợp đồng mới thành công").build();
	}

	@Override
	public MessageResponse deleteContract(Long id) {
		Contract contract = contractRepository.findById(id).orElseThrow(() -> new BadRequestException("Hợp đồng không tồn tại"));
		contractRepository.delete(contract);
		return MessageResponse.builder().message("Xóa hợp đồng thành công").build();
	}

	@Override
	public Page<ContractResponse> getAllContractOfRentaler(String name, String phone,ContractStatus status, Integer pageNo,
			Integer pageSize) {
		int page = pageNo == 0 ? pageNo : pageNo - 1;
		Pageable pageable = PageRequest.of(page, pageSize);
		return mapperUtils.convertToResponsePage(
				contractRepository.searchingContact(name, phone,status, getUserId(), pageable), ContractResponse.class,
				pageable);
	}

	@Override
	public MessageResponse checkoutRoom(Long id) {
		Contract contract = contractRepository.findById(id).orElseThrow(() -> new BadRequestException("Hợp đồng không tồn tại"));
		contract.setStatus(ContractStatus.CHECKOUT);
		contractRepository.save(contract);
		return MessageResponse.builder().message("Trả phòng và xuất hóa đơn thành công.").build();
	}

	@Override
	public ContractResponse getContractById(Long id) {
		return mapperUtils.convertToResponse(
				contractRepository.findById(id).orElseThrow(() -> new BadRequestException("Hợp đồng không tồn tại!")),
				ContractResponse.class);
	}

	@Override
	public MessageResponse editContractInfo(Long id, String name, Long roomId, String nameOfRent, Long numOfPeople,
			String phone, String deadlineContract, ContractStatus contractStatus,String createdAt, List<MultipartFile> files) {
		Room room = roomRepository.findById(roomId)
				.orElseThrow(() -> new BadRequestException("Phòng đã không tồn tại"));
		User user = getUserRepository().findByPhone(phone).orElseThrow(() -> new BadRequestException("User không tồn tại"));

		if (room.getIsLocked().equals(LockedStatus.DISABLE)) {
			throw new BadRequestException("Phòng đã bị khóa");
		}

		Contract contract = contractRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Hợp đồng không tồn tại!"));
		contract.setDeadlineContract(LocalDateTime.parse(deadlineContract));
		contract.setRoom(room);
		contract.setName(name);
		if (files != null && Objects.nonNull(files.get(0))) {
			String file = fileStorageService.storeFile(files.get(0)).replace("photographer/files/", "");
			contract.setFiles("http://localhost:8080/document/" + file);
		}
		contract.setNameOfRent(nameOfRent);
		contract.setNumOfPeople(numOfPeople);
		contract.setPhone(phone);
		contract.setStatus(contractStatus);
		contract.setCreatedAt(LocalDateTime.parse(createdAt));
		contract.setUpdatedBy(user.getEmail());
		contractRepository.save(contract);
		return MessageResponse.builder().message("Cập nhật hợp đồng thành công.").build();
	}

	@Override
	public Page<ContractResponse> getAllContractOfCustomer(Long userId, Integer pageNo, Integer pageSize) {
		User user = getUserRepository().findById(userId).orElseThrow(() -> new BadRequestException("User không tồn tại"));
		int page = pageNo == 0 ? pageNo : pageNo - 1;
		Pageable pageable = PageRequest.of(page, pageSize);
		return mapperUtils.convertToResponsePage(contractRepository.findByUserId(userId, pageable),
				ContractResponse.class, pageable);
	}



}
