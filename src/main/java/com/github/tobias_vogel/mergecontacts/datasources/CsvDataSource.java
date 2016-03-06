package com.github.tobias_vogel.mergecontacts.datasources;

public class CsvDataSource extends TabularDataSource {

    @Override
    public String getPrefix() {
        return "CSV";
    }





    public CsvDataSource() {
        // required default constructor for getPrefix() method
    }





    @Override
    public char getFieldSeparator() {
        return ',';
    }

    // def initialize filename
    // super ",", filename
    // end
}