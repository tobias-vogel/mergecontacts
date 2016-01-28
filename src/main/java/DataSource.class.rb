class DataSource
#  private_class_method :new #avoid construction
  def initialize()
    raise "DataSource cannot be instantiiated directly."
  end

  def normalizeContacts()
    #TODO invoke a chain of normalizers (always on the whole contact)
    @contacts.each do |contact|
      Normalizer.normalize(contact)
    end
  end

  def mergeIn(otherSource)
    # todo
  end
end