package com.udemy.spring.cloud.service.item.app.controller;

import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
import com.udemy.spring.cloud.service.item.app.model.data.Item;
import com.udemy.spring.cloud.service.item.app.service.IInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryRestController {

    @Autowired
    private IInventoryService iInventoryService;

    @GetMapping("/findAll")
    public List<Inventory> findAll() {
        return iInventoryService.findAll();
    }

    @HystrixCommand(fallbackMethod = "fallbackMethodFindById")
    @GetMapping("/findByIdAmount/{id}/{amount}")
    public Inventory findById(@PathVariable Long id, @PathVariable Integer amount) {
        return iInventoryService.findById(id, amount);
    }

    public Inventory fallbackMethodFindById(Long id, Integer amount) {
        Item item = new Item();
        item.setId(id);
        item.setName("DEFAULT PRODUCT");
        item.setPrice(Double.valueOf(0));
        return new Inventory(item, amount);
    }

}
