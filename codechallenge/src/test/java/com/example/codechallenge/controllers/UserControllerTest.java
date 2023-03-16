package com.example.codechallenge.controllers;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.example.codechallenge.entities.Address;
import com.example.codechallenge.entities.User;
import com.example.codechallenge.exceptions.NotFoundException;
import com.example.codechallenge.model.AddressData;
import com.example.codechallenge.model.AddressType;
import com.example.codechallenge.model.Gender;
import com.example.codechallenge.model.UserUpdateRequest;
import com.example.codechallenge.model.request.UserCreateRequest;
import com.example.codechallenge.repositories.AddressRepository;
import com.example.codechallenge.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    @MockBean
    private AddressRepository addressRepository;

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;


    @Test
    public void testCreateUser() throws Exception {
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setAddresses(new ArrayList<>());
        userCreateRequest.setAge(1);
        userCreateRequest.setDateOfBirth("2020-03-01");
        userCreateRequest.setFirstName("Jane");
        userCreateRequest.setGender(Gender.M);
        userCreateRequest.setId(1L);
        userCreateRequest.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(userCreateRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/createuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void testCreateUser2() throws Exception {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setAge(1);
        user.setDateOfBirth("2020-03-01");
        user.setFirstName("Jane");
        user.setGender(Gender.M);
        user.setId(1L);
        user.setLastName("Doe");
        when(userRepository.save((User) any())).thenReturn(user);

        AddressData addressData = new AddressData();
        addressData.setCity("Oxford");
        addressData.setId(1L);
        addressData.setLine1("?");
        addressData.setLine2("?");
        addressData.setPostcode("OX1 1PT");
        addressData.setState("MD");
        addressData.setType(AddressType.RESIDENTIAL);

        ArrayList<AddressData> addressDataList = new ArrayList<>();
        addressDataList.add(addressData);

        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setAddresses(addressDataList);
        userCreateRequest.setAge(1);
        userCreateRequest.setDateOfBirth("2020-03-01");
        userCreateRequest.setFirstName("Jane");
        userCreateRequest.setGender(Gender.M);
        userCreateRequest.setId(1L);
        userCreateRequest.setLastName("Doe");
        String content = (new ObjectMapper()).writeValueAsString(userCreateRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/createuser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testGetUser() throws Exception {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setAge(1);
        user.setDateOfBirth("2020-03-01");
        user.setFirstName("Jane");
        user.setGender(Gender.M);
        user.setId(1L);
        user.setLastName("Doe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/{userId}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":1,\"gender\":\"M\",\"dateOfBirth\":\"2020-03-01\",\"addresses"
                                        + "\":null}"));
    }


    @Test
    public void testGetUser3() throws Exception {
        User user = new User();
        user.setAddresses(new ArrayList<>());
        user.setAge(1);
        user.setDateOfBirth("2020-03-01");
        user.setFirstName("Jane");
        user.setGender(Gender.M);
        user.setId(1L);
        user.setLastName("Doe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Long) any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users/{userId}", 1L);
        MockHttpServletRequestBuilder requestBuilder = getResult.param("includeAddresses", String.valueOf(true));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"age\":1,\"gender\":\"M\",\"dateOfBirth\":\"2020-03-01\",\"addresses"
                                        + "\":[]}"));
    }


    @Test
    public void testGetUser4() throws Exception {
        when(userRepository.findById((Long) any())).thenThrow(new NotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/{userId}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void testUpdateUser() throws Exception {
        // create test data
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setGender(Gender.M);
        user.setDateOfBirth("2000-02-02");

        Address address = new Address();
        address.setId(1L);
        address.setType(AddressType.RESIDENTIAL);
        address.setLine1("123 Main St");
        address.setCity("New York");
        address.setPostcode("10001");
        address.setState("NY");
        address.setUser(user);

        user.setAddresses(Collections.singletonList(address));

        // create update request
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setFirstName("Jane");
        updateRequest.setGender(Gender.F);

        AddressData addressData = new AddressData();
        addressData.setId(1L);
        addressData.setType(AddressType.RESIDENTIAL);
        addressData.setCity("Los Angeles");
        addressData.setState("CA");
        updateRequest.setAddresses(Collections.singletonList(addressData));

        // configure mock repository to return test data
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // perform PUT request
        ResponseEntity<User> response = userController.updateUser(1L, updateRequest);

        // assert response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        User updatedUser = response.getBody();
        assertEquals("Jane", updatedUser.getFirstName());
        assertEquals("Doe", updatedUser.getLastName());
        assertEquals(Gender.F, updatedUser.getGender());
        assertEquals("2000-02-02", updatedUser.getDateOfBirth());

        List<Address> updatedAddresses = updatedUser.getAddresses();
        assertEquals(1, updatedAddresses.size());

        Address updatedAddress = updatedAddresses.get(0);
        assertEquals(AddressType.RESIDENTIAL, updatedAddress.getType());
        assertEquals("123 Main St", updatedAddress.getLine1());
        assertEquals("Los Angeles", updatedAddress.getCity());
        assertEquals("CA", updatedAddress.getState());
        assertEquals("10001", updatedAddress.getPostcode());

        // verify repository was called correctly
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(userRepository).save(user);
    }
}

