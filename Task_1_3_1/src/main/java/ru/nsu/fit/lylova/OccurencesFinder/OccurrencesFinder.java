package ru.nsu.fit.lylova.OccurencesFinder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * The interface of a class that has a find function
 * that finds all occurrences of a string in the text.
 */
public interface OccurrencesFinder {
    /**
     * Function that finds all occurrences of string {@code pattern}
     * in the text that is contained in the stream {@code inputStream}.
     *
     * @param inputStream stream that contains text
     * @param pattern pattern string
     * @return list of indexes of all occurrences
     * @throws IOException when inputStream throws IOException
     */
    static List<Integer> find(InputStream inputStream, String pattern) throws IOException {
        return null;
    }
}
