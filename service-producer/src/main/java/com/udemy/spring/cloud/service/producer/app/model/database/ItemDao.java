package com.udemy.spring.cloud.service.producer.app.model.database;

import com.udemy.spring.cloud.commons.model.data.Item;

import org.springframework.data.repository.CrudRepository;

public interface ItemDao extends CrudRepository<Item, Long> {

}
