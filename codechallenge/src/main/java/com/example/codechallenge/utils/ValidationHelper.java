package com.example.codechallenge.utils;

import com.example.codechallenge.model.AddressData;
import com.example.codechallenge.model.AddressType;

import java.util.List;

public class ValidationHelper {

    public static boolean validateAddresses(List<AddressData> addressDataList){
      boolean isValidAddress=  addressDataList.stream()
                .map(AddressData::getType)
                .anyMatch(t -> t.equals(AddressType.RESIDENTIAL));

      return isValidAddress;

    }
}
