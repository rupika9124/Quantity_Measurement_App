package UC13;

import java.util.function.DoubleBinaryOperator;

public enum ArithmeticOperation {
    ADD((a, b) -> a+b),
    SUBTRACT((a, b) -> a-b),
    DIVIDE((a, b) -> {
        if(b == 0){
            throw new ArithmeticException("Cannot divide by 0");
        }
        return a/b;
    });

    private final DoubleBinaryOperator op;

    ArithmeticOperation(DoubleBinaryOperator op){
        this.op=op;
    }
    public double compute(double a, double b){
        return op.applyAsDouble(a, b);
    }
}
