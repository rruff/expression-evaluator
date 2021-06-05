package evaluator.model.operator;

public interface Operator {

    /**
     * Execute this operation on the left and right operands.
     * @param left the left operand.
     * @param right the right operand.
     * @return the result of the operation.
     */
    int execute(int left, int right);

    int getPrecedence();

    /**
     * Returns true if this operator has higher precedence than, or equal to, {@code other}, else false.
     * @param other the Operator instance to compare.
     * @return true if this operator has higher precedence than, or equal to, {@code other}, else false.
     */
    boolean precedes(Operator other);
}
