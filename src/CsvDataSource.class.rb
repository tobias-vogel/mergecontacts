class CsvDataSource < TabularDataSource
  include Normalizer

  def initialize filename
    super ",", filename
  end
end