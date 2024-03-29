package evaluator.model.operator;

import java.util.function.BinaryOperator;

/**
 * A binary operator that supports order of operations (PEMDAS).
 * 
 * @param <T> the type of the operands and the result.
 */
public interface OrderedOperator<T> extends BinaryOperator<T>, Comparable<OrderedOperator<T>> {
    
    /**
     * Returns the character that this operator represents.
     * 
     * @return the character that this operator represents.
     */
    char getChar();
}
