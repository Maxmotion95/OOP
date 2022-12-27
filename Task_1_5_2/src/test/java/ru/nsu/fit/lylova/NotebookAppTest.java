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
    void test() throws IOException {
        File source = new File("src/test/resources/data/data1.json");
        File dest = new File("src/test/resources/tmp/data.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/result.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/tmp/data.json"));
        cmd.execute("-show");
        System.out.close();

        byte[] f1 = Files.readAllBytes(new File("src/test/resources/results/test1result.txt").toPath());
        byte[] f2 = Files.readAllBytes(new File("src/test/resources/tmp/result.txt").toPath());
        assertArrayEquals(f1, f2);
    }
}