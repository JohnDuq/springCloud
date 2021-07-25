package com.udemy.spring.cloud.service.item.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
import com.udemy.spring.cloud.service.item.app.model.data.Item;
import com.udemy.spring.cloud.service.item.app.service.IInventoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryRestController {

    private static Logger log = LoggerFactory.getLogger(InventoryRestController.class);

    @Autowired
    private Environment env;

    @Autowired
    private IInventoryService iInventoryService;

    @GetMapping("/findAll")
    public List<Inventory> findAll() {
        return iInventoryService.findAll();
    }

    @HystrixCommand(fallbackMethod = "alternativeMethod")
    @GetMapping("/findByIdAmount/{id}/{amount}")
    public Inventory findById(@PathVariable Long id, @PathVariable Integer amount) {
        return iInventoryService.findById(id, amount);
    }

    public Inventory alternativeMethod(Long id, Integer amount) {
        Inventory inventory = new Inventory();
        inventory.setAmount(amount);
        Item item = new Item();
        item.setId(id);
        item.setName("Alternative Method");
        item.setPrice(500d);
        inventory.setItem(item);
        return inventory;
    }

    @GetMapping("/get-config")
    private ResponseEntity<?> getConfiguration(@Value("${server.port}") String serverPort,
            @Value("${text.configuration}") String textConfiguration) {
        log.info(String.format("textConfiguration : %s", textConfiguration));
        log.info(String.format("serverPort : %s", serverPort));
        Map<String, String> json = new HashMap<>();
        json.put("text.configuration", textConfiguration);
        json.put("server.port", serverPort);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("name.autor.configuration", env.getProperty("name.autor.configuration"));
            json.put("email.autor.configuration", env.getProperty("email.autor.configuration"));
        }
        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

}
