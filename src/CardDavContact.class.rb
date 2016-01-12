class CardDavContact
  @@attributes = [
    :givenName,
    :familyName,
    :nickname,
    :mail,
    :workphone,
    :homephone,
    :fax,
    :pager,
    :mobilephone,
    :street,
    :state,
    :zip,
    :country,
    :title,
    :organizationalUnit,
    :organization,
    :year,
    :month,
    :day,
    :notes
  ]

  attr_accessor *@@attributes

  def initialize(constructorAttributes = {})
    constructorAttributes.each_pair do |constructorAttribute, constructorAttributeValue|
      if @@attributes.member?(constructorAttribute)
        instance_variable_set("@#{constructorAttribute}", constructorAttributeValue)
        constructorAttributes.delete(constructorAttribute)
      else
        raise "The attribute #{key} does not exist."
      end
    end

    # initialize undefined attributes
    # maybe it better should stay nil...?
    #@@attributes.each do |attribute|
    #  if instance_variable_get("@#{attribute}").nil?()
    #    instance_variable_set("@#{attribute}", "")
    #  end
    #end

    #TODO do something with the remaining attributes in the constructor hash (append them to notes or so)
  end

  def clone()
    clonedAttributes = {}
    @@attributes.each do |attribute|
      clonedAttributes[attribute] = instance_variable_get("@#{attribute}")
    end

    return CardDavContact.new(clonedAttributes)   
  end
  
  def allAttributesSet?()
    @@attributes.each() do |attribute|
      value = instance_variable_get("@#{attribute}")
      if value.nil?
        return false
      end
    end
    return true
  end

  def eql?(other)
    @@attributes.each() do |attribute|
      thisValue = instance_variable_get("@#{attribute}")
      otherValue = other.instance_variable_get("@#{attribute}")
      return false unless thisValue.eql?(otherValue)
    end
    return true
  end

  def mergeInOtherContact(other)
    mergeNames(other)
    mergeEMails(other)
    #todo implement for other attributes
    # go over all attributes, but respect that phone numbers can be mixed around several fields
      # @@attributes.each() do |attribute| ...
  end

  private
  def mergeNames(other)
    if @givenName.nil?()
      @givenName = other.givenName
    elsif @givenName[other.givenName].nil?()
      @givenName << " " + other.givenName
    end

    if @familyName.nil?()
      @familyName = other.familyName
    elsif @familyName[other.familyName].nil?()
      @givenName << " " + other.givenName
    end

    
  end

  def mergeEMails(other)
    # todo
  end
end