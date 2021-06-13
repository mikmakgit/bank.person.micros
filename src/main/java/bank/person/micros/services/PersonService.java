package bank.person.micros.services;

import bank.person.micros.entity.Person;
import bank.person.micros.repository.PersonRepository;
import bank.person.micros.types.Address;
import bank.person.micros.types.enums.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class PersonService {

    Query query;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    // create single person
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    // single
    public Person getPerson(String name) {
        return personRepository.findByName(name);
    }

    // update single person
    public Person updatePerson(String name, int age, Gender gender, double height, double weight, Address address) {
        Person p = personRepository.findByName(name);
        p.setName(name);
        p.setAge(age);
        p.setGender(gender);
        p.setHeight(height);
        p.setWeight(weight);
        p.setAddress(address);
        return personRepository.save(p);
    }

    // delete single person
    public void deleteOnePerson(String name) {
        Person p = personRepository.findByName(name);
        personRepository.delete(p);
    }

    // delete all person
    public void deleteAllPersons() {
        personRepository.deleteAll();
    }


    // read multiple person
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }


    // read all people whose name start with A or K
    public List<Person> getAllPersonsByFirstCharName(){
        query = new Query();
        query.addCriteria(
                new Criteria().orOperator(
                        Criteria.where("name").regex("^a"),
                        Criteria.where("name").regex("^k")
                )
        );
        // addCriteria(Criteria.where("address.state").is("ISRAEL"));

        List<Person> val = mongoTemplate.find(query, Person.class);
        return val;
}

    // read all people thar are average weught of all people and from Israel
    public List<Person> getAllIsraelCitizensAboveAverageWeight() {
        GroupOperation groupByStateAndSumPup = group()
                .avg("weight").as("weightAVG");
        Aggregation aggregation = newAggregation(groupByStateAndSumPup);
        AggregationResults<Object> obj = mongoTemplate.aggregate(aggregation, "person", Object.class);
        LinkedHashMap<Object, Object> avg = (LinkedHashMap<Object, Object>) obj.getUniqueMappedResult();
        Double avgDouble = (Double) avg.get("weightAVG");

        query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("weight").gt(avgDouble),
                        Criteria.where("address.state").is("ISRAEL")
                )
        );

        List<Person> val = mongoTemplate.find(query, Person.class);

        return val;
    }

}
