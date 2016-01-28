class Normalizer
  @@configuration = nil
  
  def Normalizer.loadDefaults()
    #TODO read config.ini into configHash
    @@configuration = {}
    @@configuration["defaultCountryCode"] = "0049"
    @@configuration["defaultCountry"] = "Deutschland"
  end

  def Normalizer.normalize(contact)
    if @@configuration.nil?
      loadDefaults()
    end

    normalizeEncoding(contact)
    normalizePhoneNumbers(contact)
    normalizeEmail(contact)
    normalizeCountry(contact)
    normalizeNames(contact)

    return contact
  end
  
  private
  def Normalizer.normalizeEncoding(contact)
    #TODO for each field: convert to utf-8, maybe guess (different) encodings
  end

  def Normalizer.normalizePhoneNumbers(contact)
    normalizePhoneNumber(contact.workphone)
    normalizePhoneNumber(contact.homephone)
    normalizePhoneNumber(contact.fax)
    normalizePhoneNumber(contact.pager)
    normalizePhoneNumber(contact.mobilephone)    
  end
  
  def Normalizer.normalizePhoneNumber(phoneNumber)
    return if phoneNumber.nil?()

    phoneNumber.delete!("(")
    phoneNumber.delete!(")")
    phoneNumber.delete!("-")
    phoneNumber.delete!(" ")
    
    # +49...
    # add default country code unless a country code exists already
    hasPlusCountryCode = phoneNumber[/^\+/] != nil
    has00CountryCode = phoneNumber[/^00/] != nil
    if hasPlusCountryCode
      phoneNumber.gsub!(/^\+/, "00")
    elsif has00CountryCode
      # do nothing
    else
      # add 00 country code
      #TODO read out country code from country
      phoneNumber.gsub!(/^0/, @@configuration["defaultCountryCode"])
      # don't touch numbers that do not start with a 0
    end
  end

  def Normalizer.normalizeEmail(contact)
    contact.mail.downcase!() unless contact.mail.nil?()
  end

  def Normalizer.normalizeCountry(contact)
    if contact.country.to_s().empty?()
      contact.country = @@configuration["defaultCountry"] 
    end
  end

  def Normalizer.normalizeNames(contact)
    [contact.givenName, contact.familyName, contact.nickname].each() do |name|
      if !name.nil?()
        parts = name.split(" ")
        name = removeSubstringNames(parts)
      end
    end
  end

  def Normalizer.removeSubstringNames(names)
    # iterate over each name
    # if it is a substring of another, remove it
    # if another name is a substring of the current name, remove the other name

    survivingNames = []

    until names.empty?() do
      current = names.shift()

      # remove all names that are substrings of the current one
      names.delete_if() {|name| isSubname(name, current)}

      # keep the current unless it is a substring of the remaining names
      matches = names.collect() {|name| isSubname(current, name)}
      if !matches.member?(true)
        survivingNames << current
      end
    end

    return survivingNames
  end
end