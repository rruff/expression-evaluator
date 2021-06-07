package evaluator.model.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

import com.google.common.base.Preconditions;

class OrderedIntegerOperator implements OrderedOperator<Integer> {

    /**
     * Implements the actual operations.
     * 
     * <p>{@link Enum} provides a natural ordering (the order of item declarations)
     * for easy {@link Comparable} implementation.</p>
     */
    private enum IntOperator implements BinaryOperator<Integer> {
        SUBTRACTION('-') {
            public Integer apply(Integer left, Integer right) {
                return left - right;
            }
        },
        ADDITION('+') {
            public Integer apply(Integer left, Integer right) {
                return left + right;
            }
        },
        DIVISION('/') {
            public Integer apply(Integer left, Integer right) {
                Preconditions.checkArgument(right != 0, "Cannot divide by 0");
                return left / right;
            }
        },
        MULTIPLICATION('*') {
            public Integer apply(Integer left, Integer right) {
                return left * right;
            }
        },
        POWER('^') {
            public Integer apply(Integer left, Integer right) {
                return (int)Math.pow((double)left, (double)right);
            }
        };

        final char op;
        
        IntOperator(char op) {
            this. op = op;
        }

        public char getChar() {
            return op;
        }

        private static final Map<Character, IntOperator> charToOperator = new HashMap<>();
        static {
            for (IntOperator operator : values()) {
                charToOperator.put(operator.op, operator);
            }
        }

        public static IntOperator fromChar(char c) {
            return charToOperator.get(c);
        }
    }
    
    private final IntOperator intOperator;
    
    public OrderedIntegerOperator(char op) {
        this.intOperator = IntOperator.fromChar(op);
    }

    @Override
    public Integer apply(Integer left, Integer right) {
        return intOperator.apply(left, right);
    }

    @Override
    public int compareTo(OrderedOperator<Integer> o) {
        return intOperator.compareTo(IntOperator.fromChar(o.getChar()));
    }

    @Override
    public char getChar() {
        return intOperator.getChar();
    }
    
}
