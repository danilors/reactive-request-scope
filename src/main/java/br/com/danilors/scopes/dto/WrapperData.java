package br.com.danilors.scopes.dto;

public class WrapperData {

    private String id;
    private Address address;
    private Person person;

    public WrapperData(String id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "WrapperData{" +
                "id='" + id + '\'' +
                ", address=" + address +
                ", person=" + person +
                '}';
    }
}
