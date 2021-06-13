package bank.person.micros.controllers;

import bank.person.micros.constants.Constants;
import bank.person.micros.entity.Person;
import bank.person.micros.services.*;
import bank.person.micros.types.Address;
import bank.person.micros.types.enums.Gender;
import bank.person.micros.types.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    Address address;
    Gender gender;
    Person person;
    String validation;
    String errorMessage;
    String zipcode;

    @RequestMapping(method=RequestMethod.POST, value = "/createperson")
    public String createperson (@RequestParam String name, @RequestParam int age, @RequestParam int genderId, @RequestParam double height,
                          @RequestParam double weight, @RequestBody Address address) {

        validation = validationCreateFields(name, age , genderId, height, address);
        if (!validation.equals(Constants.CORRECT))
            return validation;

        gender = getGender(genderId);

        person = personService.createPerson(new Person(name, age, gender, height, weight, address));
        return person.toString();
    }

    @RequestMapping("/getperson")
    public String getPerson (@RequestParam String name) {
        errorMessage = nameValidation(name);
        if(!errorMessage.equals(Constants.CORRECT))
            return errorMessage;

        personService.getPerson(name);

        return personService.getPerson(name).toString();
    }

    @RequestMapping("/getallpersons")
    public List<Person> getAllPersons () {
        return personService.getAllPersons();
    }

    @RequestMapping(method=RequestMethod.POST, value = "/updateperson")
    public String updateperson (@RequestParam String name, @RequestParam int age, @RequestParam int genderId, @RequestParam double height,
                          @RequestParam double weight, @RequestBody Address address) {
        errorMessage = nameValidation(name);
        if(!errorMessage.equals(Constants.CORRECT))
            return errorMessage;

        validation = validationFields(age , genderId, height, address);
        if (!validation.equals("correct"))
            return validation;


        if (genderId < 0 && genderId > 2){
            return Constants.UNRECOGNIZED_GENDER;
        }

        address = new Address();
        gender = getGender(genderId);

        personService.updatePerson(name, age, gender, height,weight,address);

        return personService.updatePerson(name, age, gender, height,weight, address).toString();
    }

    @RequestMapping("/deleteperson")
    public String deleteperson (@RequestParam String name) {
        errorMessage = nameValidation(name);
        if(!errorMessage.equals(Constants.CORRECT))
            return errorMessage;

        personService.deleteOnePerson(name);

        return name + " was deleted";
    }

    @RequestMapping("/deleteallperson")
    public String deleteAllperson () {

        personService.deleteAllPersons();

        return "All persons were deleted";
    }

    @RequestMapping("/getallpersonbychar")
    public List<Person> getAllPersonsByFirstCharName () {
        return personService.getAllPersonsByFirstCharName();
    }

    // read all people that are above the average weight of all people and from Israel
    @RequestMapping("/allavgcit")
    public List<Person> getAllIsraelCitizensAboveAverageWeight () {
        return personService.getAllIsraelCitizensAboveAverageWeight();
    }


    private Gender getGender (int genderId){
        switch(genderId){
            case 0:
                gender = Gender.Male;
                break;
            case 1:
                gender = Gender.Female;
                break;
            case 2:
                gender = Gender.Other;
                break;
        }

        return gender;
    }

    private String validationFields(int age, int genderId, double height, Address address){

        // age validation
        if (age < 0 || age > 200){
            return Constants.INCORRECT_AGE;
        }

        // gender validation
        if (genderId < 0 || genderId > 2){
            return Constants.UNRECOGNIZED_GENDER;
        }

        // height validation
        if (height < 0){
            return Constants.INCORRECT_HEIGHT;
        }

        // address validation
        if (!address.getState().equals(State.ISRAEL))
            return Constants.NOT_ISRAEL_CITIZEN;

        if(address.getCity().length() < 3 || address.getCity().length() > 20)
            return Constants.INCORRECT_LENGTH;

        if(address.getStreet().length() < 3 || address.getStreet().length() > 50)
            return Constants.INCORRECT_LENGTH;

        // zipcode validation
        zipcode = address.getZipcode();
        if(!(zipcode.matches("[0-9]+") && zipcode.length() > 5))
            return Constants.INCORRECT_ZIPCODE;

        return Constants.CORRECT;
    }

    private String validationCreateFields(String name, int age, int genderId, double height, Address address){
        // name is unique - validate only in create function
        if(name.length() < 3 || name.length() > 20){
            return Constants.INCORRECT_NAME;
        }

        errorMessage = validationFields (age, genderId, height, address);

        if (errorMessage.equals(Constants.CORRECT)){
            return Constants.CORRECT;
        } else
            return errorMessage;

    }

    private String nameValidation(String name){
        person = personService.getPerson(name);
        if (person.equals(null))
            return Constants.INCORRECT_NAME;

        return Constants.CORRECT;

    }

}
