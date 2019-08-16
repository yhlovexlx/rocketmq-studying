package com.xiaoniucr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {


    private static final long serialVersionUID = -5122219313493536212L;

    private Integer id;

    private String productName;

    private Double price;

    private String userName;

    private Integer status;


}
