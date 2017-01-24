package by.hiendsystems.library.controller;

import java.io.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import by.hiendsystems.library.model.Book;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import by.hiendsystems.library.service.BookService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	BookService service;

	@Autowired
	MessageSource messageSource;

	@RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
	public String listBooks(ModelMap model, Integer offset, Integer maxResults) {
		model.addAttribute("book", service.findAllBooks(offset, maxResults));
		model.addAttribute("count", service.count());
		model.addAttribute("offset", offset);
		return "allbooks";
	}

	@RequestMapping(value = {"/new"}, method = RequestMethod.GET)
	public String newBook(ModelMap model) {
		Book book = new Book();
		model.addAttribute("book", book);
		model.addAttribute("edit", false);
		return "addition";
	}

	@RequestMapping(value = {"/new"}, method = RequestMethod.POST)
	public String saveBook(@Valid Book book, BindingResult result, HttpServletRequest request,
						   ModelMap model, @RequestParam("file") MultipartFile file) {
		if (result.hasErrors()) {
			return "addition";
		}
		String errorMessage = service.validateFile(file);
		if (errorMessage != null) {
			request.setAttribute("fileError", errorMessage);
			return "addition";
		}
		if (!service.isBookNameUnique(book.getId(), book.getName())) {
			FieldError nameError = new FieldError("book", "name",
					messageSource.getMessage("non.unique.name", new String[]{book.getName()}, Locale.getDefault()));
			result.addError(nameError);
			return "addition";
		}

		try {
            service.saveFile(file, book.getName());
        } catch (IOException e) {
		    return "addition";
		}
		service.saveBook(book);
		model.addAttribute("success", "Book " + book.getName() + " added");
		return "success";
	}

	@RequestMapping(value = {"/edit-{name}-book"}, method = RequestMethod.GET)
	public String editBook(@PathVariable String name, ModelMap model) {
		Book book = service.findBookByName(name);
		model.addAttribute("book", book);
		model.addAttribute("edit", true);
		return "addition";
	}

	@RequestMapping(value = {"/edit-{name}-book"}, method = RequestMethod.POST)
	public String updateBook(@Valid Book book, BindingResult result, ModelMap model,
                     @RequestParam("file") MultipartFile file, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("edit", true);
			return "addition";
		}

		if (!service.isBookNameUnique(book.getId(), book.getName())) {
			FieldError nameError = new FieldError("book", "name",
                    messageSource.getMessage("non.unique.name", new String[]{book.getName()}, Locale.getDefault()));
			result.addError(nameError);
			return "addition";
		}

        String errorMessage = service.validateFile(file);

        if (errorMessage == "Size is too large. Max: 20MB") {
            request.setAttribute("fileError", errorMessage);
            model.addAttribute("edit", true);
            return "addition";
        }
        if (errorMessage == null) {
            try {
                service.saveFile(file, book.getName());
            } catch (IOException e) {
                return "addition";
            }
        }
		service.updateBook(book);

		model.addAttribute("success", "Book " + book.getName() + " updated successfully");
		return "success";
	}

	@RequestMapping(value = {"/view-{name}-book"}, method = RequestMethod.GET)
	public String viewBook(@PathVariable String name, ModelMap model, HttpSession session) {
		ArrayList<Book> list = new ArrayList<Book>();
		if (session.getAttribute("cashed") != null){
			list = (ArrayList<Book>) session.getAttribute("cashed");
		}
		Book cashedBook = service.findBookByName(name);
		boolean contain = false;
		for (Book b : list){
            if (name.equals(b.getName())){
                contain = true;
            }
        }
        if (!contain){
		    list.add(cashedBook);
        }
		session.setAttribute("cashed", list);
		Book book = service.findBookByName(name);
		model.addAttribute("book", book);
		model.addAttribute("edit", false);
		return "view";
	}

	@RequestMapping(value = {"/download-{name}-book"}, method = RequestMethod.GET)
	public String downloadDocument(@PathVariable String name, HttpServletResponse response) throws IOException {
		File root = new File("c:/tmpFiles/");
		System.out.println(name);
		name = name.replace(" ", "\\s");
		System.out.println(name);
		String regex = name + ".(pdf|txt|html|fb2|doc|docx)" + ".gz";
		System.out.println(regex);
		final Pattern p = Pattern.compile(regex);
		File[] files = root.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return p.matcher(file.getName()).matches();
			}
		});
		File file;
		try {
			file = files[0];
		}
		catch (NullPointerException e){
			return "redirect:/view-{name}-book";
		}
		String s = file.getName();
		int i = s.indexOf('.');
		int f = s.lastIndexOf('.');
		String contentType = "application/" + s.substring(i + 1,f);

		try {
			FileInputStream fis = new FileInputStream(file);
			InputStream gis = new GZIPInputStream(fis);
			byte[] bytes = IOUtils.toByteArray(gis);
			response.setContentType(contentType);
			response.setContentLength(bytes.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + s.substring(0, f) + "\"");

			FileCopyUtils.copy(bytes, response.getOutputStream());
			gis.close();

		} catch (IOException e) {
            return "redirect:/view-{name}-book";
		}
		return "redirect:/view-{name}-book";
	}

	@RequestMapping(value = { "/delete-{name}-book" }, method = RequestMethod.GET)
	public String deleteBook(@PathVariable String name, HttpSession session) {
        ArrayList<Book> list = new ArrayList<Book>();
        if (session.getAttribute("cashed") != null){
            list = (ArrayList<Book>) session.getAttribute("cashed");
        }
        for (Book b : list){
            if (name.equals(b.getName())){
                list.remove(b);
                break;
            }
        }
		service.deleteBookByName(name);
        session.setAttribute("cashed", list);
        service.deleteFile(name);
		return "redirect:/list";
	}
}