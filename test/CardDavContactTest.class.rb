require_relative "../src/CardDavContact.class.rb"
require_relative "Test.module.rb"

class CardDavContactTest
  include Test

  def run()
    #TODO implement
    testNormalizePhoneNumber()
  end

  def testNormalizePhoneNumber()
    x = CardDavContact.new({:mobilephone => "22"})
  end  
end

=begin
# test normalize phone numbers
contact = CardDavContact.new
contact.mobilephone = "0049123"


puts x
exit
assertEquals(Normalizer.normalize(CardDavContact.new.mobilephone("0049123456")).mobilephone, "0049123456")
assertEquals(Normalizer.normalize(CardDavContact.new.mobilephone("0123456")).mobilephone, "0049123456")
assertEquals(Normalizer.normalize(CardDavContact.new.mobilephone("(012) 34-56")).mobilephone, "0049123456")


puts "tests ok"

=begin
things to test:
carddavcontact constructor with no params, with reasonable (incomplete) params, with reasonable complete params, with non-existing params, with double params (does it overwrite something?)
=end