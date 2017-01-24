package by.hiendsystems.library.dao;

import java.io.IOException;
import java.util.List;

import by.hiendsystems.library.model.Book;
import org.springframework.web.multipart.MultipartFile;

public interface BookDao {

	Book findById(int id);

	void saveBook(Book book);
	
	void deleteBookByName(String name);
	
	List<Book> findAllBooks(Integer offset, Integer maxResults);

	Long count();

	Book findBookByName(String name);

	void saveFile(MultipartFile file, String bookName) throws IOException;

	void deleteFile(String bookName);
}