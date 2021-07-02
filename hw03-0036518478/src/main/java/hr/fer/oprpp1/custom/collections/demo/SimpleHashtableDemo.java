package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class SimpleHashtableDemo {

    public static void main(String[] args) {

        {
            System.out.println("----------DEMO 1----------");
            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5);
            Integer kristinaGrade = examMarks.get("Kristina");
            System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
            System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4
        }

        {
            System.out.println("----------DEMO 2----------");

            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5); // overwrites old grade for Ivana
            for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
                System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            }
        }
        {
            System.out.println("----------DEMO 3----------");

            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5); // overwrites old grade for Ivana
            for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
                for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
                    System.out.printf(
                            "(%s => %d) - (%s => %d)%n",
                            pair1.getKey(), pair1.getValue(),
                            pair2.getKey(), pair2.getValue()
                    );
                }
            }
        }
        {
            System.out.println("----------DEMO 4----------");

            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5); // overwrites old grade for Ivana
            Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
            while (iter.hasNext()) {
                SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
                if (pair.getKey().equals("Ivana")) {
                    iter.remove(); // sam iterator kontrolirano uklanja trenutni element
                }
            }
            for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
                System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
            }
        }


        {
            System.out.println("----------DEMO 5----------");
            // create collection:
            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
// fill data:
            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5); // overwrites old grade for Ivana
            Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
            try {
                while (iter.hasNext()) {
                    SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
                    if (pair.getKey().equals("Ivana")) {
                        iter.remove();
                        iter.remove();
                    }
                }
            } catch (IllegalStateException ex) {
                System.out.printf("Exception " + ex.getMessage() + "\n");
            }
        }


        {
            System.out.println("----------DEMO 6----------");
            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5); // overwrites old grade for Ivana
            Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
            try {
                while (iter.hasNext()) {
                    SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
                    if (pair.getKey().equals("Ivana")) {
                        examMarks.remove("Ivana");
                    }
                }
            } catch (ConcurrentModificationException ex) {
                System.out.printf("Exception " + ex.getMessage());
            }
        }

        {
            System.out.println("----------DEMO 7----------");
            SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

            examMarks.put("Ivana", 2);
            examMarks.put("Ante", 2);
            examMarks.put("Jasna", 2);
            examMarks.put("Kristina", 5);
            examMarks.put("Ivana", 5); // overwrites old grade for Ivana
            Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
            while (iter.hasNext()) {
                SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
                System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
                iter.remove();
            }
            System.out.printf("Veličina: %d %n", examMarks.size());
        }

    }
}