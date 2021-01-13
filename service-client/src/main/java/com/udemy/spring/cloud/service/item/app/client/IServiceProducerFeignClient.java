package com.udemy.spring.cloud.service.item.app.client;

import java.util.List;

import com.udemy.spring.cloud.service.item.app.model.data.Item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-producer")
public interface IServiceProducerFeignClient {

    @GetMapping("/findAll")
    public List<Item> findAll();

    @GetMapping("/findById/{id}")
    public Item findById(@PathVariable("id") Long id);

}
