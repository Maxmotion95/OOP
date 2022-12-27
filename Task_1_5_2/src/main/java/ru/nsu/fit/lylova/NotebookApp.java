package ru.nsu.fit.lylova;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class application notebook.
 */
@Command(name = "notebook", mixinStandardHelpOptions = true, version = "checksum 0.1",
        description = "A notebook with a set of functions: " +
                "add an entry, delete an entry, " +
                "display all entries sorted by the time of addition.",
        subcommands = {
                NotebookApp.AddCommand.class,
                NotebookApp.RmCommand.class,
                NotebookApp.ShowCommand.class})
class NotebookApp {
    private Notebook notebook;
    private String notebookFilePath;

    /**
     * Constructs application with notebook with data from {@code notebookFilePath}.
     * If such file doesn't exist then notebook is empty.
     *
     * @param notebookFilePath filepath of file with notebook data
     */
    public NotebookApp(String notebookFilePath) {
        this.notebookFilePath = notebookFilePath;
        File dataFile = new File(notebookFilePath);
        try {
            notebook = new Notebook(new FileInputStream(dataFile));
        } catch (FileNotFoundException e) {
            notebook = new Notebook();
        }
    }

    private void loadChanges() {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(notebookFilePath);
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

    /**
     * Class command add record to notebook.
     */
    @Command(name = "-add", mixinStandardHelpOptions = true,
            description = "Add record with specified title and content. \n" +
                    "First parameter is title. Second is content of record.")
    public static class AddCommand implements Runnable {

        @ParentCommand
        NotebookApp parent;

        @Option(names = "-add")
        private boolean add;

        @Parameters(index = "0")
        private String title;
        @Parameters(index = "1")
        private String content;

        /**
         * Adds record to notebook.
         */
        @Override
        public void run() {
            parent.notebook.addRecord(new NotebookRecord(title, content));
            parent.loadChanges();
        }
    }

    /**
     * Class command remove records with some title from notebook.
     */
    @Command(name = "-rm", mixinStandardHelpOptions = true,
            description = "Remove all records with specified title.\n" +
                    "First parameter is title.")
    public static class RmCommand implements Runnable {
        @ParentCommand
        NotebookApp parent;

        @Option(names = "-rm")
        private boolean rm;

        @Parameters(index = "0")
        private String title;

        /**
         * Removes records from notebook.
         */
        @Override
        public void run() {
            parent.notebook.removeRecordsWithTitle(title);
            parent.loadChanges();
        }
    }

    /**
     * Class command show notebook.
     */
    @Command(name = "-show", mixinStandardHelpOptions = true,
            description = "Show all records if no parameters or show filtered records. \n" +
                    "To show filtered records first and second parameters must be dates of format \"dd.MM.yyyy hh:mm\". " +
                    "First is minimal date of creation and second is maximal date of creation. " +
                    "The next parameters will be list of keywords which title of record must contain.")
    public static class ShowCommand implements Runnable {
        @ParentCommand
        NotebookApp parent;

        @Option(names = "-show")
        private boolean show;

        @Parameters
        private String[] params;

        /**
         * Shows notebook according to command parameters.
         */
        @Override
        public void run() {
            if (params == null) {
                System.out.println(parent.notebook.showAll());
                return;
            }

            if (params.length < 2) {
                System.err.println("Invalid command line arguments. First 2 parameters must be starDate and endDate");
                return;
            }

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            String[] keywords = new String[params.length - 2];
            System.arraycopy(params, 2, keywords, 0, params.length - 2);

            Date startDate;
            try {
                startDate = dateFormat.parse(params[0]);
            } catch (ParseException e) {
                System.err.println("Invalid command line arguments. Correct format of startDate: \"dd.MM.yyyy hh:mm\"");
                return;
            }
            Date endDate;
            try {
                endDate = dateFormat.parse(params[1]);
            } catch (ParseException e) {
                System.err.println("Invalid command line arguments. Correct format of endDate: \"dd.MM.yyyy hh:mm\"");
                return;
            }
            System.out.println(parent.notebook.showAllWithFilter(startDate, endDate, keywords));
        }
    }

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;
}
