package evaluator.model.operator;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public final class Operators {

    private static final Set<Character> OPERATORS = ImmutableSet.of('^', '*', '/', '+', '-');

    public static OrderedOperator<Integer> fromChar(char c) {
        checkArgument(isOperator(c), "%s is not a valid operator", c);
        return new OrderedIntegerOperator(c);
    }

    public static boolean isOperator(char c) {
        return OPERATORS.contains(c);
    }
}
