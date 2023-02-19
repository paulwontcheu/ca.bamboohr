package ca.bamboohr.calculator;

import lombok.ToString;

@ToString
public enum Operator implements ExpressionElement {
    ADD,
    MULTIPLY;

    public int calculate(int firstOperand, int secondOperand) {
        return switch (this) {
            case ADD -> firstOperand + secondOperand;
            case MULTIPLY -> firstOperand * secondOperand;
        };
    }
}
