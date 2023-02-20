package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody() Book book){
        // Your code goes here.
        book.setId(id++);
        this.bookList.add(book);
        System.out.println(bookList.size());
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int iD){
        for(Book book : bookList){
            if (book.getId() == iD)return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
        return null;
     }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    @DeleteMapping("/delete-book-by-id/{id}")
     public ResponseEntity<String> deleteBookById(@PathVariable("id") int iD){
        int idx = -1;
        for(int i =0; i < bookList.size(); i++){
            if(bookList.get(i).getId() == iD){
                idx = i;
            }
        }
        if(idx != -1){
            bookList.remove(idx);
            return new ResponseEntity<>(iD + "has been deleted",HttpStatus.CREATED);
        }
        return new ResponseEntity<>("ID not found", HttpStatus.CREATED);
     }

    // get request /get-all-books
    @GetMapping("/get-all-books")
     public ResponseEntity<Map<String,Book>> getAllBooks(){
        Map <String, Book> map = new HashMap<>();
            for (Book book : bookList) {
            map.put(Integer.toString(book.getId()), book);
        }
        return new ResponseEntity<>(map, HttpStatus.CREATED);
     }

    // delete request /delete-all-books
    @DeleteMapping("/delete-all-books")
     public String deleteAllBooks(){
        bookList.clear();
        return "All books in bookList has been deleted" + bookList.size();
     }

    // get request /get-books-by-author
    // pass author name as request param
    @GetMapping("/get-books-by-author")
     public ResponseEntity<Book> getBooksByAuthor(@RequestParam("author") String author){
        for(Book book : bookList){
            if(book.getAuthor().equals(author)){
                return new ResponseEntity<>(book, HttpStatus.CREATED);
            }
        }
        return null;
     }

    // get request /get-books-by-genre
    // pass genre name as request param
    @GetMapping("/get-books-by-genre")
     public ResponseEntity<Book> getBooksByGenre(@RequestParam("genre") String genre){
        for(Book book : bookList){
            if (book.getGenre().equals(genre)){
                return new ResponseEntity<>(book, HttpStatus.CREATED);
            }
        }
        return null;
     }
}
