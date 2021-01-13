package com.udemy.spring.cloud.service.item.app.model.data;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {

    private Long id;
    private String name;
    private Double price;
    private Date createAt;
    private Integer serverPort;

}
