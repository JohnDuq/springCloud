package com.udemy.spring.cloud.service.producer.app.service;

import java.util.List;

import com.udemy.spring.cloud.commons.model.data.Item;

public interface IItemService {

    public List<Item> findAll();

    public Item findById(Long id);

    public Item save(Item item);

    public void deleteById(Long id);

}
