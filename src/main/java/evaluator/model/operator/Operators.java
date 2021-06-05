package evaluator.model.operator;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Operators {
    private static final Map<Character, Supplier<Operator>> OPERATORS = new HashMap<>();
    static {
        OPERATORS.put('+', Addition::new);
        OPERATORS.put('-', Subtraction::new);
        OPERATORS.put('*', Multiplication::new);
        OPERATORS.put('/', Division::new);
    }
    
    public static Operator of(char c) {
        checkArgument(isOperator(c), "%s is not a valid operator", c);
        return OPERATORS.get(c).get();
    }

    public static boolean isOperator(char c) {
        return OPERATORS.keySet().contains(c);
    }

    private static abstract class AbstractOperator implements Operator, Comparable<Operator> {
        
        private final int precedence;

        AbstractOperator(int precedence) {
            this.precedence = precedence;
        }

        @Override
        public int getPrecedence() {
            return this.precedence;
        }

        @Override
        public int compareTo(Operator o) {
            return this.getPrecedence() - o.getPrecedence();
        }

        @Override
        public boolean isHigherPrecedenceThan(Operator other) {
            return compareTo(other) > 0;
        }
    }

    private static class Multiplication extends AbstractOperator {
        
        Multiplication() {
            super(16);
        }

        @Override
        public int execute(int left, int right) {
            return left * right;
        }

    }

    private static class Division extends AbstractOperator {
        
        Division() {
            super(8);
        }

        @Override
        public int execute(int left, int right) {
            return left / right;
        }
    }

    private static class Addition extends AbstractOperator {

        Addition() {
            super(4);
        }

        @Override
        public int execute(int left, int right) {
            return left + right;
        }
    }

    private static class Subtraction extends AbstractOperator {
        
        Subtraction() {
            super(2);
        }

        @Override
        public int execute(int left, int right) {
            return left - right;
        }
    }
}
