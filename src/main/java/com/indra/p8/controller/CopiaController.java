package com.indra.p8.controller;


import com.indra.p8.service.CopiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // viene en la dependencia WEB (spring-boot-starter-web) del pom
@RequestMapping("/copia")
public class CopiaController {
    @Autowired
    CopiaService service;
}
