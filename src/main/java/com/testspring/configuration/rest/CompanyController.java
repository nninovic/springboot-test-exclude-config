package com.testspring.configuration.rest;

import com.testspring.configuration.entity.Company;
import com.testspring.configuration.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {


    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all")
    public List<Company> all(){
        return companyService.all();
    }


}
