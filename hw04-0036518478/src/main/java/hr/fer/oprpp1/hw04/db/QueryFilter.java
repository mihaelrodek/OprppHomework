package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * Class that represents filter for filtering records
 *
 * @author Mihael Rodek
 */
public class QueryFilter implements IFilter {

    /**
     * List of queries
     */
    List<ConditionalExpression> list;

    /**
     * Default constructor
     *
     * @param list
     */
    public QueryFilter(List<ConditionalExpression> list) {
        this.list = list;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression el : list)
            if (!el.getOperator().satisfied(el.getField().get(record), el.getString())) return false;

        return true;
    }

}
