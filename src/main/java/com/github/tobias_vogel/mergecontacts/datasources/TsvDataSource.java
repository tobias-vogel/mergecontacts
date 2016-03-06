package com.github.tobias_vogel.mergecontacts.datasources;

public class TsvDataSource extends TabularDataSource {

    @Override
    public String getPrefix() {
        return "TSV";
    }





    @Override
    public char getFieldSeparator() {
        return '\t';
    }





    public TsvDataSource() {
        // required default constructor for getPrefix() method
    }
}