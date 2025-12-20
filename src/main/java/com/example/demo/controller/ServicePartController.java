package com.example.demo.controller;

import com.example.demo.model.ServicePart;
import com.example.demo.service.ServicePartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-parts")
public class ServicePartController {

    @Autowired
    private ServicePartService servicePartService;

    @PostMapping
    public ServicePart createPart(@RequestBody ServicePart part) {
        return servicePartService.createPart(part);
    }
}
