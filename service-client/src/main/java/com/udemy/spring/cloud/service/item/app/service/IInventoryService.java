package com.udemy.spring.cloud.service.item.app.service;

import java.util.List;

import com.udemy.spring.cloud.commons.model.data.Item;
import com.udemy.spring.cloud.service.item.app.model.data.Inventory;

public interface IInventoryService {

    public List<Inventory> findAll();

    public Inventory findById(Long id, Integer amount);

    public Item save(Item item);

    public Item update(Long id, Item item);

    public void delete(Long id);

}
