package com.bta.repository;

import java.util.List;
import java.util.Optional;

import com.bta.model.Country;

public interface CountryRepository {
    List<Country> findAll();

    Optional<Country> findOne(Long id);

    int delete(Long id);

    int save(Country country);
}
