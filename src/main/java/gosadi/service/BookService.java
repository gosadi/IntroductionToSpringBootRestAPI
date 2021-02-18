/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gosadi.service;

import gosadi.entity.Book;
import java.util.List;
import org.springframework.http.ResponseEntity;


/**
 *
 * @author alkinoos
 */
public interface BookService {
    
    List<Book> findAll();

    public ResponseEntity<Book> findById(int id);
}
