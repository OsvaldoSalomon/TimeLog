package com.timelog.timelog.repositories;

import com.timelog.timelog.models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompanyRepository extends MongoRepository<Company, String> {

    @Query("{'_id': {'$in':?0}}")
    Optional<List<Company>> findByIdList(Set<String> companyList);

}
