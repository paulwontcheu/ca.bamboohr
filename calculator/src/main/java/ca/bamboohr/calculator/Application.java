package ca.bamboohr.calculator;

import java.io.PrintStream;
import java.util.Stack;

class Application {

    public static final String INVALID_INPUT = "Invalid Input";
    private final PrintStream output;
    private final Stack<Expression> expressionStack = new Stack<>();
    private Expression expression;

    public Application(PrintStream output) {
        this.output = output;
    }

    public void run(String expression) {
        if (isInteger(expression)) {
            output.println(expression);
            return;
        }
        var allCharactersProcessedCorrectly = Tokenizer.builder()
                .input(expression)
                .build()
                .run()
                .allMatch(this::processChar);
        if (allCharactersProcessedCorrectly) {
            output.println(this.expression.evaluate());
        } else {
            output.println(INVALID_INPUT);
        }
    }

    private boolean processChar(String token) {
        return switch (token) {
            case "(" -> startExpression();
            case ")" -> closeExpression();
            default -> addExpressionElement(token);
        };
    }

    private boolean addExpressionElement(String token) {
        if (expressionStack.isEmpty()) {
            return false;
        }
        try {
            expressionStack.peek().push(token);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean closeExpression() {
        if (expressionStack.isEmpty()) {
            return false;
        }
        try {
            expressionStack.pop().close();
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean startExpression() {
        try {
            var newExpression = new Expression();
            if (expressionStack.isEmpty()) {
                this.expression = newExpression;
            } else {
                expressionStack.peek().push(newExpression);
            }
            expressionStack.push(newExpression);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    private boolean isInteger(String expression) {
        try {
            Integer.parseInt(expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
