package ru.nsu.fit.lylova;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NotebookAppTest {
    @Test
    void testShowAll() throws IOException {
        File source = new File("src/test/resources/data/data1.json");
        File dest = new File("src/test/resources/tmp/data132432.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/result132432.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/tmp/data132432.json"));
        cmd.execute("-show");
        System.out.close();

        byte[] f1 = Files.readAllBytes(new File("src/test/resources/results/testShowAll.txt").toPath());
        byte[] f2 = Files.readAllBytes(new File("src/test/resources/tmp/result132432.txt").toPath());
        assertArrayEquals(f1, f2);
    }

    @Test
    void testShowWithFilter1() throws IOException {
        File source = new File("src/test/resources/data/data2.json");
        File dest = new File("src/test/resources/tmp/data4543534.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/result4543534.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/tmp/data4543534.json"));
        cmd.execute("-show", "01.12.2022 00:00", "31.12.2022 00:00", "title");
        System.out.close();

        // check result of show
        byte[] f1 = Files.readAllBytes(new File("src/test/resources/results/testShowWithFilter1.txt").toPath());
        byte[] f2 = Files.readAllBytes(new File("src/test/resources/tmp/result4543534.txt").toPath());
        assertArrayEquals(f1, f2);

        // check that data don't changed
        f1 = Files.readAllBytes(new File("src/test/resources/data/data2.json").toPath());
        f2 = Files.readAllBytes(new File("src/test/resources/tmp/data4543534.json").toPath());
        assertArrayEquals(f1, f2);
    }

    @Test
    void testShowWithFilter2() throws IOException {
        File source = new File("src/test/resources/data/data2.json");
        File dest = new File("src/test/resources/tmp/data645654.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/result645654.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/tmp/data645654.json"));
        cmd.execute("-show", "02.12.2022 00:00", "31.12.2022 00:00", "title");
        System.out.close();

        // check result of show
        byte[] f1 = Files.readAllBytes(new File("src/test/resources/results/testShowWithFilter2.txt").toPath());
        byte[] f2 = Files.readAllBytes(new File("src/test/resources/tmp/result645654.txt").toPath());
        assertArrayEquals(f1, f2);

        // check that data don't changed
        f1 = Files.readAllBytes(new File("src/test/resources/data/data2.json").toPath());
        f2 = Files.readAllBytes(new File("src/test/resources/tmp/data645654.json").toPath());
        assertArrayEquals(f1, f2);
    }

    @Test
    void testAdd1() throws IOException {
        File source = new File("src/test/resources/data/data3.json");
        File dest = new File("src/test/resources/tmp/data1544853.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/result1544853.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/tmp/data1544853.json"));
        cmd.execute("-add", "Good News", "Manic Drive");
        System.out.close();

        // check result of add
        byte[] f1 = Files.readAllBytes(new File("src/test/resources/results/empty.txt").toPath());
        byte[] f2 = Files.readAllBytes(new File("src/test/resources/tmp/result1544853.txt").toPath());
        assertArrayEquals(f1, f2);

        // check changed data
        f1 = Files.readAllBytes(new File("src/test/resources/resultsData/testAdd1.json").toPath());
        f2 = Files.readAllBytes(new File("src/test/resources/tmp/data1544853.json").toPath());
        int i = 0;
        int j = 0;
        boolean dateReading = false;
        while (i < f1.length && j < f2.length) {
            if (dateReading) {
                if (f2[j] == '\"') {
                    dateReading = false;
                    ++i;
                    continue;
                }
                ++j;
            }else {
                assertEquals(f1[i], f2[j]);
                ++i; ++j;
                if (i < f1.length && f1[i] == '*' && f1[i - 1] == '\"') {
                    dateReading = true;
                }
            }
        }
        assertEquals(f1.length, i);
        assertEquals(f2.length, j);
    }

    @Test
    void testRemove1() throws IOException {
        File source = new File("src/test/resources/data/data4.json");
        File dest = new File("src/test/resources/tmp/data41638332.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/result41638332.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/tmp/data41638332.json"));
        cmd.execute("-rm", "title1");
        System.out.close();

        // check result of add
        byte[] f1 = Files.readAllBytes(new File("src/test/resources/results/empty.txt").toPath());
        byte[] f2 = Files.readAllBytes(new File("src/test/resources/tmp/result41638332.txt").toPath());
        assertArrayEquals(f1, f2);

        // check changed data
        f1 = Files.readAllBytes(new File("src/test/resources/resultsData/testRemove1.json").toPath());
        f2 = Files.readAllBytes(new File("src/test/resources/tmp/data41638332.json").toPath());
        assertArrayEquals(f1, f2);
    }
}