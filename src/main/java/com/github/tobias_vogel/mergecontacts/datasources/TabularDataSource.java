package com.github.tobias_vogel.mergecontacts.datasources;
public abstract class TabularDataSource extends DataSource {
    //
    // def loadAndClenseContacts()
    // content = loadFile(@filename)
    // headerLine, content = splitHeaderLineFromContent(content)
    // fieldCount = calculateFieldCount(@separator, headerLine)
    // content = removeSuperfluousLineBreaksFromTabularFile(@separator,
    // fieldCount, content)
    // @contacts = parseIntoContacts(content)
    // @contacts = normalizeContacts()
    // end
    //
    // #TODO ensure that this class should not be directly instantiable
    //
    // private
    // def initialize(separator, filename)
    // @separator = separator
    // @filename = filename
    // end
    //
    // def loadFile(filename)
    // content = File.read(filename)
    // return content
    // end
    //
    // def splitHeaderLineFromContent(content)
    // # We assume that the first line is the header line and that it has no
    // line breaks.
    // headerLine, content = content.split("\n", 2)
    // return headerLine, content
    // end
    //
    // def calculateFieldCount(separator, headerLine)
    // fieldCount = headerLine.split(separator).size
    // return fieldCount
    // end
    //
    // def removeSuperfluousLineBreaksFromTabularFile(separator, fieldCount,
    // content)
    // #TODO
    // end
    //
    // def parseIntoContacts(content)
    // contacts = []
    // # CSV.foreach(filename) do |row|
    // # contact = parseRow row, header
    // # contacts.add contact
    // # end
    // return contacts
    // end
    // end
}