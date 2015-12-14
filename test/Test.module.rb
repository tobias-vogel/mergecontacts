module Test
  def assertEquals(actual, expected)
    if actual != expected
      raise "error during test"
   end
  end
end