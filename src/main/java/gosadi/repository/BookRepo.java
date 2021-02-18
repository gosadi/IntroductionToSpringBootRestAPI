/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gosadi.repository;

import gosadi.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alkinoos
 */
@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{//EDO TO SPRING DATA epireazete me tis leksis klidia pou dinoume sta onomata ton methodon
                                                                                                            //kai autes metatreponte se query - (ta onomata ton parametron den pezoun rolo)
    
       List<Book> findByAuthorId(int authorId);
       
       Optional<Book> findByIdAndAuthorId(int bookId, int authorId);
}
