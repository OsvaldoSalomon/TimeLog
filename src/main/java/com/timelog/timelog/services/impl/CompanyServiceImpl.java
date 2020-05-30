package com.timelog.timelog.services.impl;

import com.timelog.timelog.models.Company;
import com.timelog.timelog.repositories.CompanyRepository;
import com.timelog.timelog.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

//    @Override
//    public void deleteCompany(String id) {
//        companyRepository.deleteById(id);
//    }
//
//    @Override
//    public Company findById(String id) {
//        return null;
//    }


}
