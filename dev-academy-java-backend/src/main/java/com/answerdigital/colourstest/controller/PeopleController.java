package com.answerdigital.colourstest.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.action.internal.EntityAction;
import org.hibernate.annotations.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.exception.NotImplementedException;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRespository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        // TODO STEP 1
        //returns all people from db.  
    	//If db has no entries, as status is OK, will still return an empty array.
    	return new ResponseEntity<>(peopleRespository.findAll(), HttpStatus.OK);	
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Person>> getPerson(@PathVariable("id") long id) {
        // TODO: Step 2
    	// Set person from repository to a variable
    	Optional<Person> person = peopleRespository.findById(id);
    	
    	
    	if(person.isPresent()) {
    		// if person, return person data plus 200 status code
    		return new ResponseEntity<>(person, HttpStatus.OK);
    	} 
		
    	// if we reach here, no person in db. 404 status - NOT FOUND is sent.
		return new ResponseEntity<>( HttpStatus.NOT_FOUND); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Person>> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        // TODO STEP 3
        //
        // Implement an endpoint that recieves a JSON object to
        // update a person using the PeopleRepository based on
        // the id parameter. Once the person has been sucessfullly
        // updated, the person should be returned from the endpoint.
        // If null is returned from the PeopleRepository then a
        // NotFound should be returned.
    	
    	
    	
    	// Set person from repository to a variable
    	Optional<Person> person = peopleRespository.findById(id);
    	
    	if(person.isPresent()) {
    		if(person.get().isAuthorised() != personUpdate.isAuthorised()) {
        		person.get().setAuthorised(personUpdate.isAuthorised());
        	}
        	if(person.get().isEnabled() != personUpdate.isEnabled()) {
        		person.get().setEnabled(personUpdate.isEnabled());
        	}
        	if(person.get().getColours() != (personUpdate.getColours())) {
        		person.get().setColours(personUpdate.getColours());
        	}
        	peopleRespository.save(person.get());
        	
        	return new ResponseEntity<>(person, HttpStatus.OK);
    	}
		
    	// if we reach here, no person in db. 404 status - NOT FOUND is sent.
		return new ResponseEntity<>( HttpStatus.NOT_FOUND); 
        
    }
    
    /* Optional POST route for adding new Person */
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person newPerson) {
        
    	
    	
    	// Set person from repository to a variable
    	if(newPerson != null) {
    		
    		Person person = new Person();
    				
//    				person.setId(newPerson.getId());
    				person.setFirstName(newPerson.getFirstName());
    				person.setLastName(newPerson.getLastName());
    				person.setAuthorised(newPerson.isAuthorised());
    				person.setEnabled(newPerson.isEnabled());
    				person.setColours(newPerson.getColours());
    			
    				
    		
    		peopleRespository.save(person);
    		 

        	
        	
        	return new ResponseEntity<>(person, HttpStatus.OK);
        	
        /* Having a little trouble with this.  Post automatically tries to insert in into
          row 1.  If I send 12 post requests, it finds the empty slot and inserts the new person.
          Probably my bad, but I researched this and found that because the program uses hibernate,
          the id's are not auto incrementing on setup leading to a request trying to insert a new object
          into row 1.  I think this is the correct method to make a POST request in the production version.
          Hope this is OK.
         
         */
    	}
    	
    	return null;
    }
     

}
