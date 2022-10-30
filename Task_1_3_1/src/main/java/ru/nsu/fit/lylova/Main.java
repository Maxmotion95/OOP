package ru.nsu.fit.lylova;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws IOException {
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        String filename = sc.nextLine();

        File file = new File(filename);
        BufferedReader input = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));

        String str;
        str = sc.nextLine();
        var ans = FindAllOccurrences.calc(input, str);
        for (var i : ans) {
            System.out.println(i);
        }
    }
}