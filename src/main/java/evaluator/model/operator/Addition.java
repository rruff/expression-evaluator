package parser.model.operator;

public class Addition implements Operator {
    
    @Override
     public int execute(int left, int right) {
        return left + right;
    }
}
