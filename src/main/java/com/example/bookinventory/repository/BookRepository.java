package com.example.bookinventory.repository;


import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.Category;
import com.example.bookinventory.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByTitle(String title);
    List<Book> findByCategory(Category category);
    List<Book> findByPublisher(Publisher publisher);
    boolean existsByIsbn(String isbn);
}
