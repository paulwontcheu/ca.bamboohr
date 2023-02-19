package ca.bamboohr.calculator;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

@Builder
@RequiredArgsConstructor
public class Tokenizer {

    private final String input;

    public Stream<String> run() {
        return Arrays.stream(input.split(" "))
                .map(String::trim)
                .filter(not(String::isEmpty))
                .flatMap(s -> (s.contains("(") || s.contains(")")) ? tokenize(s) : Stream.of(s));
    }

    private Stream<String> tokenize(String s) {
        List<String> result = new ArrayList<>();
        StringBuilder next = new StringBuilder();
        for (Character c : s.toCharArray()) {
            if (c == '(' || c == ')') {
                if (next.isEmpty()) {
                    result.add(c.toString());
                    continue;
                }
                result.add(next.toString());
                result.add(c.toString());
                next = new StringBuilder();
            } else {
                next.append(c);
            }
        }
        if (!next.isEmpty()) {
            result.add(next.toString());
        }
        return result.stream();
    }

}
