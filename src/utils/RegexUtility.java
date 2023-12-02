package utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtility {

    private final Pattern regex;
    private final Matcher matcher;
    private final String input;

    public RegexUtility(String regex, final String input) {
        this.regex = Pattern.compile(regex);
        this.input = input;
        this.matcher = this.regex.matcher(input);
    }

    public String group(int g) {
        if (this.matcher.find()) {
            return this.matcher.group(g);
        }
        return "";
    }

    public ArrayList<String> all() {
        final ArrayList<String> matches = new ArrayList<>();
        if (this.matcher.find()) {
            final int groupCount = this.matcher.groupCount();
            for (int i = 0; i < groupCount; i++) {
                matches.add(this.matcher.group(i));
            }
        }
        return matches;
    }
}
