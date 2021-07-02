package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo homework
 */
public class Demo {
    public static void main(String[] args) {
        {
            System.out.println("----------DEMO-1----------");
            IComparisonOperator oper = ComparisonOperators.LIKE;
            System.out.println(oper.satisfied("Zagreb", "Aba*")); // false
            System.out.println(oper.satisfied("AAA", "AA*AA")); // false
            System.out.println(oper.satisfied("AAAA", "AA*AA")); // true
        }

        {
            System.out.println("----------DEMO-2----------");
            StudentRecord record = getSomehowOneRecord();
            System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
            System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
            System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));
        }
        {
            System.out.println("----------DEMO-3----------");
            IComparisonOperator oper = ComparisonOperators.EQUALS;
            System.out.println(oper.satisfied("Zagreb", "Zagreb ")); // false
            System.out.println(oper.satisfied("AAA", "AA*AA")); // false
            System.out.println(oper.satisfied("AAAA", "AAAA")); // true
        }
        {
            System.out.println("----------DEMO-4----------");
            ConditionalExpression expr = new ConditionalExpression(
                    FieldValueGetters.LAST_NAME,
                    "Bos*",
                    ComparisonOperators.LIKE
            );
            StudentRecord record = getSomehowOneRecord();
            boolean recordSatisfies = expr.getOperator().satisfied(
                    expr.getField().get(record), // returns lastName from given record
                    expr.getString() // returns "Bos*"
            );
            //System.out.println(recordSatisfies);
        }
        {
            System.out.println("----------DEMO-5----------");
            QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
            System.out.println("isDirectQuery(): " + qp1.isDirectQuery()); // true
            System.out.println("jmbag was: " + qp1.getQueriedJMBAG()); // 0123456789
            System.out.println("size: " + qp1.getQuery().size()); // 1
            QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
            System.out.println("isDirectQuery(): " + qp2.isDirectQuery()); // false
            //System.out.println(qp2.getQueriedJMBAG()); // would throw!
            System.out.println("size: " + qp2.getQuery().size()); // 2
        }

    }

    private static StudentRecord getSomehowOneRecord() {
        StudentRecord record1 = new StudentRecord("0036518478", "Rodek", "Mihael", 3);
        StudentRecord record2 = new StudentRecord("0036513428", "Patko", "Matko", 1);
        StudentRecord record3 = new StudentRecord("0036315418", "Zlatko", "Rajko", 4);
        List<String> database = new ArrayList<>();
        database.add(record1.toString());
        database.add(record2.toString());
        database.add(record3.toString());
        StudentDatabase db = new StudentDatabase(database);
        return db.forJMBAG("0036513428");
    }
}
