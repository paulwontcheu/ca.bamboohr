package ca.bamboohr.calculator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest {

    Application objectUnderTest;
    private ByteArrayOutputStream output;

    @BeforeEach
    void setUp() {
        output = new ByteArrayOutputStream();
        objectUnderTest = new Application(new PrintStream(output));
    }

    @AfterEach
    void tearDown() throws IOException {
        output.close();
    }

    @Test
    void simpleNumberTest() {
        objectUnderTest.run("45");

        assertThat(output.toString()).isEqualTo("45\n");
    }

    @Test
    void simpleAddExpressionTest() {
        objectUnderTest.run("(add 1 2)");

        assertThat(output.toString()).isEqualTo("3\n");
    }

    @Test
    void nestedExpressionTest() {
        objectUnderTest.run("(add 1 (multiply (add 2 1) 3))");

        assertThat(output.toString()).isEqualTo("10\n");
    }

    @Test
    void wrongExpressionTest() {
        objectUnderTest.run("(add a 1");

        assertThat(output.toString()).isEqualTo("Invalid Input\n");
    }
}