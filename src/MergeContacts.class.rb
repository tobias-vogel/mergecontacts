require_relative "TsvDataSource.class.rb"

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
    loadAndClenseContactsFromFiles()
    mergeContacts() 
    exportContactsToTsv()
    puts "Finished."
  end

  private

  def loadAndClenseContactsFromFiles()
    @contacts = []
    @filenames.each() do |fileSpecifier|
      prefix, filename = detectType(fileSpecifier)
      @contacts << [loadFileAndClenseContacts(prefix, filename)]
    end
  end

  def detectType(fileSpecifier)
    prefix, filename = fileSpecifier.split(":", 2)
    return prefix, filename
  end

  def loadFileAndClenseContacts(prefix, filename)
    contacts = case prefix
      when "csv" then CsvDataSource.new(filename).loadAndClenseContacts()
      when "tsv" then TsvDataSource.new(filename).loadAndClenseContacts()
      when "vcard" then VcardDataSource.new(filename).loadAndClenseContacts()
      else raise "Unknown prefix #{prefix}. Exiting."
    end
    return contacts
  end

  def mergeContacts()
    # todo (something with hashmaps where the person names are the keys
  end
  
  def exportContactsToTsv()
    # todo
  end
end