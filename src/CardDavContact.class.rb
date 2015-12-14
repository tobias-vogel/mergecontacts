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

    #TODO do something with the remaining attributes in the constructor hash (append them to notes or so)
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
end