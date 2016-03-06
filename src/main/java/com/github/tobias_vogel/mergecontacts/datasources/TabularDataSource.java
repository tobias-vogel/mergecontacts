package com.github.tobias_vogel.mergecontacts.datasources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact;
import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;
import com.github.tobias_vogel.mergecontacts.utils.Configuration;
import com.google.common.base.Splitter;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public abstract class TabularDataSource extends DataSource {

    private List<CardDavContactAttributes> header = new ArrayList<>();

    // private String[][] content;





    @Override
    public Set<CardDavContact> readContacts(String filename) {
        this.filename = filename;
        Configuration.loadDefaultProperties();
        readHeaderFromFile(filename);
        int fieldCount = calculateFieldCount();
        List<List<String>> content = readContentFromFile();
        content = removeSuperfluousLineBreaksFromTabularFile(fieldCount, content);
        Set<CardDavContact> contacts = parseContentIntoContacts(content);
        return contacts;
    }





    private List<List<String>> removeSuperfluousLineBreaksFromTabularFile(int fieldCount, List<List<String>> content) {
        List<List<String>> correctRows = new ArrayList<>();
        int inputRowCount = 0;

        List<String> stagedRow;

        // avoid cold-start by assuming the first non-empty row as staged
        do {
            stagedRow = content.remove(0);
        } while (stagedRow.isEmpty());

        do {
            List<String> currentRow = content.remove(0);
            inputRowCount++;

            boolean stagedRowIsComplete = (stagedRow.size() == fieldCount);
            boolean currentRowIsComplete = (currentRow.size() == fieldCount);

            if (stagedRowIsComplete && currentRowIsComplete) {
                // The current row looks like a complete row. Thus, the staged
                // row has to be finished.
                correctRows = addStagedRowToCorrectRows(stagedRow, correctRows, fieldCount);
                stagedRow = currentRow;
            } else if (!stagedRowIsComplete && currentRowIsComplete) {
                // This situation is not possible under our assumptions. If the
                // current row is complete, the staged row should have only one
                // field (with a line break).
                // However, we assume the first field to be without line breaks,
                // a contradiction.
                throw new RuntimeException("The file \"" + filename
                        + "\" is messed up. It looks like that there are line breaks in the first field somewhere around line "
                        + inputRowCount + ". Please fix this issue manually (or re-arrange the fields).");
            } else if (stagedRowIsComplete && !currentRowIsComplete) {
                // The current row could be the continuation of the staged row's
                // last field (because the staged row is complete).
                // Or the staged row is really finished, but the current row is
                // incomplete.
                if (currentRow.size() == 0) {
                    // This is an empty row. Silently ignore it.
                } else if (currentRow.size() == 1) {
                    // The current row is a continuation of the staged row's
                    // last field.
                    stagedRow = mergeStagedRowAndCurrentRow(stagedRow, currentRow);
                } else {
                    // The current row is a new, unfinished row.
                    correctRows = addStagedRowToCorrectRows(stagedRow, correctRows, fieldCount);
                    stagedRow = currentRow;
                }
            } else { // !stagedRowIsComplete && !currentRowIsComplete
                // There is a line break somewhere in the staged row.
                stagedRow = mergeStagedRowAndCurrentRow(stagedRow, currentRow);
                correctRows = addStagedRowToCorrectRows(stagedRow, correctRows, fieldCount);
            }

            if (content.isEmpty()) {
                // flush the staged row
                correctRows = addStagedRowToCorrectRows(stagedRow, correctRows, fieldCount);
            }
        } while (!content.isEmpty());

        // TODO prevent duplicate correct rows to be inserted
        return correctRows;
    }





    private List<String> mergeStagedRowAndCurrentRow(List<String> stagedRow, List<String> currentRow) {
        String mergedField = stagedRow.remove(stagedRow.size() - 1) + " " + currentRow.remove(0);

        List<String> mergedRow = new ArrayList<>(stagedRow.size() + 1 + currentRow.size());
        mergedRow.addAll(stagedRow);
        mergedRow.add(mergedField);
        mergedRow.addAll(currentRow);

        return mergedRow;
    }





    private List<List<String>> addStagedRowToCorrectRows(List<String> stagedRow, List<List<String>> correctRows,
            int fieldCount) {
        doPlausibilityCheck(stagedRow, fieldCount);
        correctRows.add(stagedRow);
        return correctRows;
    }





    private void doPlausibilityCheck(List<String> rowToAdd, int fieldCount) {
        if (rowToAdd.size() != fieldCount) {
            throw new RuntimeException("The file \"" + filename
                    + "\" is messed up. I was trying to generate a row with a field count of " + rowToAdd.size()
                    + " instead of a maximum of " + fieldCount + ". I don't know what to do.");
        }
    }





    private List<List<String>> readContentFromFile() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));) {
            CSVReader csvReader = new CSVReaderBuilder(br).withCSVParser(new CSVParser(getFieldSeparator()))
                    .withSkipLines(1).build();

            List<List<String>> result = new ArrayList<>();
            // TODO use java8 lambda magic
            for (String[] row : csvReader.readAll()) {
                result.add(new ArrayList<>(Arrays.asList(row)));
            }

            csvReader.close();
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong during reading a datasource.", e);
        }
    }





    private Set<CardDavContact> parseContentIntoContacts(List<List<String>> content) {
        Set<CardDavContact> result = new HashSet<>(content.size());

        for (List<String> row : content) {
            Map<CardDavContactAttributes, String> attributeValueMap = convertRowToAttributeValueMap(row);
            CardDavContact contact = new CardDavContact(attributeValueMap);
            result.add(contact);
        }

        // TODO remove unused csv library? --> is not unused anymore
        return result;
    }





    private Map<CardDavContactAttributes, String> convertRowToAttributeValueMap(List<String> row) {
        // XXX hier wird irgendwie notes auf null gesetzt bzw. gar nicht gesetzt
        Map<CardDavContactAttributes, String> map = new HashMap<>(row.size());

        for (int i = 0; i < row.size() - 1; i++) {
            CardDavContactAttributes attribute = header.get(i);
            String value = row.get(i);

            map.put(attribute, value);
        }

        return map;
    }





    private int calculateFieldCount() {
        return header.size();
    }





    private void readHeaderFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new RuntimeException("The file \"" + filename + "\" seems to be empty.");
            }

            fillHeadersFromHeaderLine(headerLine);

        } catch (IOException e) {
            throw new RuntimeException("Could not load file \"" + filename + "\".");
        }
    }





    private void fillHeadersFromHeaderLine(String headerLine) {
        // CSVParser csvParser = new CSVParser(getFieldSeparator());
        // csvParser.parseLine(headerLine);
        // TODO use csvparser if used anyway
        // TODO use java 8 lambda magic
        for (String headerField : Splitter.on(getFieldSeparator()).split(headerLine)) {
            CardDavContactAttributes attribute = Configuration.getAlias(headerField);
            header.add(attribute);
        }
    }





    public abstract char getFieldSeparator();
}