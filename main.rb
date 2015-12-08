require_relative "src/MergeContacts.class.rb"
mc = MergeContacts.new ARGV
mc.run

# call it like so (replace ruby with your preferred version that is sufficiently recent):
# ruby main.rb abc.tsv tsv:blabla.tsv csv:blabla.csv vcard:contacts.vcard
