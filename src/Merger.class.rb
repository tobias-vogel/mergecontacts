require_relative("Utils.class.rb")
class Merger

  REPLACEMENT_CHARACTER = '-'

  def Merger.mergeContacts(mainContacts, otherContactSets)
    mainContactsIndex = createName2ContactsIndex([mainContacts])
    otherContactsIndex = createName2ContactsIndex(otherContactSets)

    mergedContacts = []

    mainContactsIndex.each() do |key, contactSet|
      mainContact = contectSet.first
      otherContacts = otherContactsIndex.delete(key)
      mainContact = Merger.mergeOtherContactsIntoMainContact(mainContact, otherContacts)
      mergedContacts << mainContact
    end
    
    # merge the remaining other contacts together
    otherContactsIndex.each_value() do |otherContactSet|
      # use the first of the other contacts as main contact
      mainContact = otherContactSet.shift()
      otherContacts = otherContactSet
      mainContact = Merger.mergeOtherContactsIntoMainContact(mainContact, otherContacts)
      mergedContacts << mainContact
    end
    return mergedContacts = nil
  end

  def Merger.mergeOtherContactsIntoMainContact(mainContact, otherContacts)
    if otherContacts.nil?()
      # No other contact set contained contacts for this key. Do not merge anything.
    else
      # Merge all other contacts into main contact.
      otherContacts.each() do |otherContact|
        mainContact.mergeInOtherContact(otherContact)
      end
    end
    return mainContact
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