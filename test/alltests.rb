require_relative "../src/CardDavContact.class.rb"
require_relative "../src/Normalizer.class.rb"

def assertEquals(actual, expected)
  if actual != expected
    raise "error during test"
  end
end

# test normalize phone numbers
contact = CardDavContact.new
contact.mobilephone = "0049123"
assertEquals(Normalizer.normalize(contact).mobilephone, "0049123")

puts "tests ok"