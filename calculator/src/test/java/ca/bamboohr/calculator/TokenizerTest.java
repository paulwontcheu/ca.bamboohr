package ca.bamboohr.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void runTest() {
        var tokens = Tokenizer.builder().input("(add 1 2)").build().run();

        assertThat(tokens)
                .isNotNull()
                .isNotEmpty()
                .hasSize(5)
                .containsExactly("(", "add", "1", "2", ")");
    }

}