package edu.yccc.cis174.michaellombard.gettingstarted;
//Author: Michael Lombard
//Class: CIS174
//Instructor: M. Bourgeois

//4 Pillars of Object Oriented Programming
//Abstraction - Representing the traits and actions of a real object in code
//Encapsulation - Subdividing code into reusable , smaller chunks assigned to specific tasks
//Inheritance - Code reuse based on the hierarchy of objects above it
//Polymorphism - Having different actions for methods of the same name

//import dependencies
import java.util.ArrayList;
import java.util.List;

//main class
public class GettingStarted {

	//Main Method
		public static void main(String[] args) {  
			//Creating list of books  
			List<Book> booklist=new ArrayList<Book>();  
			//Create books  
			Book b1=new Book(1,"The Eye of the Storm","Jordan","Robert","TOR",21);  
			Book b2=new Book(2,"The Adventures of Tom Sawyer","Clemens","Samuel","PCH",3);  
			Book b3=new Book(3,"Oathbringer","Sanderson","Brandon","TOR",12);  
			//Add books to list  
			booklist.add(b1);  
			booklist.add(b2);  
			booklist.add(b3);  
			//Go through list  
			for(Book b:booklist){  
				System.out.println(b.id+" "+b.name+" "+b.authorLast+", "+b.authorFirst+" "+b.publisher+" "+b.quantity);  
			}  
		}  

		
}
