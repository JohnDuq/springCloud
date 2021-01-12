package com.udemy.spring.cloud.service.producer.app.service;

import java.util.List;

import com.udemy.spring.cloud.service.producer.app.model.data.Item;

public interface IItemService {

    public List<Item> findAll();

    public Item findById(Long id);

}
