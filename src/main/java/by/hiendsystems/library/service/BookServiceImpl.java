package by.hiendsystems.library.service;

import java.io.IOException;
import java.util.List;

import by.hiendsystems.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.hiendsystems.library.dao.BookDao;
import org.springframework.web.multipart.MultipartFile;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao dao;


	@Override
	public void saveBook(Book book) {
		dao.saveBook(book);
	}

	@Override
	public void updateBook(Book book) {
		Book entity = dao.findById(book.getId());
		if(entity != null){
			entity.setName(book.getName());
			entity.setAuthor(book.getAuthor());
			entity.setYear(book.getYear());
			entity.setDescription(book.getDescription());
			entity.setContent(book.getContent());
		}
	}
	@Override
	public void deleteBookByName(String name) {
		dao.deleteBookByName(name);
	}

	@Override
	public List<Book> findAllBooks(Integer offset, Integer maxResults) {
		return dao.findAllBooks(offset, maxResults);
	}

	@Override
	public Long count(){
		return dao.count();
	}

	@Override
	public Book findBookByName(String name) {
		return dao.findBookByName(name);
	}

	@Override
	public boolean isBookNameUnique(Integer id, String name) {
		Book book = findBookByName(name);
		return ( book == null || ((id != null) && (book.getId() == id)));
	}

	@Override
	public String validateFile(MultipartFile file) {
		String errorMessage = null;
		if (!file.isEmpty()) {
			if (file.getSize() > 20971520) {
				errorMessage = "Size is too large. Max: 20MB";
			}
		} else {
			errorMessage = "File is not choosen";
		}
		return errorMessage;
	}

	@Override
	public void saveFile(MultipartFile file, String bookName) throws IOException {
		dao.saveFile(file, bookName);
	}

    @Override
    public void deleteFile(String bookName) {
        dao.deleteFile(bookName);
    }
}