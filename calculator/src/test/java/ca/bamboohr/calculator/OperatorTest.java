package ca.bamboohr.calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OperatorTest {

    @Test
    void addTest() {
        assertThat(Operator.ADD.calculate(1, 2)).isEqualTo(3);
    }

    @Test
    void multiplyTest() {
        assertThat(Operator.MULTIPLY.calculate(3, 2)).isEqualTo(6);
    }
}