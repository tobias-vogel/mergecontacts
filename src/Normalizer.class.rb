class Normalizer
  @@configuration = nil
  
  def Normalizer.loadDefaults()
    #TODO read config.ini into configHash
    @@configuration = {}
    @@configuration["defaultCountryCode"] = "0049"
  end

  def Normalizer.normalize(contact)
    if @@configuration.nil?
      loadDefaults()
    end

    #TODO implement
    normalizeEncoding(contact)
    normalizePhoneNumbers(contact)

    return contact
  end
  
  private
  def Normalizer.normalizeEncoding(contact)
    #TODO for each field: convert to utf-8
  end

  def Normalizer.normalizePhoneNumbers(contact)
    normalizePhoneNumber(contact.workphone)
    normalizePhoneNumber(contact.homephone)
    normalizePhoneNumber(contact.fax)
    normalizePhoneNumber(contact.pager)
    normalizePhoneNumber(contact.mobilephone)    
  end
  
  def Normalizer.normalizePhoneNumber(phoneNumber)
    return if phoneNumber.nil?

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
    end
  end
end