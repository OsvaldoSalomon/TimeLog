package com.timelog.timelog.controller;

import com.google.common.collect.Lists;
import com.timelog.timelog.exceptions.CompanyNotFoundException;
import com.timelog.timelog.exceptions.CompanyPageParameterException;
import com.timelog.timelog.models.Company;
import com.timelog.timelog.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.timelog.timelog.constants.TimeLogConstants.COMPANIES_PATH;
import static com.timelog.timelog.constants.TimeLogConstants.TIME_LOG_V1_PATH;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(TIME_LOG_V1_PATH)
public class CompanyController {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(COMPANIES_PATH)
    public ResponseEntity<List<Company>> getPagedCompanyList(
            @RequestParam(value = "companyList", required = false) Set<String> requestedCompanyList,
            @RequestParam(name = "page", required = false/*, defaultValue = "0"*/) Integer page,
            @RequestParam(name = "size", required = false/*, defaultValue = "2"*/) Integer size) {

        List<Company> companyList;

        if (requestedCompanyList == null || requestedCompanyList.isEmpty()) {

            companyList = getPagedCompanyList(page, size);
        } else {

            companyList = getFilteredCompanyList(requestedCompanyList);
        }

        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    private List<Company> getFilteredCompanyList(Set<String> requestedCompanyList) {

        List<Company> companyList;
        Optional<List<Company>> optionalList = companyRepository.findByIdList(requestedCompanyList);
        if (!optionalList.isPresent()) {

            throw new CompanyNotFoundException("");
        }
        companyList = optionalList.get();
        return companyList;
    }

    private List<Company> getPagedCompanyList(Integer page, Integer size) {

        if (page == null ^ size == null) {
            throw new CompanyPageParameterException();
        }

        List<Company> companyList;
        if (page == null /*&& size == null*/)  {

            companyList = companyRepository.findAll();
        } else {

            Pageable pageable = PageRequest.of(page, size);
            Page<Company> requestedPage = companyRepository.findAll(pageable);
            companyList = Lists.newArrayList(requestedPage);
        }
        return companyList;
    }


//    @GetMapping(COMPANIES_PATH)
//    Page<Company> companiesPageable(Pageable pageable) {
//        return companyRepository.findAll(pageable);
//    }

    @GetMapping(COMPANIES_PATH + "/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable("id") String id)
    {
        Optional<Company> optionalResponse = companyRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new CompanyNotFoundException(id);
        }
        return new ResponseEntity<>(optionalResponse.get(), HttpStatus.OK);
    }


    @PostMapping(COMPANIES_PATH)
    public @ResponseBody ResponseEntity<Company> addCompany(@Validated @RequestBody Company company) {
        companyRepository.save(company);
        return new ResponseEntity<>(company, HttpStatus.OK);
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
    public ResponseEntity<Company> updateCompanyById(@Validated @RequestBody Company company, @PathVariable("id") String id) {

        Optional<Company> optionalResponse = companyRepository.findById(id);
        if (!optionalResponse.isPresent()) {

            throw new CompanyNotFoundException(id);
        }
        company.id = id;
        companyRepository.save(company);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

}