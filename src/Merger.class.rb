require_relative("Utils.class.rb")
class Merger

  REPLACEMENT_CHARACTER = '-'

  def Merger.mergeContacts(contactSets)
    index = createName2ContactsIndex(contactSets)
#    mergedContacts = index.map() |key, contacts| do
#      mergeMatchingContacts(contacts)
#    end
    return mergedContacts = nil
  end

  def Merger.createName2ContactsIndex(contactSets)
    index = {}
    contactSets.each() do |contactSet|
      contactSet.each() do |contact|
        key = Merger.calculateHashKeyForContact(contact)
        index = Utils.failsafeArrayHashAppend(index, key, contact)
      end
    end
    return index
  end

  def Merger.calculateHashKeyForContact(contact)
    # We need a simple key that does not create false positives and false negatives.
    # Thus, the key may neither be too relaxed (only country[0]) nor too strict (all attributes).
    # We assume the data to be relatively clean and to contain mainly person data.
    # Therefore, we take the first two characters from the given, family name and the city, hoping that all attributes are usually set correctly and that this is unique enough.
    key = ""
    key += Merger.generateKeyPart(contact.givenName)
    key += Merger.generateKeyPart(contact.familyName)
    #TODO add city
#    city = contact.city
    return key
  end
  
  def Merger.generateKeyPart(attributeValue)
    result = attributeValue
    if result.nil?()
      result = ""
    end
    # append the replacement character in case that the string is too short
    2.times do
      result += REPLACEMENT_CHARACTER
    end
    result = result[0..1]
    return result
  end
end