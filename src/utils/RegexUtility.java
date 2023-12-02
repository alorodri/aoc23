package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtility {

    private final Pattern regex;
    private final String input;

    public RegexUtility(Pattern regex, final String input) {
        this.regex = regex;
        this.input = input;
    }

    public Matcher getMatches() {
        return this.regex.matcher(input);
    }
}
