package com.indra.p8.controller;


import com.indra.p8.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // viene en la dependencia WEB (spring-boot-starter-web) del pom
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    AutorService service;
}
