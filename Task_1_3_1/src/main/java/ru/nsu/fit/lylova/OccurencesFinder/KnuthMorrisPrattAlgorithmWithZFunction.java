package ru.nsu.fit.lylova.OccurencesFinder;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that implements interface {@code OccurrencesFinder}.
 * The find function is an implementation of the Knuth–Morris–Pratt algorithm
 * using the z-function instead of the prefix-function.
 */
public class KnuthMorrisPrattAlgorithmWithZFunction implements OccurrencesFinder {
    private static final int MIN_BUFFER_SIZE = 1024;

    /**
     * Constructs OccurrencesFinder that uses Knuth-Morris-Pratt algorithm with z-function.
     */
    public KnuthMorrisPrattAlgorithmWithZFunction() {
    }

    private static int[] zFunction(String str) {
        int[] ans = new int[str.length()];
        ans[0] = 0;
        int left = 0;
        int right = 0;
        for (int i = 1; i < str.length(); ++i) {
            ans[i] = max(0, min(right - i, ans[i - left]));
            while (i + ans[i] < str.length() && str.charAt(ans[i]) == str.charAt(i + ans[i])) {
                ++ans[i];
            }
            if (i + ans[i] > right) {
                left = i;
                right = i + ans[i];
            }
        }
        return ans;
    }

    /**
     * Function that finds all occurrences of string {@code pattern}
     * in the text that is contained in the stream {@code inputStream}.
     * The function is an implementation of the Knuth–Morris–Pratt algorithm using
     * the z-function instead of the prefix-function.
     * Time complexity is O(n + m), space complexity is O(m)
     * where n - length of text, m - length of string {@code pattern}.
     *
     * @param inputStream stream that contains text
     * @param pattern     pattern string
     * @return list of indexes of all occurrences
     * @throws IOException when inputStream throws IOException
     */
    public List<Integer> find(InputStream inputStream, String pattern)
            throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(inputStreamReader);
        ArrayList<Integer> result = new ArrayList<>();
        if (pattern.length() == 0) {
            return result;
        }

        int[] strZFunc = zFunction(pattern);

        char[] buf = new char[max(2 * pattern.length(), MIN_BUFFER_SIZE)];
        int offset = 0;
        int l = -1;
        int r = -1;
        int i = 0;
        int currentPos = 0;

        int countReadSymbols;
        while ((countReadSymbols = in.read(buf, offset, buf.length - offset)) != -1) {
            offset += countReadSymbols;
            while (i + pattern.length() - 1 < offset) {
                int ans;
                if (i < r) {
                    ans = strZFunc[i - l];
                } else {
                    ans = 0;
                }

                while (ans < pattern.length()
                        && i + ans < offset && buf[i + ans] == pattern.charAt(ans)) {
                    ++ans;
                }

                if (i + ans > r) {
                    l = i;
                    r = i + ans;
                }

                if (ans == pattern.length()) {
                    result.add(currentPos + i);
                }

                ++i;
            }
            if (i >= pattern.length()) {
                l -= i;
                r -= i;
                currentPos += i;

                for (int j = i; j < offset; ++j) {
                    buf[j - i] = buf[j];
                }
                offset -= i;
                i = 0;
            }
        }
        return result;
    }
}
