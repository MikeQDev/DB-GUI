package edu.easternct.CSC342.sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDriver {
	public static void main(String[] args) {

/*
 * 2. Build a test driver class that performs the following tasks.
 
a. Creates an arrayList for your class type.

b. Create some instances of your class.

c. Add the instance objects to the arrayList.

d. Using a for loop, loop through the arrayList 
Print out each object.
The output should look like the string your formatted in your toString() method.

e. Extra credit:  sort the objects in your arrayList prior to printing them out.
 */
		//create arraylist
		List<Product> products = new ArrayList<Product>();
		//create instances
		Product prodA = new Product(555, 100, "ProductA", "FinishA", 300);
		Product prodB = new Product(556, 100, "ProductB", "FinishB", 800);
		Product prodC = new Product(557, 100, "ProductC", "FinishC", 450);
		Product prodD = new Product(558, 100, "ProductD", "FinishD", 200);
		
		//add instances
		products.add(prodA);
		products.add(prodB);
		products.add(prodC);
		products.add(prodD);
		
		//loop thru arraylist, print each object
		for(Product p : products){
			System.out.println(p);
		}
		
		System.out.println("Sorting by price!");
		
		//sort the objects
		Collections.sort(products);
		for(Product p : products){
			System.out.println(p);
		}

	}

}
