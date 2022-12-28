package ru.nsu.fit.lylova;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Class of notebook record.
 */
public class NotebookRecord {
    private final String title;
    private final String content;
    private final Date dateOfCreation;

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
        dateOfCreation = new Date();
    }

    /**
     * Returns string with record's data in readable format.
     *
     * @return string with record's data
     */
    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return "Заголовок заметки: "
                + new String(title.getBytes(StandardCharsets.UTF_8))
                + "\nВремя создания: "
                + dateFormat.format(dateOfCreation)
                + "\nСодержание заметки:\n"
                + new String(content.getBytes(StandardCharsets.UTF_8))
                + "\n";
    }

    /**
     * Returns title of this record.
     *
     * @return title of record
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns content of this record.
     *
     * @return content of record
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns date of creation of this record.
     *
     * @return date of creation of record
     */
    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    /**
     * Comparator class of notebook records, that compares records by date of creation.
     */
    public static class SortByTimeOfCreation implements Comparator<NotebookRecord> {

        @Override
        public int compare(NotebookRecord o1, NotebookRecord o2) {
            return o1.dateOfCreation.compareTo(o2.dateOfCreation);
        }
    }
}
