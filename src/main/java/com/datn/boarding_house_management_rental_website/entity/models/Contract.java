package com.datn.boarding_house_management_rental_website.entity.models;

import com.datn.boarding_house_management_rental_website.entity.enums.ContractStatus;
import com.datn.boarding_house_management_rental_website.entity.enums.RoomStatus;
import com.datn.boarding_house_management_rental_website.entity.models.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "contract")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String files;
    @Column(name = "name_of_rent")
    private String nameOfRent;
    @Column(name = "deadline_contract")
    private LocalDateTime deadlineContract;
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "num_of_people")
    private Long numOfPeople;

    private String phone;
    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
	public Contract(String name, String files, String nameOfRent, LocalDateTime deadlineContract, String createdBy,
			String updatedBy, Long numOfPeople, String phone,ContractStatus contractStatus,LocalDateTime createdAt, Room room, User user) {
		super();
		this.name = name;
		this.files = files;
		this.nameOfRent = nameOfRent;
		this.deadlineContract = deadlineContract;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.numOfPeople = numOfPeople;
        this.status = contractStatus;
        this.createdAt = createdAt;
		this.phone = phone;
		this.room = room;
		this.user = user;
	}
}
