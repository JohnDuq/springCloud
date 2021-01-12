package com.udemy.spring.cloud.service.item.app.model.data;

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
public class Inventory {

    private Item item;
    private Integer amount;

    public Double getTotal() {
        return item.getPrice() * amount;
    }

}
