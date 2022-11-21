package ru.nsu.fit.lylova.OccurencesFinder;

import static java.lang.Math.max;
import static java.lang.Math.min;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class that implements interface {@code OccurrencesFinder}.
 * The find function is an implementation of the Knuth–Morris–Pratt algorithm
 * using the z-function instead of the prefix-function.
 */
public class KnuthMorrisPrattAlgorithmWithZFunction implements OccurrencesFinder {
    private static final int bufferSize = 1024;

    /**
     * Function that finds all occurrences of string {@code pattern}
     * in the text that is contained in the stream {@code inputStream}.
     * The function is an implementation of the Knuth–Morris–Pratt algorithm using
     * the z-function instead of the prefix-function.
     * Time complexity is O(n + m), space complexity is O(m)
     * where n - length of text, m - length of string {&code pattern}.
     *
     * @param inputStream stream that contains text
     * @param pattern     pattern string
     * @return list of indexes of all occurrences
     * @throws IOException when inputStream throws IOException
     */
    public static ArrayList<Integer> find(InputStream inputStream, String pattern) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        ArrayList<Integer> res = new ArrayList<>();
        if (pattern.length() == 0) {
            return res;
        }

        int[] strZFunc = zFunction(pattern);

        char[] buf1 = new char[max(pattern.length(), bufferSize)];
        char[] buf2 = new char[max(pattern.length(), bufferSize)];
        int count1 = readBuf(buf1, in);
        int currentPos = 0;

        while (count1 != 0) {
            int count2 = readBuf(buf2, in);
            int[] tmp = zFunction(pattern, strZFunc, buf1, count1, buf2, count2);
            for (int i = 0; i < count1; ++i) {
                if (tmp[i] == pattern.length()) {
                    res.add(i + currentPos);
                }
            }
            currentPos += count1;
            count1 = count2;
            buf1 = buf2.clone();
        }
        return res;
    }

    private static int readBuf(char[] buf, BufferedReader in) throws IOException {
        int count, offset = 0;
        while (offset < buf.length && (count = in.read(buf, offset, buf.length - offset)) != -1) {
            offset += count;
        }
        return offset;
    }

    private static int[] zFunction(String str) {
        int[] ans = new int[str.length()];
        ans[0] = 0;
        int left = 0, right = 0;
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

    private static int[] zFunction(String str, int[] zStr, char[] buf1, int buf1size, char[] buf2, int buf2size) {
        int[] ans = new int[buf1size];
        StringBuilder bufSum = new StringBuilder();
        bufSum.append(buf1, 0, buf1size);
        bufSum.append(buf2, 0, buf2size);

        int left = -1, right = -1;
        for (int i = 0; i < buf1size; ++i) {
            if (i < right) {
                ans[i] = zStr[i - left];
            } else {
                ans[i] = 0;
            }
            while (ans[i] < str.length()
                    && i + ans[i] < bufSum.length()
                    && str.charAt(ans[i]) == bufSum.charAt(i + ans[i])) {
                ++ans[i];
            }
            if (i + ans[i] > right) {
                left = i;
                right = i + ans[i];
            }
        }
        return ans;
    }
}
