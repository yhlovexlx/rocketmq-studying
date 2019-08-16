package com.xiaoniucr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Panda implements Serializable {

    private static final long serialVersionUID = -8329854144202215775L;

    private String name;

    private Integer age;



}
