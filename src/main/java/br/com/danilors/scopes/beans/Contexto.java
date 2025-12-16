package br.com.danilors.scopes.beans;


import br.com.danilors.scopes.dto.Address;
import br.com.danilors.scopes.dto.Person;
import br.com.danilors.scopes.dto.WrapperData;
import jakarta.annotation.PostConstruct;

import java.util.UUID;

public class Contexto {

    private WrapperData wrapperData;

    @PostConstruct
    public void init() {
        wrapperData = new WrapperData(UUID.randomUUID().toString());
    }

    public void addAddress(Address address) {
        wrapperData.setAddress(address);
    }

    public void addPeson(Person person) {
        wrapperData.setPerson(person);
    }

    public WrapperData getWrapperData() {
        return wrapperData;
    }

}
