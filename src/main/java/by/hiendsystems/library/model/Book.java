package by.hiendsystems.library.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="BOOK")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Pattern(regexp = "^[a-zA-Zа-яА-Я0-9 ]*$", message="Only letters numbers and space chars")
	@Size(min=3, max=50, message="Min length: 3, max - 50")
	@Column(name = "NAME", unique=true, nullable = false)
	private String name;

	@NotNull
	@Size(min=3, max=50, message="Min length: 3, max - 50")
	@Column(name = "AUTHOR", nullable = false)
	private String author;

	@NotNull
	@DecimalMax(value = "2017", message="Enter actual year, now 2017")
	@DecimalMin(value = "0", message="Perhaps, but hardly")
	@Column(name = "YEAR", nullable = false)
	private Integer year;

	@NotNull
	@Size(min=3, max=255, message="Min length: 3, max - 255")
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Transient
	private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getId() != book.getId()) return false;
        if (getYear() != book.getYear()) return false;
        if (!getName().equals(book.getName())) return false;
        if (!getAuthor().equals(book.getAuthor())) return false;
        return getDescription().equals(book.getDescription());
    }

    @Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", author="
				+ author + ", year=" + year + ", description=" + description + ", content=" + content + "]";
	}
}
