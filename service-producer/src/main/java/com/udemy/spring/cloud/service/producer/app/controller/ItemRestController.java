package com.udemy.spring.cloud.service.producer.app.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.udemy.spring.cloud.commons.model.data.Item;
import com.udemy.spring.cloud.service.producer.app.service.IItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemRestController {

    @Autowired
    private Environment environment;

    @Autowired
    private IItemService iItemService;

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping("/findAll")
    public List<Item> findAll() {
        return iItemService.findAll().stream().map(item -> {
            // item.setServerPort(serverPort);
            item.setServerPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return item;
        }).collect(Collectors.toList());
    }

    @GetMapping("/findById/{id}")
    public Item findAll(@PathVariable Long id) throws InterruptedException {

        if (id.equals(10L)) {
            // Error simulation
            throw new IllegalStateException("Item not found");
        } else if (id.equals(10L)) {
            // Timeout simulation
            TimeUnit.SECONDS.sleep(5L);
        }

        Item item = iItemService.findById(id);
        item.setServerPort(Integer.parseInt(environment.getProperty("local.server.port")));

        // Error simulation
        // boolean ok = false;
        // if (!ok) {
        // throw new RuntimeException("Error simulation");
        // }

        return item;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody Item item) {
        return iItemService.save(item);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Item update(@PathVariable Long id, @RequestBody Item item) {
        Item itemDB = iItemService.findById(id);
        itemDB.setName(item.getName());
        itemDB.setPrice(item.getPrice());
        return iItemService.save(itemDB);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        iItemService.deleteById(id);
    }

}
