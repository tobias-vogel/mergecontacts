require_relative "DataSource.class.rb"

class TabularDataSource < DataSource
  @contacts = nil

  # todo this class should not be directly instantiable
  
  private
  def initialize(separator, filename)
    content = loadFile(filename)
    header = splitHeaderFromContent(content)
    fieldCount = calculateFieldcount(separator, header)

    data = removeSuperfluousLineBreaksFromTabularFile(separator, fieldCount, content)

    @contacts = parseIntoContacts(data)
  end

  def loadFile(filename)
    file = File.open(filename)
    # todo continue
  end
    
  
  
  def calculateFieldCount filename, separator
   CSV.foreach(filename) do |row|
      fieldCount = row.size
    break
    end
  end
  
  def parseIntoContacts data
    header = csv.getHeader
    CSV.foreach(filename) do |row|
      contact = parseRow row, header
      contacts.add contact
    end
  end
end