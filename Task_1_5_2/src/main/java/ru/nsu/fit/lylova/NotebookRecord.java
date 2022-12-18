package ru.nsu.fit.lylova;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class NotebookRecord {
    private final String title;
    private final String content;
    private final Date date_of_creation;

    /**
     * Creates record with specified {@code title} and {@code content}.
     * The time of creation is the time at which the record was allocated.
     *
     * @param title   title of record
     * @param content content of record
     */
    public NotebookRecord(String title, String content) {
        this.title = title;
        this.content = content;
        date_of_creation = new Date();
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return "Заголовок заметки: "
                + new String(title.getBytes(StandardCharsets.UTF_8))
                + "\nВремя создания: "
                + dateFormat.format(date_of_creation)
                + "\nСодержание заметки:\n"
                + new String(content.getBytes(StandardCharsets.UTF_8))
                + "\n";
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate_of_creation() {
        return date_of_creation;
    }

    static class SortByTimeOfCreation implements Comparator<NotebookRecord> {

        @Override
        public int compare(NotebookRecord o1, NotebookRecord o2) {
            return o1.date_of_creation.compareTo(o2.date_of_creation);
        }
    }
}
