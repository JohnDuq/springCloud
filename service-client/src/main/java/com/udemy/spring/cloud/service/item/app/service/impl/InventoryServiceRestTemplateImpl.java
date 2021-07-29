package com.udemy.spring.cloud.service.item.app.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.udemy.spring.cloud.commons.model.data.Item;
import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
import com.udemy.spring.cloud.service.item.app.service.IInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("InventoryServiceRestTemplateImpl")
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

    @Override
    public Item save(Item item) {
        HttpEntity<Item> body = new HttpEntity<Item>(item);
        ResponseEntity<Item> responseHttp = restTemplate.exchange("http://service-producer/create", HttpMethod.POST,
                body, Item.class);
        return responseHttp.getBody();
    }

    @Override
    public Item update(Long id, Item item) {
        Map<String, String> mPathVariables = new HashMap<>();
        mPathVariables.put("id", id.toString());
        HttpEntity<Item> body = new HttpEntity<Item>(item);
        ResponseEntity<Item> responseHttp = restTemplate.exchange("http://service-producer/update/{id}", HttpMethod.PUT,
                body, Item.class, mPathVariables);
        return responseHttp.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, String> mPathVariables = new HashMap<>();
        mPathVariables.put("id", id.toString());
        restTemplate.delete("http://service-producer/delete/{id}", mPathVariables);
    }

}
