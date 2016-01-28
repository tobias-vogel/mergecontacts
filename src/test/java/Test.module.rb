module Test
  def assertEquals(message = "", actual, expected)
    if !actual.eql?(expected)
      printMessageAndExit(message + "\nExpected '#{expected.inspect}', but was actually '#{actual.inspect}'.")
    end
  end

  def fail(message)
    printMessageAndExit(message)
  end

  def test1(message = "", original, expectedResult)
    actualResult = yield(original)
    assertEquals(message, actualResult, expectedResult)

    # test idempotence, too
    repeatedResult = yield(actualResult)
    assertEquals(message, repeatedResult, expectedResult)
  end

  private
  def printMessageAndExit(message)
    puts("Error during test: #{message}")
    exit(1)
  end
end