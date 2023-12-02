package utils;

import java.util.*;
import java.util.stream.Stream;

public class Parser {

    final String text;
    public Parser(final String text) {
        this.text = text;
    }

    public Stream<String> split(String delimiter) {
        return Arrays.stream(text.split(delimiter));
    }

    public static Stream<String> split(String delimiter, String text) {
        return Arrays.stream(text.split(delimiter));
    }

}
