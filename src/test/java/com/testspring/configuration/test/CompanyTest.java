package com.testspring.configuration.test;

import com.testspring.configuration.entity.Company;
import com.testspring.configuration.service.CompanyService;
import com.testspring.configuration.test.config.MainConfigTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MainConfigTest.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyTest {

    @Autowired
    private CompanyService companyService;

    @Test
    public void test(){
        List<Company> company = companyService.all();
        Assert.assertEquals(0, company.size());
    }
}
