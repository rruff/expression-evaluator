package evaluator.model.operator;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

class OrderedIntegerOperator implements OrderedOperator<Integer> {

    /**
     * Implements the actual operations.
     * 
     * <p>{@link Enum} provides a natural ordering (the order of item declarations)
     * for easy {@link Comparable} implementation.</p>
     */
    private enum IntOperator {
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

        final char symbol;
        
        IntOperator(char symbol) {
            this. symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

        public abstract Integer apply(Integer left, Integer right);
        
        private static final Map<Character, IntOperator> CHAR_TO_OPERATOR = Stream.of(values())
            .collect(toMap(IntOperator::getSymbol, e -> e));

        public static IntOperator fromChar(char c) {
            return CHAR_TO_OPERATOR.get(c);
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
        return intOperator.getSymbol();
    }
    
}
