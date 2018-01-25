package edu.yccc.cis174.michaellombard.gettingstarted;

public class Book {
	int id;
	String name,authorLast,authorFirst,publisher;  
	int quantity;  

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

	public String getAuthorLast() {
		return authorLast;
	}

	public void setAuthorLast(String authorLast) {
		this.authorLast = authorLast;
	}

	public String getAuthorFirst() {
		return authorFirst;
	}

	public void setAuthorFirst(String authorFirst) {
		this.authorFirst = authorFirst;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Book(int id, String name, String authorLast, String authorFirst, String publisher, int quantity) {  
		this.id = id;  
		this.name = name;  
		this.authorLast = authorLast;  
		this.authorFirst = authorFirst;  
		this.publisher = publisher;  
		this.quantity = quantity;  
	}  
	public Book() {
		
	}
}  