module Test
  def assertEquals(actual, expected)
    if actual != expected
      printMessageAndExit()
    end
  end

  def fail
    printMessageAndExit()
  end

  private
  def printMessageAndExit()
    puts("error during test")
    exit(1)
  end
end