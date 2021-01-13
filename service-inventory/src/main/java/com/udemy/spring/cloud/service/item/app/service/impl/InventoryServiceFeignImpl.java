package com.udemy.spring.cloud.service.item.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.udemy.spring.cloud.service.item.app.client.IServiceProducerFeignClient;
import com.udemy.spring.cloud.service.item.app.model.data.Inventory;
import com.udemy.spring.cloud.service.item.app.service.IInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("inventoryServiceFeignImpl")
@Primary
public class InventoryServiceFeignImpl implements IInventoryService {

    @Autowired
    private IServiceProducerFeignClient iServiceProducerFeignClient;

    @Override
    public List<Inventory> findAll() {
        return iServiceProducerFeignClient.findAll().stream().map(item -> new Inventory(item, 1))
                .collect(Collectors.toList());
    }

    @Override
    public Inventory findById(Long id, Integer amount) {
        return new Inventory(iServiceProducerFeignClient.findById(id), amount);
    }

}
