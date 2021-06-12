package bank.person.micros.services;

import bank.person.micros.db.MongoDB;
import bank.person.micros.entity.Person;
import bank.person.micros.repository.PersonRepository;
import bank.person.micros.types.Address;
import bank.person.micros.types.enums.Gender;
import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // create single person
    public Person createPerson (Person person){
        return personRepository.save(person);
    }

    // single
    public Person getPerson(String name){
        return personRepository.findByName(name);
    }

    // update single person
    public Person updatePerson (String name, int age, Gender gender, double height, double weight, Address address){
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
    public void deleteOnePerson(String name){
        Person p = personRepository.findByName(name);
        personRepository.delete(p);
    }

    // delete all person
    public void deleteAllPersons(){
        personRepository.deleteAll();
    }



    // read multiple person
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }


    // read all people whose name start with A or K
    // TODO
//    public List<Person> getAllPersonsByFirstCharName(char firsCharOption, char secondCharOption){
//
//        // db.users.find({$or: {"name": /firsCharOption.*/}, {"name": /secondCharOption.*/})
//
//        MongoCollection<Document> collection = MongoDB.getMongoCollection();
//
//        System.out.println("START *******");
//        AggregateIterable<Document> output = collection.aggregate(Arrays.asList(
//                new Document("name", "k.*")
//        ));
//
//        for (Document dbObject : output)
//        {
//            System.out.println(dbObject);
//        }
//
//
//
////        Document document = collection.find({$or: {"name": /firsCharOption.*/}, {"name": /secondCharOption.*/}));
////        System.out.println(document);
//
//        return new LinkedList<Person>();
//    }


////    // TODO
//    public List<Person> allIsraelCitizensAboveAverageWeight() {
//
//        MongoCollection<Document> collection = MongoDB.getMongoCollection();
//
//        System.out.println("START *******");
//        AggregateIterable<Document> output = collection.aggregate(
//                Arrays.asList(
//                        Aggregates.match(Filters.eq("weight", "$avg")),
//                        Aggregates.group("weight", Accumulators.avg("weight", "averageWeight"))
//                )
//        );
//
//        System.out.println(output);
//        for (Document dbObject : output) {
//            System.out.println(dbObject);
//        }
//    }

}
