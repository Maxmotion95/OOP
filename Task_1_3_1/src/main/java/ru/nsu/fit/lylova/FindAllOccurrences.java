package ru.nsu.fit.lylova;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FindAllOccurrences {
    public static ArrayList<Integer> calc(BufferedReader in, String str) throws IOException {
        ArrayList<Integer> res = new ArrayList<>();
        if (str.length() == 0) {
            return res;
        }

        int[] strZFunc = zFunc(str);

        char[] buf1 = new char[max(str.length(), 1024)];
        char[] buf2 = new char[max(str.length(), 1024)];
        int count1 = readBuf(buf1, in);
        int posnow = 0;

        while (count1 != 0) {
            int count2 = readBuf(buf2, in);
            int[] tmp = zFunc(str, strZFunc, buf1, count1, buf2, count2);
            for (int i = 0; i < count1; ++i) {
                if (tmp[i] == str.length()) {
                    res.add(i + posnow);
                }
            }
            posnow += count1;
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

    private static int[] zFunc(String str) {
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

    private static int[] zFunc(String str, int[] zStr, char[] buf1, int buf1size, char[] buf2, int buf2size) {
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
