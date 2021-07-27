package com.udemy.spring.cloud.service.item.app.client;

import java.util.List;

import com.udemy.spring.cloud.service.item.app.model.data.Item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-producer")
public interface IServiceProducerFeignClient {

    @GetMapping("/findAll")
    public List<Item> findAll();

    @GetMapping("/findById/{id}")
    public Item findById(@PathVariable("id") Long id);

    @PostMapping("/create")
    public Item create(@RequestBody Item item);

    @PutMapping("/update/{id}")
    public Item update(@PathVariable("id") Long id, @RequestBody Item item);

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id);

}
