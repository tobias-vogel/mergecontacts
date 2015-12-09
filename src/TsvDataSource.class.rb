require_relative "TabularDataSource.class.rb"

class TsvDataSource < TabularDataSource

  def initialize(filename)
    super("\t", filename)
  end
end