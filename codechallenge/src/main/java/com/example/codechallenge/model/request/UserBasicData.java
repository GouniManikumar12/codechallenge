package com.example.codechallenge.model.request;

import com.example.codechallenge.model.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class UserBasicData {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Integer age;

    @NotNull
    private Gender gender;

    @NotNull
    private String dateOfBirth;


}
