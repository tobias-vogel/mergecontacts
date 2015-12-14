require_relative "../src/CardDavContact.class.rb"
require_relative "../src/Normalizer.class.rb"
require_relative "Test.module.rb"

class CardDavContactTest
  include Test

  def run()
    testCreateCardDavContact()
    #testNormalizePhoneNumber()
  end

  def testCreateCardDavContact()
    contact = CardDavContact.new()
    assertEquals(contact.zip, nil)

    contact = CardDavContact.new({:mobilephone => "0049123456"})
    assertEquals(contact.mobilephone, "0049123456")
    assertEquals(contact.zip, nil)

    begin
      contact = CardDavContact.new({:abcxyz => "does not exist"})
      fail()
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
    assertEquals(contact.allAttributesSet?, true)
    end

  def testNormalizePhoneNumber()
    # do nothing
    test(
      CardDavContact.new({:mobilephone => "0049123456"}),
      CardDavContact.new({:mobilephone => "0049123456"})
    )

    # prepend international pre-dialing
    test(
      CardDavContact.new({:mobilephone => "0123456"}),
      CardDavContact.new({:mobilephone => "0049123456"})
    )

    # remove special characters
    test(
      CardDavContact.new({:mobilephone => "(012) 34-56"}),
      CardDavContact.new({:mobilephone => "0049123456"})
    )

    # remove + in pre-dialing
    test(
      CardDavContact.new({:mobilephone => "+49 123 456"}),
      CardDavContact.new({:mobilephone => "0049123456"})
    )
  end

  def test(original, expectedResult)
    actualResult = Normalizer.normalize(original)
    assertEquals(actualResult, expectedResult)

    # test idempotence, too
    repeatedResult = Normalizer.normalize(actualResult)
    assertEquals(repeatedResult, expectedResult)
  end
end