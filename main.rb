#!/bin/ruby
require_relative "src/MergeContacts.class.rb"
mc = MergeContacts.new ARGV
mc.run

# call main.rb abc.tsv tsv:blabla.tsv csv:blabla.csv vcard:contacts.vcard