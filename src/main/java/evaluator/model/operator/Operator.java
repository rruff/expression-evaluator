package parser.model.operator;

public interface Operator {

    /**
     * Execute this operation on the left and right operands.
     * @param left the left operand.
     * @param right the right operand.
     * @return the result of the operation.
     */
    int execute(int left, int right);
}
