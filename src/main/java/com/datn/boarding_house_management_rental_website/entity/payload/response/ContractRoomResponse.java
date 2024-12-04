package com.datn.boarding_house_management_rental_website.entity.payload.response;


import com.datn.boarding_house_management_rental_website.entity.enums.ContractStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ContractRoomResponse {
    private Long id;
    private String name;
    private String files;
    private String nameOfRent;
    private LocalDateTime deadlineContract;
    private LocalDateTime createdAt;
    private ContractStatus status;
    private String phone;
    private Long numOfPeople;

}
