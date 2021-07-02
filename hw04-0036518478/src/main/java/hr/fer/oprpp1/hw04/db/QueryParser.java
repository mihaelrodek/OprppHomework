package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

    ConditionalExpression expression;
    List<ConditionalExpression> expressionsEntries;
    boolean directQuery = false;
    String queriedJmbag = null;
    int currentIndex = 0;
    char[] array = null;
    boolean email = false;

    public QueryParser(String query) {

        String[] expressionSplit = query.split("\\s+(?i)and\\s+");
        expressionsEntries = new ArrayList<>();

        for (String expr : expressionSplit)
            expressionsEntries.add(evaluateExpression(expr));

        if (expressionsEntries.size() == 1 && email) {
            directQuery = true;
            queriedJmbag = expressionSplit[0].replaceAll("jmbag", "");
            array = queriedJmbag.toCharArray();
            while (currentIndex < array.length) {
                if (array[currentIndex] == '=' || array[currentIndex] == ' ') currentIndex++;
                if (array[currentIndex] == '\"') break;
            }
            currentIndex++;
            String str = "";
            for (int i = currentIndex; array[i] != '\"'; i++) {
                str += array[i];
            }
            queriedJmbag = str;
            this.expression = expressionsEntries.get(0);
        }
    }

    private IComparisonOperator getOperator(String input) {
        switch (input) {
            case "<" -> {
                return ComparisonOperators.LESS;
            }
            case "<=" -> {
                return ComparisonOperators.LESS_OR_EQUALS;
            }
            case ">=" -> {
                return ComparisonOperators.GREATER_OR_EQUALS;
            }
            case ">" -> {
                return ComparisonOperators.GREATER;
            }
            case "!=" -> {
                return ComparisonOperators.NOT_EQUALS;
            }
            case "=" -> {
                return ComparisonOperators.EQUALS;
            }
            case "LIKE" -> {
                return ComparisonOperators.LIKE;
            }
            default -> throw new UnsupportedOperationException("Unsupported operation");
        }
    }

    private ConditionalExpression evaluateExpression(String expr) {
        ConditionalExpression cond;
        Pattern pattern = Pattern.compile
                ("\\s*(lastName|firstName|jmbag)\\s*(<=|>=|=|<|>|!=|LIKE)\\s*\"([-a-zA-Z0-9*ČčĆćĐđŠšŽž\\s]+)\"");
        Matcher matcher = pattern.matcher(expr);
        if (matcher.find()) {
            IFieldValueGetter field = getField(matcher.group(1).trim());
            IComparisonOperator operator = getOperator(matcher.group(2).trim());
            String literal = matcher.group(3);
            cond = new ConditionalExpression(field, literal, operator);
        } else
            throw new IllegalArgumentException("Entered invalid expression");
        return cond;
    }


    private IFieldValueGetter getField(String field) {
        switch (field.toLowerCase()) {
            case "firstname" -> {
                return FieldValueGetters.FIRST_NAME;
            }
            case "lastname" -> {
                return FieldValueGetters.LAST_NAME;
            }
            case "jmbag" -> {
                email = true;
                return FieldValueGetters.JMBAG;
            }
            default -> throw new IllegalArgumentException("Invalid enter");
        }
    }

    public boolean isDirectQuery() {
        return directQuery;
    }

    public String getQueriedJMBAG() {
        if (queriedJmbag == null) throw new IllegalStateException();
        return queriedJmbag;
    }

    public List<ConditionalExpression> getQuery() {
        return expressionsEntries;
    }
}
