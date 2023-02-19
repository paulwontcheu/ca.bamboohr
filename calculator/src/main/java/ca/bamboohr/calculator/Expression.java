package ca.bamboohr.calculator;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ToString
@Getter
@FieldNameConstants
public class Expression implements Operand {
    private Operator operator;
    private Operand firstOperand;
    private Operand secondOperand;

    public void close() throws ParseException {
        if (!isClosed()) {
            throw new ParseException();
        }
    }

    private boolean isClosed() {
        return nonNull(operator) && nonNull(firstOperand) && nonNull(secondOperand);
    }

    public void push(String element) throws ParseException {
        if (isClosed()) {
            throw new ParseException();
        }
        if (isNull(operator)) {
            pushOperator(element);
        } else if (isNull(firstOperand)) {
            pushFirstOperand(element);
        } else if (isNull(secondOperand)) {
            pushSecondOperand(element);
        }
    }

    private void pushFirstOperand(String element) throws ParseException {
        try {
            firstOperand = new IntegerOperand(Integer.parseInt(element));
        } catch (NumberFormatException e) {
            throw new ParseException();
        }
    }

    private void pushSecondOperand(String element) throws ParseException {
        try {
            secondOperand = new IntegerOperand(Integer.parseInt(element));
        } catch (NumberFormatException e) {
            throw new ParseException();
        }
    }

    private void pushOperator(String element) throws ParseException {
        try {
            this.operator = Operator.valueOf(element.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException();
        }
    }

    public int evaluate() {
        return operator.calculate(firstOperand.evaluate(), secondOperand.evaluate());
    }

    public boolean push(Expression expression) {
        if (isClosed()) {
            return false;
        }
        if (isNull(operator)) {
            return false;
        }
        if (isNull(firstOperand)) {
            this.firstOperand = expression;
            return true;
        } else if (isNull(secondOperand)) {
            this.secondOperand = expression;
            return true;
        }
        return false;
    }

}
