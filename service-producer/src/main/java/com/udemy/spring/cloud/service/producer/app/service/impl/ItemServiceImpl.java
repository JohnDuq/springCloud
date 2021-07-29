package com.udemy.spring.cloud.service.producer.app.service.impl;

import java.util.List;

import com.udemy.spring.cloud.commons.model.data.Item;
import com.udemy.spring.cloud.service.producer.app.model.database.ItemDao;
import com.udemy.spring.cloud.service.producer.app.service.IItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemServiceImpl implements IItemService {

    @Autowired
    private ItemDao itemDao;

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return (List<Item>) itemDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Item findById(Long id) {
        return itemDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Item save(Item item) {
        return itemDao.save(item);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        itemDao.deleteById(id);
    }

}
