package evaluator.model.operator;

import java.util.HashMap;
import java.util.Map;

public enum MathOperator implements Operator {
    
    ADDITION('+') {
        public int execute(int left, int right) {
            return left + right;
        }
    },

    SUBTRACTION('-') {
        public int execute(int left, int right) {
            return left - right;
        }
    },

    MULTIPLICATION('*') {
        public int execute(int left, int right) {
            return left * right;
        }
    },

    DIVISION('/') {
        public int execute(int left, int right) {
            return left / right;
        }
    };

    private static Map<Character, MathOperator> operators = new HashMap<>();
    static {
        for (MathOperator o : values()) {
            operators.put(o.getSymbol(), o);
        }
    }
    
    public static Operator fromChar(char c) {
        if (!operators.keySet().contains(c)) {
            throw new IllegalArgumentException(String.format("%s is not a valid operator", c));
        }
        return operators.get(c);
    }

    private final char symbol;

    private MathOperator(char c) {
        this.symbol = c;
    }

    public char getSymbol() {
        return this.symbol;
    }
}
