package by.hiendsystems.library.service;

import java.io.IOException;
import java.util.List;

import by.hiendsystems.library.model.Book;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
	
	void saveBook(Book book);
	
	void updateBook(Book book);
	
	void deleteBookByName(String name);

	List<Book> findAllBooks(Integer offset, Integer maxResults);

	Long count();
	
	Book findBookByName(String name);

	boolean isBookNameUnique(Integer id, String name);

	String validateFile(MultipartFile file);

	void saveFile(MultipartFile file, String bookName) throws IOException;

	void deleteFile(String bookName);
}