package com.bta;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.bta.model.Country;
import com.bta.repository.CountryRepository;
import com.bta.repository.postgre.CountryRepositoryPostGre;

public class Main {
    public static void main(String[] args) {
        final CountryRepository countryRepository = new CountryRepositoryPostGre();
        final List<Country> countries = countryRepository.findAll();
        System.out.println(countries.stream().map(Country::getId).collect(toList()));

        final Country country = countryRepository.findOne(10L).get();
        System.out.println(country);
        countryRepository.delete(9L);

        List<Country> countriesNew = countryRepository.findAll();
        System.out.println(countriesNew.stream().map(Country::getId).collect(toList()));

        Country country1 = new Country("RU", "Russia", "The biggest country");
        countryRepository.save(country1);
        System.out.println(countryRepository.findAll());

    }
}
