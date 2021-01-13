package com.udemy.spring.cloud.service.producer.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.udemy.spring.cloud.service.producer.app.model.data.Item;
import com.udemy.spring.cloud.service.producer.app.service.IItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemRestController {

    @Autowired
    private Environment environment;

    @Autowired
    private IItemService iItemService;

    @GetMapping("/findAll")
    public List<Item> findAll() {
        return iItemService.findAll()
        .stream()
        .map(item -> {
            item.setServerPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return item;
        }).collect(Collectors.toList());
    }

    @GetMapping("/findById/{id}")
    public Item findAll(@PathVariable Long id) {
        Item item = iItemService.findById(id);
        item.setServerPort(Integer.parseInt(environment.getProperty("local.server.port")));
        return item;
    }

}
