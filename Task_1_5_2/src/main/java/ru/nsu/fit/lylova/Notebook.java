package ru.nsu.fit.lylova;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Notebook {
    List<NotebookRecord> records;

    public Notebook() {
        records = new ArrayList<>();
    }

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

    public String getDataInJson() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type itemsListType = new TypeToken<ArrayList<NotebookRecord>>() {
        }.getType();
        return gson.toJson(records, itemsListType);
    }

    public void addRecord(NotebookRecord record) {
        records.add(record);
    }

    public String showAll() {
        records.sort(new NotebookRecord.SortByTimeOfCreation());
        StringBuilder res = new StringBuilder();
        for (var record : records) {
            res.append(record.toString());
            res.append('\n');
        }
        return res.toString();
    }

    public String showAllWithFilter(Date startDate, Date endDate, String[] keywords) {
        records.sort(new NotebookRecord.SortByTimeOfCreation());
        StringBuilder res = new StringBuilder();
        for (var record : records) {
            boolean containKeyword = false;
            for (var keyword : keywords) {
                containKeyword |= record.getTitle().toLowerCase().contains(keyword.toLowerCase());
            }

            if (containKeyword
                    && record.getDate_of_creation().compareTo(startDate) >= 0
                    && record.getDate_of_creation().compareTo(endDate) <= 0) {
                res.append(record.toString());
                res.append('\n');
            }
        }
        return res.toString();
    }

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
