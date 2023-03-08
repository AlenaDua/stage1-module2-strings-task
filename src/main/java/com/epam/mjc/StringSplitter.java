package com.epam.mjc;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringSplitter {
    /**
     * Splits given string applying all delimeters to it. Keeps order of result substrings as in source string.
     *
     * @param source source string
     * @param delimiters collection of delimiter strings
     * @return List of substrings
     */
    public List<String> splitByDelimiters(String source, Collection<String> delimiters) {
        StringTokenizer tokenizer = new StringTokenizer(source, String.join("|", delimiters));
        ArrayList<String> result = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            result.add(tokenizer.nextToken());
        }
        return result;
    }
}
