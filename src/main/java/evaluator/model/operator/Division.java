package evaluator.model.operator;

public class Division implements Operator, Comparable<Operator> {
    
    @Override
     public int execute(int left, int right) {
        return left / right;
    }

    @Override
    public int compareTo(Operator o) {
        return 0;
    }
}
