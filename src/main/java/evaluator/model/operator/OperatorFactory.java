package evaluator.model.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.base.Preconditions;

public class OperatorFactory {
    
    private static final Map<Character, Supplier<Operator>> operations = new HashMap<>();
    static {
        operations.put('+', Addition::new);
        operations.put('-', Subtraction::new);
        operations.put('*', Multiplication::new);
        operations.put('/', Division::new);
    }
    
    public Operator getInstance(char op) {
        Preconditions.checkNotNull(op);
        Preconditions.checkArgument(isOperator(op), "%s is not a valid operator.");

        return operations.get(op).get();
    }

    public boolean isOperator(char c) {
        return operations.keySet().contains(c);
    }
}
