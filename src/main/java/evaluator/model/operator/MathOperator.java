package evaluator.model.operator;

import java.util.HashMap;
import java.util.Map;

public enum MathOperator implements Operator {

    SUBTRACTION('-') {
        public int execute(int left, int right) {
            return left - right;
        }
    },
    
    ADDITION('+') {
        @Override
        public int execute(int left, int right) {
            return left + right;
        }
    },
    
    DIVISION('/') {
        public int execute(int left, int right) {
            return left / right;
        }
    },
    
    MULTIPLICATION('*') {
        public int execute(int left, int right) {
            return left * right;
        }
    };

    private static Map<Character, MathOperator> operators = new HashMap<>();
    static {
        for (MathOperator o : values()) {
            operators.put(o.getSymbol(), o);
        }
    }
    
    public static MathOperator fromChar(char c) {
        if (!isOperator(c)) {
            throw new IllegalArgumentException(String.format("%s is not a valid operator", c));
        }
        return operators.get(c);
    }

    public static boolean isOperator(char c) {
        return operators.keySet().contains(c);
    }

    private final char symbol;

    private MathOperator(char c) {
        this.symbol = c;
    }

    public char getSymbol() {
        return this.symbol;
    }
}
