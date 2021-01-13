package com.udemy.spring.cloud.service.item.app.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
import com.udemy.spring.cloud.service.item.app.model.data.Item;
import com.udemy.spring.cloud.service.item.app.service.IInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryServiceRestTemplateImpl implements IInventoryService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Inventory> findAll() {
        List<Item> lItems = Arrays.asList(restTemplate.getForObject("http://service-producer/findAll", Item[].class));
        return lItems.stream().map(item -> new Inventory(item, 1)).collect(Collectors.toList());
    }

    @Override
    public Inventory findById(Long id, Integer amount) {
        Map<String, String> mPathVariables = new HashMap<>();
        mPathVariables.put("id", id.toString());
        Item item = restTemplate.getForObject("http://service-producer/findById/{id}", Item.class, mPathVariables);
        return new Inventory(item, amount);
    }

}
