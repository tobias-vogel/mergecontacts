package com.github.tobias_vogel.mergecontacts.datasources;

public class TsvDataSource extends TabularDataSource {

    @Override
    public String getPrefix() {
        return "TSV";
    }





    public TsvDataSource() {
        // required default constructor for getPrefix() method
    }

    // def initialize(filename)
    // super("\t", filename)
    // end
}