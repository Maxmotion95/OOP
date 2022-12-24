package ru.nsu.fit.lylova;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class Main with method main that implements the logic of the notebook console application.
 */
public class Main {
    /**
     * The main method that implements the logic of the notebook console application.
     * @param args arguments of command line
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid command line arguments");
            return;
        }

        Notebook notebook;

        File dataFile = new File("data.json");
        try {
            notebook = new Notebook(new FileInputStream(dataFile));
        } catch (FileNotFoundException e) {
            notebook = new Notebook();
        }

        switch (args[0]) {
            case "-add":
                if (args.length != 3) {
                    System.out.println("Invalid command line arguments. Correct format: -add title content");
                    return;
                }
                notebook.addRecord(new NotebookRecord(args[1], args[2]));
                break;
            case "-show":
                if (args.length == 1) {
                    System.out.println(notebook.showAll());
                    break;
                }
                if (args.length < 4) {
                    System.out.println("Invalid command line arguments");
                    return;
                }
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                String[] keywords = new String[args.length - 3];
                System.arraycopy(args, 3, keywords, 0, args.length - 3);

                Date startDate;
                try {
                    startDate = dateFormat.parse(args[1]);
                } catch (ParseException e) {
                    System.out.println("Invalid command line arguments. Correct format of startDate: \"dd.MM.yyyy hh:mm\"");
                    return;
                }
                Date endDate;
                try {
                    endDate =  dateFormat.parse(args[2]);
                } catch (ParseException e) {
                    System.out.println("Invalid command line arguments. Correct format of endDate: \"dd.MM.yyyy hh:mm\"");
                    return;
                }
                System.out.println(notebook.showAllWithFilter(startDate, endDate, keywords));
                break;
            case "-rm":
                if (args.length != 2) {
                    System.out.println("Invalid command line arguments. Correct format: -rm title");
                    return;
                }
                notebook.removeRecordsWithTitle(args[1]);
                break;
            default:
                System.out.println("Invalid command line arguments.");
                break;
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream("data.json");
        } catch (FileNotFoundException e) {
            System.out.println("Loading changes broke :(((((((");
            return;
        }
        byte[] strToBytes = notebook.getDataInJson().getBytes(StandardCharsets.UTF_8);
        try {
            outputStream.write(strToBytes);
        } catch (IOException e) {
            System.out.println("Loading changes broke :(((((((");
            return;
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            System.out.println("Loading changes broke :(((((((");
        }
    }
}