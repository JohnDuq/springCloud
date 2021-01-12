package com.udemy.spring.cloud.service.producer.app.controller;

import java.util.List;

import com.udemy.spring.cloud.service.producer.app.model.data.Item;
import com.udemy.spring.cloud.service.producer.app.service.IItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    
    @Autowired
    private IItemService iItemService;

    @GetMapping("/findAll")
    public List<Item> findAll() {
        return iItemService.findAll();
    }

    @GetMapping("/findById/{id}")
    public Item findAll(@PathVariable Long id) {
        return iItemService.findById(id);
    }

}
