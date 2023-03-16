package com.example.codechallenge.controllers;

import com.example.codechallenge.entities.Address;
import com.example.codechallenge.entities.User;
import com.example.codechallenge.exceptions.InvalidDataException;
import com.example.codechallenge.exceptions.NotFoundException;
import com.example.codechallenge.model.AddressData;
import com.example.codechallenge.model.UserUpdateRequest;
import com.example.codechallenge.model.request.UserCreateRequest;

import com.example.codechallenge.repositories.AddressRepository;
import com.example.codechallenge.repositories.UserRepository;
import com.example.codechallenge.utils.ValidationHelper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @PostMapping("/createuser")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) throws InvalidDataException {
              if (ValidationHelper.validateAddresses(userCreateRequest.getAddresses())) {

            User user = new User();
            user.setFirstName(userCreateRequest.getFirstName());
            user.setLastName(userCreateRequest.getLastName());
            user.setAge(userCreateRequest.getAge());
            user.setGender(userCreateRequest.getGender());
            user.setDateOfBirth(userCreateRequest.getDateOfBirth());

            // create address entities from address data
            List<Address> addresses = new ArrayList<>();
            for (AddressData addressData : userCreateRequest.getAddresses()) {
                Address address = new Address();
                address.setLine1(addressData.getLine1());
                address.setLine2(addressData.getLine2());
                address.setCity(addressData.getCity());
                address.setState(addressData.getState());
                address.setPostcode(addressData.getPostcode());
                address.setType(addressData.getType());
                address.setUser(user);
                addresses.add(address);
            }

            // set addresses property of user entity
            user.setAddresses(addresses);
            userRepository.save(user);

        } else {
            throw new InvalidDataException("Please provide atleast one resendtial address");
        }

        return null;
    }
    @PutMapping("user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) throws NotFoundException, InvalidDataException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        if (userUpdateRequest.getFirstName() != null) {
            user.setFirstName(userUpdateRequest.getFirstName());
        }
        if (userUpdateRequest.getLastName() != null) {
            user.setLastName(userUpdateRequest.getLastName());
        }
        if (userUpdateRequest.getGender() != null) {
            user.setGender(userUpdateRequest.getGender());
        }
        if (userUpdateRequest.getAge() != null) {
            throw new InvalidDataException("Age Cannot be Updated");
        }
        if (userUpdateRequest.getDateOfBirth() != null) {
            throw new InvalidDataException("Date Of Birth Cannot be Updated");
        }
        if (userUpdateRequest.getAddresses() != null) {
            for (AddressData addressData : userUpdateRequest.getAddresses()) {
                Address address = user.getAddresses().stream().filter(a -> a.getType().equals(addressData.getType())).findFirst().orElse(null);
                if (address == null) {
                    throw new InvalidDataException("Address with id " + addressData.getId() + " not found for user with id " + id);
                }
                if (addressData.getType() != null) {
                    address.setType(addressData.getType());
                }
                if (addressData.getLine1() != null) {
                    address.setLine1(addressData.getLine1());
                }
                if (addressData.getLine2() != null) {
                    address.setLine2(addressData.getLine2());
                }
                if (addressData.getPostcode() != null) {
                    address.setPostcode(addressData.getPostcode());
                }
                if (addressData.getCity() != null) {
                    address.setCity(addressData.getCity());
                }
                if (addressData.getState() != null) {
                    address.setState(addressData.getState());
                }
            }
        }

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId, @RequestParam(required = false) boolean includeAddresses) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (includeAddresses) {
                Hibernate.initialize(user.getAddresses());
            } else {
                user.setAddresses(null); // set addresses to null to exclude them from the response
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new NotFoundException("User not found"), HttpStatus.NOT_FOUND);
        }
    }



}
