package ca.bamboohr.calculator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

class MainTest {

    @Test
    void mainTest() {
        try (
                var mockedConstruction = mockConstruction(
                        Application.class,
                        (mock, context) -> assertThat(context.arguments().get(0)).isEqualTo(System.out)
                )
        ) {
            Main.main("(add", "2", "multiply(3", "4))");
            var constructed = mockedConstruction.constructed();
            assertThat(constructed).hasSize(1);

            verify(constructed.get(0)).run("(add 2 multiply(3 4))");

        }
    }
}