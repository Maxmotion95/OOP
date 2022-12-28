package ru.nsu.fit.lylova;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Notebook class.
 */
public class Notebook {
    private final List<NotebookRecord> records;

    /**
     * Constructs an empty notebook.
     */
    public Notebook() {
        records = new ArrayList<>();
    }

    /**
     * Constructs notebook with data from {@code inputStream}.
     *
     * @param inputStream stream that contains notebook data in json format
     */
    public Notebook(InputStream inputStream) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Type itemsListType = new TypeToken<ArrayList<NotebookRecord>>() {
        }.getType();
        String data = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining(""));
        records = gson.fromJson(data, itemsListType);
    }

    /**
     * Returns notebook data in json format.
     *
     * @return string with notebook data in json format
     */
    public String getDataInJson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type itemsListType = new TypeToken<ArrayList<NotebookRecord>>() {
        }.getType();
        return gson.toJson(records, itemsListType);
    }

    /**
     * Adds specified record to notebook.
     *
     * @param record record to be added to this notebook
     */
    public void addRecord(NotebookRecord record) {
        records.add(record);
    }

    /**
     * Returns string with all records of notebook.
     *
     * @return string with all records of notebook
     */
    public String showAll() {
        records.sort(new NotebookRecord.SortByTimeOfCreation());
        StringBuilder res = new StringBuilder();
        for (var record : records) {
            res.append(record.toString());
            res.append('\n');
        }
        return res.toString();
    }

    /**
     * Returns string with records of notebook,
     * that created in the interval from {@code startDate} to {@code endDate}
     * and contains any keyword from {@code keywords} in title.
     *
     * @param startDate minimal date of record's creation
     * @param endDate maximal date of record's creation
     * @param keywords array of strings with keywords
     * @return string with filtered records of notebook
     */
    public String showAllWithFilter(Date startDate, Date endDate, String[] keywords) {
        records.sort(new NotebookRecord.SortByTimeOfCreation());
        StringBuilder res = new StringBuilder();
        for (var record : records) {
            boolean containKeyword = false;
            for (var keyword : keywords) {
                containKeyword |= record.getTitle().toLowerCase().contains(keyword.toLowerCase());
            }

            if (containKeyword
                    && record.getDateOfCreation().compareTo(startDate) >= 0
                    && record.getDateOfCreation().compareTo(endDate) <= 0) {
                res.append(record.toString());
                res.append('\n');
            }
        }
        return res.toString();
    }

    /**
     * Remove all records with specified title.
     *
     * @param title title of record to be removed
     */
    public void removeRecordsWithTitle(String title) {
        int i = 0;
        while (i < records.size()) {
            if (records.get(i).getTitle().equals(title)) {
                records.remove(i);
                continue;
            }
            ++i;
        }
    }
}
