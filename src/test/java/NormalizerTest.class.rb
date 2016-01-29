require_relative "../src/CardDavContact.class.rb"
require_relative "../src/Normalizer.class.rb"
require_relative "Test.module.rb"

class NormalizerTest
  include Test

  def run()
    # todo implement own tests, usually invoked by calling CardDavContact.new

    testNormalizeNames()

    #testCreateCardDavContact()
    #testClone()
    #testNormalizeCountry()
    #testNormalizeEMailAddress()
    #testNormalizePhoneNumber()
  end

  def testNormalizeNames()
    contact = CardDavContact.new({:givenName => "Anna Annalena"})
    assertEquals("Given name should remove the shorter name.", contact.givenName, "Annalena")
  end

  def testCreateCardDavContact()
    contact = CardDavContact.new()
    assertEquals("zip code is filled", contact.zip, nil)

    contact = CardDavContact.new({:mobilephone => "0049123456"})
    assertEquals("mobilephone not set properly", contact.mobilephone, "0049123456")
    assertEquals("zip code is filled", contact.zip, nil)

    begin
      contact = CardDavContact.new({:abcxyz => "does not exist"})
      fail("An unknown key could be added.")
    rescue NameError
      # expected
    end

    # not required due to a warning when including this file
    # contact = CardDavContact.new({:zip => "b", :zip => "a"})
    # assertEquals(contact.zip, "a")

    contact = CardDavContact.new({
      :givenName => "gn",
      :familyName => "fn",
      :nickname => "nn",
      :mail => "ml",
      :workphone => "wp",
      :homephone => "hp",
      :fax => "f",
      :pager => "p",
      :mobilephone => "mp",
      :street => "st",
      :state => "se",
      :zip => "z",
      :country => "c",
      :title => "t",
      :organizationalUnit => "oa",
      :organization => "o",
      :year => "y",
      :month => "m",
      :day => "d",
      :notes => "n"
    })
    assertEquals("Not all attributes are set.", contact.allAttributesSet?, true)
    end

  
  def testNormalizeEMailAddress()
    test(
      "E-mail normalization did not work.",
      CardDavContact.new({:mail => "Ab.Cd@mail.com"}),
      CardDavContact.new({:mail => "ab.cd@mail.com", :country => "Deutschland"})
    )
  end

  def testNormalizeCountry()
    test(
      "Country normalization from nil did not work.",
      CardDavContact.new({:country => nil}),
      CardDavContact.new({:country => "Deutschland"})
    )

    test(
      "Country normalization from """" did not work.",
      CardDavContact.new({:country => ""}),
      CardDavContact.new({:country => "Deutschland"})
    )
  end

  def testClone()
    original = CardDavContact.new({:givenName => "Hans"})
    clone = original.clone()
    assertEquals("Clone should have the same name.", clone.givenName, "Hans")

    original.givenName = "Franz"
    assertEquals("Original should have changed.", original.givenName, "Franz")
    assertEquals("Clone should have its own objects.", clone.givenName, "Hans")
  end

  private
  def test(message = "", original, expectedResult)
    actualResult = Normalizer.normalize(original)
    assertEquals(message, actualResult, expectedResult)

    # test idempotence, too
    repeatedResult = Normalizer.normalize(actualResult)
    assertEquals(message, repeatedResult, expectedResult)
  end
end