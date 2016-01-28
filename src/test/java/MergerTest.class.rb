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
  
  def test(mainContact, otherContact, expectedResult, message)
puts
puts mainContact.inspect
puts otherContact.inspect
    backup = otherContact.clone()
    mainContact.mergeInOtherContact(otherContact)
    assertEquals(message, mainContact, expectedResult)

    # idempotence
    mainContact.mergeInOtherContact(otherContact)
    assertEquals("Idempotence: " + message, mainContact, expectedResult)
    assertEquals("Other contact should not be modified.", otherContact, backup)
  end

  def testMerge()
    testMergeNames()
    testMergeEMails()
    testMergePhoneNumbers()
    testMergeNotes()
  end

  def testMergeNames()
    test(
      CardDavContact.new({:givenName => "Hans"}),
      CardDavContact.new({:givenName => "Franz"}),
      CardDavContact.new({:givenName => "Hans Franz"}),
      "Names should be appended."
    )

    test(
      CardDavContact.new({:givenName => "Hans"}),
      CardDavContact.new({:givenName => "Hans"}),
      CardDavContact.new({:givenName => "Hans"}),
      "Names should not be changed if they match."
    )

    test(
      CardDavContact.new({:givenName => "Hans"}),
      CardDavContact.new({:givenName => "Hans-Peter"}),
      CardDavContact.new({:givenName => "Hans-Peter"}),
      "The longer name should prevail."
    )

    test(
      CardDavContact.new({:givenName => "Hans Hugo"}),
      CardDavContact.new({:givenName => "Hans Thomas"}),
      CardDavContact.new({:givenName => "Hans Hugo Thomas"}),
      "Concatenate all the pieces."
    )

    test(
      CardDavContact.new({:givenName => "Hans Peter"}),
      CardDavContact.new({:givenName => "Hans-Peter"}),
      CardDavContact.new({:givenName => "Hans-Peter"}),
      "Take the longer name."
    )

     test(
      CardDavContact.new({:givenName => "Anna Annalena"}),
      CardDavContact.new({:givenName => "Petra"}),
      CardDavContact.new({:givenName => "Annalena Petra"}),
      "Remove short names within the same contact."
    )

     test(
      CardDavContact.new({:givenName => "Anna Annalena"}),
      CardDavContact.new(),
      CardDavContact.new({:givenName => "Annalena"}),
      "Merging should normalize substring names."
    )

    test(
      CardDavContact.new({:givenName => "Hans-Franz"}),
      CardDavContact.new({:givenName => "Franz"}),
      CardDavContact.new({:givenName => "Hans-Franz"}),
      "Names should be appended, but not duplicated."
    )

    test(
      CardDavContact.new({:familyName => "Meier"}),
      CardDavContact.new({:familyName => "Meier geb. Schwarzenegger"}),
      CardDavContact.new({:familyName => "Meier geb. Schwarzenegger"}),
      "Common substring names should be replaced by the longest."
    )
  end

  def testMergeEMails()
    test(
      CardDavContact.new({:mail => "hans.meier@gmail.com"}),
      CardDavContact.new({:mail => "hans.meier@hotmail.com"}),
      CardDavContact.new({:mail => "hans.meier@gmail.com", :notes => "other-mail= hans.meier@hotmail.com"}),
      "E-Mails should not be appended but be appended to the notes section."
    )
  end

  def testMergePhoneNumbers()
    test(
      CardDavContact.new({:homephone => "007", :workphone => "123"}),
      CardDavContact.new({:homephone => "123", :workphone => "007"}),
      CardDavContact.new({:homephone => "007", :workphone => "123"}),
      "Phone numbers should not be put into the notes section if they appear somewhere else already (crossed)."
    )

    test(
      CardDavContact.new({:homephone => "007", :notes => "other-phone=123"}),
      CardDavContact.new({:workphone => "123"}),
      CardDavContact.new({:homephone => "007", :notes => "other-phone=123"}),
      "All superfluous phone numbers are put into a common phone field in the notes section (and thus are not duplicated)."
    )
  end

  def testMergeNotes()
    test(
      CardDavContact.new({:homephone => "007", :notes => "other-phone=123"}),
      CardDavContact.new({:workphone => "123", :notes => "other-mail=abc@lmn.xyz"}),
      CardDavContact.new({:homephone => "007", :notes => "other-phone=123, other-mail=abc@lmn.xyz"}),
      "Attributes are not taken (and promoted) from the notes section and notes are properly appended."
    )    
  end
end