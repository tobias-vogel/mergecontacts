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