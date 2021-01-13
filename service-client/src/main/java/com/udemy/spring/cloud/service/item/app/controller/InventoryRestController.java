package com.udemy.spring.cloud.service.item.app.controller;

import java.util.List;

import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
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

    @GetMapping("/findByIdAmount/{id}/{amount}")
    public Inventory findById(@PathVariable Long id, @PathVariable Integer amount) {
        return iInventoryService.findById(id, amount);
    }

}
