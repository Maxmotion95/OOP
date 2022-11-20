package ru.nsu.fit.lylova.OccurencesFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.Integer.max;

public class ApostolicoCrochemoreAlgorithm implements OccurrencesFinder {
    private static final int bufferSize = 1024;

    public static ArrayList<Integer> find(InputStream inputStream, String pattern) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        ArrayList<Integer> result = new ArrayList<>();
        if (pattern.length() == 0) {
            return result;
        }

        char[] buf1 = new char[Math.max(pattern.length(), bufferSize)];
        char[] buf2 = new char[Math.max(pattern.length(), bufferSize)];
        int count1 = readBuf(buf1, in);
        int currentPos = 0;

        while (count1 != 0) {
            int count2 = readBuf(buf2, in);
            String stringBuilder = String.valueOf(buf1, 0, count1) +
                    String.valueOf(buf2, 0, count2);

            ArrayList<Integer> tmp = aG(pattern, stringBuilder);
            for (int i: tmp) {
                result.add(i + currentPos);
            }

            currentPos += count1;
            count1 = count2;
            buf1 = buf2.clone();
        }
        return result;
    }

    private static int readBuf(char[] buf, BufferedReader in) throws IOException {
        int count, offset = 0;
        while (offset < buf.length && (count = in.read(buf, offset, buf.length - offset)) != -1) {
            offset += count;
        }
        return offset;
    }

    private static void getT(String str, int[] t) {
        int i = 0;
        int j = t[0] = -1;
        while (i < str.length()) {
            while (j > -1 && str.charAt(i) != str.charAt(j)) {
                j = t[j];
            }
            ++i;
            ++j;
            if (i < str.length() && str.charAt(i) == str.charAt(j)) {
                t[i] = t[j];
            } else {
                t[i] = j;
            }
        }
    }

    private static ArrayList<Integer> aG(String pattern, String text) {
        int[] t = new int[pattern.length() + 1];
        ArrayList<Integer> result = new ArrayList<>();

        getT(pattern, t);

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
                if (k >= l)
                    result.add(j);
            }
            j += i - t[i];
            if (i == l) {
                k = max(0, k - 1);
            }else if (t[i] <= l) {
                k = max(0, t[i]);
                i = l;
            }else {
                k = l;
                i = t[i];
            }
        }
        return result;
    }
}
