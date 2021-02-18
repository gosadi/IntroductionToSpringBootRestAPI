/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gosadi.controller;

import gosadi.entity.Author;
import gosadi.repository.AuthorRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author alkinoos
 */
@RestController//den xriazete na vazoume @ResponseBody se kathe methodo giati to pernoun default
@RequestMapping("/api/authors")
public class AuthorController {
    
    @Autowired
    private AuthorRepo authorRepo;
    
    //findAll
    //@ResponseBody//metatrepi auto to pou girnaei i methodos se json
    @GetMapping
    public List<Author> findAll(){
        return authorRepo.findAll();
    }
    
    //findById
    @GetMapping("/{id}") //@GetMapping(value= "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE}) ean den exoume vgali to @XmlROOTELEMENT
    public ResponseEntity<Author> findById(@PathVariable(value = "id") int id){
        Optional<Author> optionalAuthor = authorRepo.findById(id);
        if(!optionalAuthor.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Author author = optionalAuthor.get();
        ResponseEntity responseEntity = new ResponseEntity(author, HttpStatus.OK);
        return responseEntity;
    }
    
    //create
    @PostMapping
    public Author create(@RequestBody Author author){//gia na paroume to JSON apo to front end meso ton setters
        Author savedAuthor = authorRepo.save(author);
        return savedAuthor;
    }
    
    //update
    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@RequestBody Author author, @PathVariable(value = "id") int id){
        //find author to update
        Author authorToUpdate =  authorRepo.findById(id).get();
        //change author details
        authorToUpdate.setName(author.getName());
        //save author
        authorToUpdate = authorRepo.save(authorToUpdate);
        //return a response
        return ResponseEntity.status(HttpStatus.CREATED).body(authorToUpdate);
    }
    
    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") int id){
        Author authorToDelete =  authorRepo.findById(id).get();
        authorRepo.delete(authorToDelete);
        String message = "Author with id:"+id+ " successfully deleted!";
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    
    
}
