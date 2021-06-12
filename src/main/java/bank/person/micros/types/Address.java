package bank.person.micros.types;

import bank.person.micros.types.enums.State;

public class Address {
    State state;
    String city;
    String street;
    String zipcode;
    Boolean containsAnimals;

    public Address() {
    }

    public Address(State state, String city, String street, String zipcode, Boolean containsAnimals) {
        this.state = state;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.containsAnimals = containsAnimals;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Boolean getContainsAnimals() {
        return containsAnimals;
    }

    public void setContainsAnimals(Boolean containsAnimals) {
        this.containsAnimals = containsAnimals;
    }
}
