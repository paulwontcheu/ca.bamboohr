package ca.bamboohr.calculator;

import lombok.SneakyThrows;

public class TestUtil {

    @SneakyThrows
    public void setField(Object instance, String field, Object value) {
        var declaredField = instance.getClass().getDeclaredField(field);
        declaredField.setAccessible(true);
        declaredField.set(instance, value);
    }
}
