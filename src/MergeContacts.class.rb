require_relative("TsvDataSource.class.rb")

class MergeContacts

  @filenames

  def initialize(filenames)
    @targetFilename = filenames.shift()
    if @targetFilename.nil?
      raise "No target file was provided. Exiting"
    elsif File.exists?(@targetFilename)
      raise "Target file #{@targetFilename} exists already. Exiting."
    end
    @filenames = filenames
  end

  def run
    #TODO for conflict resolution during merging, it is good to define a main contact file and merge in all other information
    loadAndClenseContactsFromFiles()
    coordinateMerge()
    exportContactsToTsv()
    puts "Finished."
  end

  private
  def loadAndClenseContactsFromFiles()
    mainContactFileSpecifier = @fileSpecifiers.shift()
    @mainContacts = loadFileAndCleanseContacts(mainContactFileSpecifier)

    @otherContactSets = []
    @fileSpecifiers.each() do |fileSpecifier|
      @otherContactSets << [loadFileAndCleanseContacts(fileSpecifier)]
    end
  end

  def loadFileAndClenseContacts(fileSpecifier)
    prefix, filename = detectType(fileSpecifier)

    contacts = case prefix
      when "csv" then CsvDataSource.new(filename).loadAndClenseContacts()
      when "tsv" then TsvDataSource.new(filename).loadAndClenseContacts()
      when "vcard" then VcardDataSource.new(filename).loadAndClenseContacts()
      else raise "Unknown prefix #{prefix}. Exiting."
    end
    return contacts
  end

  def detectType(fileSpecifier)
    prefix, filename = fileSpecifier.split(":", 2)
    return prefix, filename
  end

  def mergeContacts()
    @mainContacts = Merger.coordinateMerge(@mainContacts, @otherContactSets)
  end

  def exportContactsToTsv()
    # todo
  end
end