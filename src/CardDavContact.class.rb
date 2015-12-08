class CardDavContact
  attr_accessor :givenname
  attr_accessor :familyname
  # ...

  def initialize hash
    @givenname = hash["givenname"]
    @familyname = hash["familyname"]
  end
end