package com.example.codechallenge.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserUpdateRequest {


    private String firstName;

    private String lastName;

    private Integer age;

    private Gender gender;

    private String dateOfBirth;

    private List<AddressData> addresses;




}
