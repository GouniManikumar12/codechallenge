package com.example.codechallenge.model.request;

import com.example.codechallenge.model.AddressData;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserCreateRequest extends UserBasicData {

    @Valid
    @NotNull
    private List<AddressData> addresses;
}
