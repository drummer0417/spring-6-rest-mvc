package nl.androidappfactory.spring6restmvc.repositories;

//import nl.androidappfactory.spring6restmvc.entities.Beer;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.test.context.ActiveProfiles;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
///**
// * Created by jt, Spring Framework Guru.
// */
//@Testcontainers
//@SpringBootTest
//@ActiveProfiles("localmysql")
//public class MySqlIT {
//
//    @Container
//    @ServiceConnection
//    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:9");
//
//    @Autowired
//    BeerRepository beerRepository;
//
//    @Test
//    void testListBeers() {
//        List<Beer> beers = beerRepository.findAll();
//
//        assertThat(beers.size()).isGreaterThan(0);
//    }

import nl.androidappfactory.spring6restmvc.entities.Beer;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("localmysql")
public class MySqlIT {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:9");

    @Autowired
    DataSource dataSource;

    @Autowired
    BeerRepository beerRepository;

    @DynamicPropertySource
    static void mySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
    }


    @Ignore
//    @Test
    // Before enabling this test start: DockerDesktop
    // Before enabling this test start: DockerDesktop
    // Before enabling this test start: DockerDesktop
    // Before enabling this test start: DockerDesktop
    // Before enabling this test start: DockerDesktop
    // Before enabling this test start: DockerDesktop
    // Before enabling this test start: DockerDesktop
    void listBeers() {
        List<Beer> beers = beerRepository.findAll();

        assertThat(beers).isNotEmpty();

        assertThat(beers).hasSize(2413);
    }
}
