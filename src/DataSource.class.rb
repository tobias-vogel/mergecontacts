class DataSource
#  private_class_method :new #avoid construction
  def initialize()
    raise "DataSource cannot be instantiiated directly."
  end

  def mergeIn(otherSource)
    # todo
  end
end