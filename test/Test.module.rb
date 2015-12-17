module Test
  def assertEquals(message = "", actual, expected)
    if !actual.eql?(expected)
      printMessageAndExit(message + "\nExpected '#{expected.inspect}', but was actually '#{actual.inspect}'.")
    end
  end

  def fail(message)
    printMessageAndExit(message)
  end

  private
  def printMessageAndExit(message)
    puts("Error during test: #{message}")
    exit(1)
  end
end