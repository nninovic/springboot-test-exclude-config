package com.testspring.configuration.service;

import com.testspring.configuration.entity.Company;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Override
    public List<Company> all() {
        return Collections.emptyList();
    }
}
