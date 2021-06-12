package bank.person.micros.entity;

import bank.person.micros.types.Address;
import bank.person.micros.types.enums.Gender;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Person {

    @Id
    String id;
    String name;
    int Age;
    Gender gender;
    double height;
    double weight;
    Address address;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Person(){
    }

    public Person(String name, int age, Gender gender, double height, double weight, Address address) {
        this.name = name;
        Age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", Age=" + Age +
                ", gender=" + gender +
                ", height=" + height +
                ", weight=" + weight +
                ", address=" + address +
                '}';
    }
}
