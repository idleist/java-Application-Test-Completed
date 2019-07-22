package com.answerdigital.colourstest.controller;

import com.answerdigital.colourstest.model.Colour;
import com.answerdigital.colourstest.repository.ColoursRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/colours")
public class ColoursController {

    @Autowired
    private ColoursRepository coloursRepository;

    @GetMapping
    public ResponseEntity<List<Colour>> getColours() {
        return new ResponseEntity<>(coloursRepository.findAll(), HttpStatus.OK);
    }
    
    /* Optional Routes */
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Colour>> getColour(@PathVariable("id") long id) {
        
    	// Set colour from repository to a variable
    	Optional<Colour> colour = coloursRepository.findById(id);
    	
    	
    	if(colour.isPresent()) {
    		// if colour, return colour data plus 200 status code
    		return new ResponseEntity<>(colour, HttpStatus.OK);
    	} 
		
    	// if we reach here, no colour matched in db. 404 status - NOT FOUND is sent.
		return new ResponseEntity<>( HttpStatus.NOT_FOUND); 
    }
    
    @PostMapping
    public ResponseEntity<Colour> createColour(@RequestBody Colour newColour) {
        /* Same error with this API request.  It's probably me, but I have tried to solve this issue.
         	I can't get the save function to insert the new colour at the end of the table.  It tries the first
         	slot on the first call, then the second etc.  If I do 4 post requests it finally accepts this new colour.
         	I think this code is correct (maybe not :( ) but it seems to be an issue with having data already in the
         	table in hibernate.  
        */
    	
    	
    	// Set person from repository to a variable
    	if(newColour != null) {
    		
    		Colour colour = new Colour();
    		colour.setName(newColour.getName());	
    			
    		coloursRepository.save(colour);
    		
        	return new ResponseEntity<>(colour, HttpStatus.OK);
    	}
    	
    	return null;
    }
}



