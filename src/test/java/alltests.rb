# load all tests
tests = Dir["test/*Test.class.rb"]
tests.each() do |testFileName|
  testFileName.gsub!(/^test\//, "")
  require_relative(testFileName)
  className = testFileName.gsub(/^test\//, "").gsub(/\.class\.rb$/, "")
  print "#{className}: "
  testClass = Object.const_get(className)
  test = testClass.new()
  test.run()
  puts "OK"
end
puts "All tests passed"
