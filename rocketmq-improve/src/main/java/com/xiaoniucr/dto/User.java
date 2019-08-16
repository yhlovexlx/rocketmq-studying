package com.xiaoniucr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {


    private static final long serialVersionUID = 693826965623270312L;

    private Integer id;

    private String name;

    private Integer age;

    private Integer sex;

    private Integer active;


}
