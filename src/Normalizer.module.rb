module Normalizer
  def Normalizer.removeSuperfluousLineBreaksFromTabularFile fieldSeparator, expectedFieldCount, data
    # todo: for each line, count the number of separators, if there are too few, these values belong to the line before. then remove this line break
	#line.gsub! /\n/ " "
  end
end