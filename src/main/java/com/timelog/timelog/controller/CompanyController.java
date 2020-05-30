package com.timelog.timelog.controller;

import com.timelog.timelog.exceptions.CompanyNotFoundException;
import com.timelog.timelog.models.Company;
import com.timelog.timelog.repositories.CompanyRepository;
import com.timelog.timelog.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.timelog.timelog.constants.TimeLogConstants.COMPANIES_PATH;
import static com.timelog.timelog.constants.TimeLogConstants.TIME_LOG_V1_PATH;

@RestController
@RequestMapping(TIME_LOG_V1_PATH)
public class CompanyController {

    private CompanyRepository companyRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(COMPANIES_PATH)
    public ResponseEntity<List<Company>> getCompanyList(@RequestParam(required = false)
                                        Set<String> companyList) {

        if (companyList == null || companyList.isEmpty()) {

            return new ResponseEntity<>(companyRepository.findAll(), HttpStatus.OK);

        } else {

            Optional<List<Company>> optionalList = companyRepository.findByIdList(companyList);
            if (!optionalList.isPresent()) {

                throw new CompanyNotFoundException("");
            }
            return new ResponseEntity<>(optionalList.get(), HttpStatus.OK);
        }
    }

    @GetMapping(COMPANIES_PATH + "/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String id) {

        Optional<Company> optionalResponse = companyRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new CompanyNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }


    @PostMapping(COMPANIES_PATH)
    public @ResponseBody ResponseEntity<Company> addCompany(@Validated @RequestBody Company company) {
        companyRepository.save(company);
        return new ResponseEntity(company, HttpStatus.OK);
    }


    @DeleteMapping(COMPANIES_PATH + "/{id}")
    public void deleteCompany(@PathVariable("id") String id) {

        Optional<Company> optionalResponse = companyRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new CompanyNotFoundException(id);
        }
        companyRepository.deleteById(id);
    }

    @PutMapping(COMPANIES_PATH + "/{id}")
    public ResponseEntity<Company> updateCompanyById(@RequestBody Company company, @PathVariable("id") String id) {

        Optional<Company> optionalResponse = companyRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new CompanyNotFoundException(id);
        }
        companyRepository.save(company);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }



}
