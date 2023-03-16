package com.example.codechallenge.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import com.example.codechallenge.entities.Address;
import com.example.codechallenge.entities.User;

import com.example.codechallenge.model.AddressType;
import com.example.codechallenge.model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    private User user;
    private Address address;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setAge(30);
        user.setGender(Gender.M);
        user.setDateOfBirth("12-03-1998");

        entityManager.persist(user);

        address = new Address();
        address.setUser(user);
        address.setType(AddressType.RESIDENTIAL);
        address.setLine1("123 Main St");
        address.setCity("Anytown");
        address.setState("CA");
        address.setPostcode("12345");

        entityManager.persist(address);
        entityManager.flush();
    }

    @Test
    void findByUserId() {
        Optional<Address> optionalAddress = addressRepository.findByUserId(user.getId());
        assertNotNull(optionalAddress);
        assertEquals(address, optionalAddress.get());
    }
}
