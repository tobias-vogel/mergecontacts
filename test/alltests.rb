require_relative "../src/CardDavContact.class.rb"
require_relative "../src/Normalizer.class.rb"

#TODO is it possible to have one test file for each class and call them automatically, just like require "test.*.class.rb", for not to miss any tests
# they could all implement a test interface (or module?) to provide these assert methods

def assertEquals(actual, expected)
  if actual != expected
    raise "error during test"
  end
end

# test normalize phone numbers
contact = CardDavContact.new
contact.mobilephone = "0049123"

x = CardDavContact.new({:mobilephone => "22"})

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