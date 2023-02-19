package ca.bamboohr.calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IntegerOperandTest {

    @Test
    void evaluateTest() {
        assertThat(new IntegerOperand(1).evaluate()).isEqualTo(1);
    }
}