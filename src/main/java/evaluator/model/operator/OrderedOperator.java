package evaluator.model.operator;

import java.util.function.BinaryOperator;

/**
 * A binary operator that supports order of operations (PEMDAS).
 * 
 * @param <T> the type of the operands and the result.
 */
public interface OrderedOperator<T> extends BinaryOperator<T>, Comparable<OrderedOperator<Integer>> {
    
    /**
     * Returns the character that this operator represent.
     * 
     * @return the character that this operator represent.
     */
    char getChar();
}
