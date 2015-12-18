require_relative("../src/CardDavContact.class.rb")
require_relative("../src/Merger.class.rb")
require_relative("Test.module.rb")

class MergerTest
  include Test

  def run()
    testCreateIndex()
    testMerge()
  end
  
  def testCreateIndex()
    contactA1 = CardDavContact.new({:givenName => "Hans", :familyName => "Meier", :mail => "hans.meier@gmail.com", :country => "Deutschland"})
    contactB1 = CardDavContact.new({:givenName => "Hans", :familyName => "Meier", :mail => "hans.meier@hotmail.com", :country => "Deutschland"})
    contactB2 = CardDavContact.new({:givenName => "Franz", :familyName => "Müller", :country => "Deutschland"})

    contacts = [[contactA1], [contactB1, contactB2]]

    index = Merger.createName2ContactsIndex(contacts)

    assertEquals("There should be two index entries.", index.size(), 2)
    contact1Together = false
    contact2Alone = false
    index.each_value() do |matchingContacts|
      if matchingContacts.size() == 2 and matchingContacts.member?(contactA1) and matchingContacts.member?(contactB1)
        contact1Together = true
      elsif matchingContacts.size() == 1 and matchingContacts.member?(contactB2)
        contact2Alone = true
      end
    end
    assertEquals("Hans Meier should have two contacts.", contact1Together, true)
    assertEquals("Franz Müller should have one contact.", contact2Alone, true)
  end
  
  def testMerge()
    #todo
  end
end