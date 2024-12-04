package com.datn.boarding_house_management_rental_website.entity.enums;

public enum ContractStatus {
    REQUEST("REQUEST"),
    ROOM_RENT("ROOM_RENT"),
    RESERVATION("RESERVATION"),
    CHECKOUT("CHECKOUT");


    private String value;

    ContractStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
