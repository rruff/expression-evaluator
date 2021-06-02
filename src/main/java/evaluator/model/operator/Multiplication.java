package evaluator.model.operator;

public class Multiplication implements Operator {
    
    @Override
    public int execute(int left, int right) {
        return left * right;
    }
}
