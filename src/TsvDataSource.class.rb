require_relative "TabularDataSource.class.rb"
require_relative "Normalizer.module.rb"

class TsvDataSource < TabularDataSource
  include Normalizer

  def initialize(filename)
    super("\t", filename)
  end
end