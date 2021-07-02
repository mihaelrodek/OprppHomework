package hr.fer.oprpp1.custom.collections.demo;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;
import hr.fer.oprpp1.custom.collections.Processor;

/**
 * Demo class that demonstrates usage of {@link ArrayIndexedCollection} and
 * {@link LinkedListIndexedCollection}. Commented lines represent expected
 * return. Demo cases are taken from task documentation.
 * 
 * @author Mihael Rodek
 *
 */
public class Demo {
	
	/**
	 * Main method that starts the program.
	 * 
	 */

	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position
		// 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		}
		;

		System.out.println("col1 elements:");
		// 20
		// San Francisco
		// Los Angeles
		col.forEach(new P());
		System.out.println("col1 elements again:");// [20, San Francisco, Los Angeles]
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		// 20
		// San Francisco
		// Los Angeles
		col2.forEach(new P());
		System.out.println("col2 elements again:");// [20, San Francisco, Los Angeles]
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(Integer.valueOf(20));
		System.out.println(col.contains(20)); // false

	}

}
