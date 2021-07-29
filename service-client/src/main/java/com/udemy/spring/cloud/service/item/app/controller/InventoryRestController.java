package com.udemy.spring.cloud.service.item.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.udemy.spring.cloud.commons.model.data.Item;
import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
import com.udemy.spring.cloud.service.item.app.service.IInventoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class InventoryRestController {

    private static final String EMAIL_AUTOR_CONFIGURATION = "email.autor.configuration";
    private static final String NAME_AUTOR_CONFIGURATION = "name.autor.configuration";

    private static Logger log = LoggerFactory.getLogger(InventoryRestController.class);

    @Autowired
    private Environment env;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    // sirve para instanciar una implementación especifica de la interfaz
    // @Qualifier("InventoryServiceRestTemplateImpl")
    private IInventoryService iInventoryService;

    @GetMapping("/findAll")
    public List<Inventory> findAll() {
        return iInventoryService.findAll();
    }

    // Define que en caso de error invoca el metodo alternativeMethod
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
    public ResponseEntity<?> getConfiguration(@Value("${text.configuration}") String textConfiguration) {
        log.info(String.format("textConfiguration : %s", textConfiguration));
        log.info(String.format("serverPort : %s", serverPort));
        Map<String, String> json = new HashMap<>();
        json.put("text.configuration", textConfiguration);
        json.put("server.port", serverPort);
        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put(NAME_AUTOR_CONFIGURATION, env.getProperty(NAME_AUTOR_CONFIGURATION));
            json.put(EMAIL_AUTOR_CONFIGURATION, env.getProperty(EMAIL_AUTOR_CONFIGURATION));
        }
        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody Item item) {
        return iInventoryService.save(item);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Item update(@PathVariable Long id, @RequestBody Item item) {
        return iInventoryService.update(id, item);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        iInventoryService.delete(id);
    }

}
