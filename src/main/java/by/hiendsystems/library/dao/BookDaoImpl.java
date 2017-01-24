package by.hiendsystems.library.dao;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import by.hiendsystems.library.model.Book;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Repository("bookDao")
public class BookDaoImpl extends AbstractDao<Integer, Book> implements BookDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Book findById(int id) {
		return getByKey(id);
	}

	@Override
	public void saveBook(Book book) {
		persist(book);
	}

	@Override
	public void deleteBookByName(String name) {
		Query query = getSession().createSQLQuery("delete from Book where name = :name");
		query.setString("name", name);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Book> findAllBooks(Integer offset, Integer maxResults) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Book.class);
		criteria.setFirstResult(offset!=null?offset:0).setMaxResults(maxResults!=null?maxResults:10);
		return (List<Book>) criteria.list();
	}

	@Override
	public Long count(){
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Book.class);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@Override
	public Book findBookByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		return (Book) criteria.uniqueResult();
	}

	@Override
	public void saveFile(MultipartFile file, String bookName) throws IOException {
		String rootPath = "C:\\";
		File dir = new File(rootPath + File.separator + "tmpFiles");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String originalName = file.getOriginalFilename();
		int index = originalName.lastIndexOf('.');
		String extension = originalName.substring(index);

		File serverFile = new File(dir.getAbsolutePath() + File.separator + bookName + extension + ".gz");
		FileOutputStream fos = new FileOutputStream(serverFile);
		GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
		byte[] bytes = file.getBytes();
		gzipOS.write(bytes);
		gzipOS.close();
		fos.close();
	}

	@Override
	public void deleteFile(String bookName) {
		File root = new File("c:/tmpFiles/");
		String regex = bookName + ".(pdf|txt|html|fb2|doc|docx)" + ".gz";
		final Pattern p = Pattern.compile(regex);
		File[] files = root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return p.matcher(file.getName()).matches();
			}
		});
		if (files.length > 0) {
			files[0].delete();
		}
	}
}
