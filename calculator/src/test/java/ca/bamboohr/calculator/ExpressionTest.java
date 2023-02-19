package ca.bamboohr.calculator;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class ExpressionTest {

    Expression objectUnderTest;
    TestUtil testUtil;

    @BeforeEach
    void setUp() {
        objectUnderTest = new Expression();
        testUtil = new TestUtil();
    }

    @SneakyThrows
    @Test
    void closeSuccessfullyTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        testUtil.setField(objectUnderTest, Expression.Fields.secondOperand, new IntegerOperand(1));

        objectUnderTest.close();
    }

    @Test
    void closeFailedWhenOperatorIsMissingTest() {
        assertThatThrownBy(() -> objectUnderTest.close()).isInstanceOf(ParseException.class);
    }

    @Test
    void closeFailedWhenFirstOperandTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        assertThatThrownBy(() -> objectUnderTest.close()).isInstanceOf(ParseException.class);
    }

    @Test
    void closeFailedWhenSecondOperandTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        assertThatThrownBy(() -> objectUnderTest.close()).isInstanceOf(ParseException.class);
    }

    @SneakyThrows
    @Test
    void pushOperatorSuccessfullyTest() {
        objectUnderTest.push("add");
        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);

        objectUnderTest = new Expression();
        objectUnderTest.push("ADD");
        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);

        objectUnderTest = new Expression();
        objectUnderTest.push("AdD");
        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);

        objectUnderTest = new Expression();
        objectUnderTest.push("multiply");
        assertThat(objectUnderTest)
                .returns(Operator.MULTIPLY, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);

        objectUnderTest = new Expression();
        objectUnderTest.push("multiPly");
        assertThat(objectUnderTest)
                .returns(Operator.MULTIPLY, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);

        objectUnderTest = new Expression();
        objectUnderTest.push("MULTIPLY");
        assertThat(objectUnderTest)
                .returns(Operator.MULTIPLY, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @Test
    void pushOperatorUnknownOperatorTest() {
        assertThatThrownBy(() -> objectUnderTest.push("sub")).isInstanceOf(ParseException.class);

        assertThat(objectUnderTest)
                .returns(null, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @Test
    void pushOperatorWhenOperatorAlreadySetTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        assertThatThrownBy(() -> objectUnderTest.push("multiply")).isInstanceOf(ParseException.class);

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(null, Expression::getFirstOperand);
    }

    @SneakyThrows
    @Test
    void pushFirstOperandSuccessfullyTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        objectUnderTest.push("1");

        assertThat(objectUnderTest.getFirstOperand()).isEqualTo(new IntegerOperand(1));

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @Test
    void pushFirstOperandWithWrongFormatTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);

        assertThatThrownBy(() -> objectUnderTest.push("true")).isInstanceOf(ParseException.class);

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @SneakyThrows
    @Test
    void pushSecondOperandSuccessfullyTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));

        objectUnderTest.push("2");

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(new IntegerOperand(2), Expression::getSecondOperand);
    }

    @Test
    void pushSecondOperandWithWrongFormatTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        assertThatThrownBy(() -> objectUnderTest.push("true")).isInstanceOf(ParseException.class);

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @Test
    void pushOperatorWhenEverythingIsSetTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        testUtil.setField(objectUnderTest, Expression.Fields.secondOperand, new IntegerOperand(2));

        assertThatThrownBy(() -> objectUnderTest.push("add")).isInstanceOf(ParseException.class);
        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(new IntegerOperand(2), Expression::getSecondOperand);
    }

    @Test
    void pushOperandWhenEverythingIsSetTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        testUtil.setField(objectUnderTest, Expression.Fields.secondOperand, new IntegerOperand(2));

        assertThatThrownBy(() -> objectUnderTest.push("1")).isInstanceOf(ParseException.class);
        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(new IntegerOperand(2), Expression::getSecondOperand);
    }

    @Test
    void pushExpressionAsFirstOperandSuccessfullyTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        var expression = mock(Expression.class);
        assertThat(objectUnderTest.push(expression)).isTrue();

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(expression, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @Test
    void pushExpressionAsSecondOperandSuccessfullyTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));

        var expression = mock(Expression.class);
        assertThat(objectUnderTest.push(expression)).isTrue();

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(expression, Expression::getSecondOperand);
    }

    @Test
    void pushExpressionWhenOperatorNotSetYetTest() {
        assertThat(objectUnderTest.push(mock(Expression.class))).isFalse();

        assertThat(objectUnderTest)
                .returns(null, Expression::getOperator)
                .returns(null, Expression::getFirstOperand)
                .returns(null, Expression::getSecondOperand);
    }

    @Test
    void pushExpressionWhenEverythingIsSetTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        testUtil.setField(objectUnderTest, Expression.Fields.secondOperand, new IntegerOperand(2));

        assertThat(objectUnderTest.push(mock(Expression.class))).isFalse();

        assertThat(objectUnderTest)
                .returns(Operator.ADD, Expression::getOperator)
                .returns(new IntegerOperand(1), Expression::getFirstOperand)
                .returns(new IntegerOperand(2), Expression::getSecondOperand);
    }

    @Test
    void evaluateTest() {
        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        testUtil.setField(objectUnderTest, Expression.Fields.secondOperand, new IntegerOperand(2));

        assertThat(objectUnderTest.evaluate()).isEqualTo(3);
    }

    @Test
    void evaluateDeeperTest() {
        var addExpression = new Expression();
        testUtil.setField(addExpression, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(addExpression, Expression.Fields.firstOperand, new IntegerOperand(2));
        testUtil.setField(addExpression, Expression.Fields.secondOperand, new IntegerOperand(1));

        var multiply = new Expression();
        testUtil.setField(multiply, Expression.Fields.operator, Operator.MULTIPLY);
        testUtil.setField(multiply, Expression.Fields.firstOperand, new IntegerOperand(2));
        testUtil.setField(multiply, Expression.Fields.secondOperand, addExpression);

        testUtil.setField(objectUnderTest, Expression.Fields.operator, Operator.ADD);
        testUtil.setField(objectUnderTest, Expression.Fields.firstOperand, new IntegerOperand(1));
        testUtil.setField(objectUnderTest, Expression.Fields.secondOperand, multiply);

        assertThat(objectUnderTest.evaluate()).isEqualTo(7);
    }

}