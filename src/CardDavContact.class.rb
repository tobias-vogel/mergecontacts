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

    normalize()
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
  def mergeNamesSmart(main, other)
    # tokenize both names by blanks
    # do a nested loop and delete all names that have longer counterparts in the other contact
    # join both lists back to a string

    # tokenize both names
    mainNames = other.to_s().split(" ")
    otherNames = other.to_s().split(" ")

    # merge all names
    names = mainNames + otherNames

    # remove exact duplicates
    names.uniq!()

    # remove all short names that exist in a longer form (that is, are part of another name)
    names = removeSubstringNames(names)

    # append all remaining parts
    main = names.join(" ")

    return main
  end

  def normalize()
    Normalizer.normalize(self)
  end

  def removeSubstringNames(names)
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

  def isSubname(subname, name)
    match = name[subname]
    return match.nil?()
  end

  def mergeNames(other)
puts @givenName
puts other.givenName
    if @givenName.nil?()
      @givenName = other.givenName
    elsif @givenName[other.givenName].nil?()
      @givenName << " " + other.givenName
    end

   @givenName = mergeNamesSmart(@givenName, other.givenName)
# das soll er machen:
# die namen des zweiten tokenisieren (an leerzeichen) und die neuen teile an den hauptnamen anhängen

   # if @familyName.nil?()
   #   @familyName = other.familyName
   # elsif @familyName[other.familyName].nil?()
   #   @givenName << " " + other.givenName
   # end

    
  end

  def mergeEMails(other)
    # todo
  end
end