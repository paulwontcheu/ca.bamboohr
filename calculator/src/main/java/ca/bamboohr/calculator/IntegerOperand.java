package ca.bamboohr.calculator;

public record IntegerOperand(int value) implements Operand {

    @Override
    public int evaluate() {
        return value();
    }
}
