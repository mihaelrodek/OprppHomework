package hr.fer.oprpp1.custom.collections.demo;

import java.util.NoSuchElementException;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.Collection;
import hr.fer.oprpp1.custom.collections.ElementsGetter;
import hr.fer.oprpp1.custom.collections.LinkedListIndexedCollection;

public class Demo {
	public static void main(String[] args) {

		// Demo 1
		{
			System.out.println("----------DEMO 1----------");
			Collection col = new ArrayIndexedCollection();
			col.add("Ivo");
			col.add("Ana");
			col.add("Jasna");
			ElementsGetter getter = col.createElementsGetter();
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
			System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		}

		// Demo 2
		{
			System.out.println("----------DEMO 2----------");
			Collection col = new LinkedListIndexedCollection();
			col.add("Ivo");
			col.add("Ana");
			col.add("Jasna");
			ElementsGetter getter = col.createElementsGetter();
			System.out.println("Jedan element: " + getter.getNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
			try {
				System.out.println("Jedan element: " + getter.getNextElement());
			} catch (NoSuchElementException exc) {
				System.out.println("Excepton: " + exc.getMessage());
			}
		}

		// Demo 3
		{
			System.out.println("----------DEMO 3----------");
			Collection col = new LinkedListIndexedCollection();
			col.add("Ivo");
			col.add("Ana");
			col.add("Jasna");
			ElementsGetter getter1 = col.createElementsGetter();
			ElementsGetter getter2 = col.createElementsGetter();
			System.out.println("Jedan element: " + getter1.getNextElement());
			System.out.println("Jedan element: " + getter1.getNextElement());
			System.out.println("Jedan element: " + getter2.getNextElement());
			System.out.println("Jedan element: " + getter1.getNextElement());
			System.out.println("Jedan element: " + getter2.getNextElement());
		}

		// Demo 4
		{
			System.out.println("----------DEMO 4----------");
			Collection col1 = new ArrayIndexedCollection();
			Collection col2 = new ArrayIndexedCollection();
			col1.add("Ivo");
			col1.add("Ana");
			col1.add("Jasna");
			col2.add("Jasmina");
			col2.add("Štefanija");
			col2.add("Karmela");
			ElementsGetter getter1 = col1.createElementsGetter();
			ElementsGetter getter2 = col1.createElementsGetter();
			ElementsGetter getter3 = col2.createElementsGetter();
			System.out.println("Jedan element: " + getter1.getNextElement());
			System.out.println("Jedan element: " + getter1.getNextElement());
			System.out.println("Jedan element: " + getter2.getNextElement());
			System.out.println("Jedan element: " + getter3.getNextElement());
			System.out.println("Jedan element: " + getter3.getNextElement());
		}

		// Demo 5
		{
			System.out.println("----------DEMO 5----------");

			Collection col = new ArrayIndexedCollection();
			col.add("Ivo");
			col.add("Ana");
			col.add("Jasna");
			ElementsGetter getter = col.createElementsGetter();
			System.out.println("Jedan element: " + getter.getNextElement());
			System.out.println("Jedan element: " + getter.getNextElement());
			col.clear();
			try {
				System.out.println("Jedan element: " + getter.getNextElement());
			} catch (Exception exc) {
				System.out.println("Exception: " + exc.getMessage());
			}

			Collection col2 = new LinkedListIndexedCollection();
			col2.add("Ivo");
			col2.add("Ana");
			col2.add("Jasna");
			ElementsGetter getter2 = col2.createElementsGetter();
			System.out.println("Jedan element: " + getter2.getNextElement());
			System.out.println("Jedan element: " + getter2.getNextElement());
			col2.clear();
			try {
				System.out.println("Jedan element: " + getter2.getNextElement());
			} catch (Exception exc) {
				System.out.println("Exception: " + exc.getMessage());
			}
		}
		// Demo 6
		{
			System.out.println("----------DEMO 6----------");
			Collection col = new LinkedListIndexedCollection();
			col.add("Ivo");
			col.add("Ana");
			col.add("Jasna");
			ElementsGetter getter = col.createElementsGetter();
			getter.getNextElement();
			getter.processRemaining(System.out::println);

		}
	}

}
