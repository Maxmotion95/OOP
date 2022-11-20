package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.OccurencesFinder.KnuthMorrisPrattAlgorithmWithZFunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws IOException {
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        String filename = sc.nextLine();

        File file = new File(filename);
        InputStream input = new FileInputStream(file);

        String str;
        str = sc.nextLine();
        var ans = KnuthMorrisPrattAlgorithmWithZFunction.find(input, str);
        System.out.println(ans);
    }
}