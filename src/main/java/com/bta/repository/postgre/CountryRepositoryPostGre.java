package com.bta.repository.postgre;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import com.bta.Main;
import com.bta.model.City;
import com.bta.model.Country;
import com.bta.repository.CountryRepository;

public class CountryRepositoryPostGre implements CountryRepository {

    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";

    private Properties connectionProperties;

    public CountryRepositoryPostGre() {
        this.connectionProperties = getConnectionProperties();
    }

    private Properties getConnectionProperties() {
        final Properties properties = new Properties();
        try (InputStream resourceAsStream = Main.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            System.err.println("Problem with config file!!!");
        }
        return properties;
    }

    @Override
    public List<Country> findAll() {
        final String query = "select cn.id, cn.code, cn.name, cn.description, ct.id, ct.name, ct.population, ct.description" +
                " from country cn " +
                "left join city ct  on cn.id = ct.country_id";
        final Map<Long, Country> map = new HashMap<>();
        try (final Connection connection = DriverManager.getConnection(
                this.connectionProperties.getProperty(DB_URL),
                this.connectionProperties.getProperty(DB_USERNAME),
                this.connectionProperties.getProperty(DB_PASSWORD));
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Long countryId = resultSet.getLong(1);
                if (map.get(countryId) == null) {
                    Country countryFromRs = getCountry(resultSet);
                    map.put(countryId, countryFromRs);
                }
                Country country = map.get(countryId);
                City city = getCity(resultSet, country);
                country.getCities().add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(map.values());
    }

    private Country getCountry(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(1);
        String code = resultSet.getString(2);
        String name = resultSet.getString(3);
        String description = resultSet.getString(4);

        return new Country(id, code, name, description, new ArrayList<>());
    }

    private City getCity(ResultSet resultSet, Country country) throws SQLException {
        Long id = resultSet.getLong(5);
        String name = resultSet.getString(6);
        Long population = resultSet.getLong(7);
        String description = resultSet.getString(8);

        return new City(id, name, population, description, country);
    }

    @Override
    public Optional<Country> findOne(Long id) {
        Country country = null;
        try {
            final Connection connection = DriverManager.getConnection(
                    this.connectionProperties.getProperty(DB_URL),
                    this.connectionProperties.getProperty(DB_USERNAME),
                    this.connectionProperties.getProperty(DB_PASSWORD));
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from country where id = " + id);
            while (resultSet.next()) {
                //Long id = resultSet.getLong("id");
                String code = resultSet.getString("code");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                country = new Country(id, code, name, description, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(country);
    }

    @Override
    public int delete(Long id) {
        try {
            final Connection connection = DriverManager.getConnection(
                    this.connectionProperties.getProperty(DB_URL),
                    this.connectionProperties.getProperty(DB_USERNAME),
                    this.connectionProperties.getProperty(DB_PASSWORD));
            PreparedStatement preparedStatement = connection.prepareStatement("delete from country where id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int save(Country country) {
        final String query = "insert into country (id, code, name, description) values (" +
                "nextval('country_seq'), ?, ?, ?)";

        try (final Connection connection = DriverManager.getConnection(
                this.connectionProperties.getProperty(DB_URL),
                this.connectionProperties.getProperty(DB_USERNAME),
                this.connectionProperties.getProperty(DB_PASSWORD));
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, country.getCode());
            preparedStatement.setString(2, country.getName());
            preparedStatement.setString(3, country.getDescription());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
