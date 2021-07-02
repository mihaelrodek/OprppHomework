package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AlInOneTest {

    @Test
    public void testTrueQuery() {
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName LIKE \"O*ć\"");
        StudentRecord record = new StudentRecord("0123456789", "Obilinović", "Romano", 0);
        QueryFilter filter = new QueryFilter(qp2.getQuery());

        assertTrue(filter.accepts(record));

    }

    @Test
    public void testFalseQuery() {
        QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName > \"R\"");
        StudentRecord record = new StudentRecord("0123456789", "Darko", "Romano", 80);
        QueryFilter filter = new QueryFilter(qp2.getQuery());

        assertFalse(filter.accepts(record));

    }

    @Test
    public void lessTrueTest() {
        assertTrue(ComparisonOperators.LESS.satisfied("Ana", "Jasna"));
    }

    @Test
    public void lessFalseTest() {
        assertFalse(ComparisonOperators.LESS.satisfied("Jure", "Antica"));
    }

    @Test
    public void lessOrEqualsTrueTest() {
        assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("Ana", "Ana"));
    }

    @Test
    public void lessOrEqualsFalseTest() {
        assertFalse(ComparisonOperators.LESS_OR_EQUALS.satisfied("Marko", "Antica"));
    }

    @Test
    public void greaterTrueTest() {
        assertTrue(ComparisonOperators.GREATER.satisfied("Stipica", "Milica"));
    }

    @Test
    public void greaterEqualsTrueTest() {
        assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Anica", "Anica"));
    }

    @Test
    public void greaterEqualsFalseTest() {
        assertFalse(ComparisonOperators.GREATER_OR_EQUALS.satisfied("Jure", "Špiro"));
    }

    @Test
    public void equalsTrueTest() {
        assertTrue(ComparisonOperators.EQUALS.satisfied("Ana", "Ana"));
    }

    @Test
    public void equalsFalseTest() {
        assertFalse(ComparisonOperators.EQUALS.satisfied("Jure", "Antica"));
    }

    @Test
    public void notEqualsTrueTest() {
        assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("Ana", "Jasna"));
    }

    @Test
    public void notEqualsFalseTest() {
        assertFalse(ComparisonOperators.NOT_EQUALS.satisfied("Jure", "Jure"));
    }

    @Test
    public void likeTest() {
        assertFalse(ComparisonOperators.LIKE.satisfied("Zagreb", "Aba*"));
        assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
        assertTrue(ComparisonOperators.LIKE.satisfied("AAAA", "AA*AA"));
    }
}
