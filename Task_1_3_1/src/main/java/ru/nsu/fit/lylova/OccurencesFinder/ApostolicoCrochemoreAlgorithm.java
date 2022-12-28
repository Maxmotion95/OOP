package ru.nsu.fit.lylova.OccurencesFinder;

import static java.lang.Integer.max;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements interface {@code OccurrencesFinder}.
 * The find function is an implementation of the Apostolico-Crochemore algorithm.
 */
public class ApostolicoCrochemoreAlgorithm implements OccurrencesFinder {
    private static final int MIN_BUFFER_SIZE = 1024;

    /**
     * Constructs OccurrencesFinder that uses Apostolico Crochemore algorithm.
     */
    public ApostolicoCrochemoreAlgorithm() {
    }

    /**
     * Function that finds all occurrences of string {@code pattern}
     * in the text that is contained in the stream {@code inputStream}.
     * The function is an implementation of the Apostolico-Crochemore algorithm.
     * Time complexity is O(n + m), space complexity is O(m)
     * where n - length of text, m - length of string {@code pattern}.
     *
     * @param inputStream stream that contains text
     * @param pattern     pattern string
     * @return list of indexes of all occurrences
     * @throws IOException when inputStream throws IOException
     */
    public List<Integer> find(InputStream inputStream,
                                     String pattern) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,
                StandardCharsets.UTF_8));
        ArrayList<Integer> result = new ArrayList<>();
        if (pattern.length() == 0) {
            return result;
        }

        char[] firstPartOfText = new char[Math.max(pattern.length(), MIN_BUFFER_SIZE)];
        char[] secondPartOfText = new char[Math.max(pattern.length(), MIN_BUFFER_SIZE)];
        int countSymbolsInFirstPartOfText = readBuf(firstPartOfText, in);
        int currentPos = 0;

        while (countSymbolsInFirstPartOfText != 0) {
            int countSymbolsInSecondPartOfText = readBuf(secondPartOfText, in);
            String text = String.valueOf(firstPartOfText, 0, countSymbolsInFirstPartOfText)
                    + String.valueOf(secondPartOfText, 0, countSymbolsInSecondPartOfText);

            ArrayList<Integer> tmp = useApostolicoCrochemoreAlgorithm(pattern, text);
            for (int i : tmp) {
                if (i < countSymbolsInFirstPartOfText) {
                    result.add(i + currentPos);
                }
            }

            currentPos += countSymbolsInFirstPartOfText;
            countSymbolsInFirstPartOfText = countSymbolsInSecondPartOfText;
            firstPartOfText = secondPartOfText.clone();
        }
        return result;
    }

    private static int readBuf(char[] buf, BufferedReader in) throws IOException {
        int count;
        int offset = 0;
        while (offset < buf.length && (count = in.read(buf, offset, buf.length - offset)) != -1) {
            offset += count;
        }
        return offset;
    }

    private static void getBiggestTaggedBorder(String str, int[] biggestTaggedBorder) {
        int i = 0;
        int j = biggestTaggedBorder[0] = -1;
        while (i < str.length()) {
            while (j > -1 && str.charAt(i) != str.charAt(j)) {
                j = biggestTaggedBorder[j];
            }
            ++i;
            ++j;
            if (i < str.length() && str.charAt(i) == str.charAt(j)) {
                biggestTaggedBorder[i] = biggestTaggedBorder[j];
            } else {
                biggestTaggedBorder[i] = j;
            }
        }
    }

    private static ArrayList<Integer> useApostolicoCrochemoreAlgorithm(String pattern, String text) {
        int[] biggestTaggedBorder = new int[pattern.length() + 1];
        ArrayList<Integer> result = new ArrayList<>();

        getBiggestTaggedBorder(pattern, biggestTaggedBorder);

        int l = 1;
        while (l < pattern.length() && pattern.charAt(l - 1) == pattern.charAt(l)) {
            ++l;
        }
        if (l == pattern.length()) {
            l = 0;
        }

        int i = l;
        int j = 0;
        int k = 0;
        while (j <= text.length() - pattern.length()) {
            while (i < pattern.length() && pattern.charAt(i) == text.charAt(i + j)) {
                ++i;
            }
            if (i >= pattern.length()) {
                while (k < l && pattern.charAt(k) == text.charAt(j + k)) {
                    ++k;
                }
                if (k >= l) {
                    result.add(j);
                }
            }
            j += i - biggestTaggedBorder[i];
            if (i == l) {
                k = max(0, k - 1);
            } else if (biggestTaggedBorder[i] <= l) {
                k = max(0, biggestTaggedBorder[i]);
                i = l;
            } else {
                k = l;
                i = biggestTaggedBorder[i];
            }
        }
        return result;
    }
}
