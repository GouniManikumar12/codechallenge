package com.example.codechallenge.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressData {

    private Long id;

    @Valid
    @NotNull
    private String line1;

    @Valid
    @NotNull
    private String line2;

    @Valid
    @NotNull
    private String city;

    @Valid
    @NotNull
    private String state;

    @Valid
    @NotNull
    private String postcode;

    @Valid
    @NotNull
    private AddressType type;
}
