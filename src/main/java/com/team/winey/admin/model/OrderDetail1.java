package com.team.winey.admin.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
public class OrderDetail1 {
    private Long orderId;
    private String orderDate;
    private String email;
    private String nmKor; //와인한글이름
    private int salePrice; //와인개별금액
}
