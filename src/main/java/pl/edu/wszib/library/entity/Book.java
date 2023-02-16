package pl.edu.wszib.library.entity;

public class Book {

    private int id;
    private String title;
    private String author;
    private String isbn;
    private boolean avaliable;

    public Book() {
    }

    public Book(int id, String title, String author, String isbn, boolean avaliable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.avaliable = avaliable;
    }

    public int getId() {
        return id;
    }

    public boolean isAvaliable() {
        return avaliable;
    }

    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    public String getTitle() {
        return title.toUpperCase();
    }

    public void setTitle(String title) {
        this.title = title.toUpperCase();
    }

    public String getAuthor() {
        return author.toUpperCase();
    }

    public void setAuthor(String author) {
        this.author = author.toUpperCase();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Book =  ")
                .append("id: ")
                .append(id)
                .append("\ttitle: \"")
                .append(title)
                .append("\"")
                .append("\tauthor: ")
                .append(author)
                .append("\tisbn: ")
                .append(isbn)
                .append("\tavaliable: ")
                .append(avaliable)
                .toString();
    }
}
