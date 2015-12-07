# mergecontacts
A ruby tool to merge different datasources with contacts.

## Goal
The goal is to integrate different address book data sources from (partly out-dated) address book exports.
This integration shall be idempotent and the target format shall be compatible with the CardDAV standard.

This compatibility can be checked by doing a CSV and a VCARD export. The CSV file should not contain any line breaks other than to separate records from each other (especially not in the notes section). The VCARD file should not have additional, proprietary fields. For example, MS Outlook supports 3 sets of postal addresses, but CardDAV supports only 2 (private and business). In VCARD exports from MS Outlook, these additional postal addresses have a kind of X-Header prefix.

The following features/precautions shall be accomplished:
- Phone numbers shall be formatted uniformly (0049…)
- The default encoding should be UTF-8. The datasources may have other formats. A quick-and-dirty replacement of special characters (e.g., umlauts) can be done with a simple gsub.
- Each field just contains a single value, especially phone numbers do not contain line breaks. The only exception is the notes section (see below), which still must not contain line breaks.
- Each real person has a Herr/Frau in front (if supported by CardDAV).
- Old data (phone numbers, e-mail addresses, etc.) are kept in a special place in the notes section. This way, the merge does not fill old data back into the already cleansed records (to promote idempotence).
- Matching records should be done using the person names. People keep their maiden names in parantheses, so that the match should still work.
- A match shall compare the (then normalized) phone numbers and check, whether they exist in any other field, not doing anything with them. All other (new/unknown) information should be taken to the existing record.

## Workflow
The workflow is as follows:

The base dataset is the CSV export of the current address book. It can be enriched with other datasets, that might contain older, but maybe also to some extend, newer data. All data going through the workflow undergoes the mentionned normalization. Thus, integration can be done over and over again for the same data, not messing up the base dataset.

## Usage
Put all the data files under …/data/. Invoke main.rb with the files to merge. The first file is the file to create and will be saved as TSV. If it exists already, the program stops here. All following files (at least one is required) specify the files to merge together. Proceed each file with one of the following prefixed to specify its type: "tsv:", "csv:", or "vcard:".

For example:

    main.rb data/result.txt csv:data/flatfile1.csv tsv:data/othercontact-export.tsv vcard:data/outlook.export


## Why?
I ended up writing this tool because I struggled with problems of having different data silos that turned into one-way-streets.
In our university, we were provided with a MS Exchange account. I used the address book of this account to store my contacts. Everything synced relatively nice. I used it on an iPad, on two MS Outlook installations, on my Android smartphone, and sometimes also Outlook Web Access. Then, I lost access to the Exchange server. The datasets were not syncing anymore and I were not aware that this was a problem for some time. Finally, I set up a baikal server to get things under my controll, but the data had distributed changes and there was no definitive dataset containing all changes. Moreover, exporting the data was quite hard. Outlook could export only individual VCARDS, Android could not export natively, the iPad not, too. Tools I tried out just exported some parts of the contacts, but not all details. Outlook Web Access contained relatively up-to-date data, but had no export, too. Thus, I installed DavMail and connected to OWA Web Services, loaded this into Thunderbird where an export was possible, but did not contain the full information, for example, the third address set for some contacts. I also installed the Outlook and OWA apps on the iPad and Android, but those covered the notes section of all the contacts with some nag messages.

After all, it was a mess. My address book might not have been too tidy before, but now, it's completely disastrous. So I wrote this tool to accomplish normalization and integration of the different sources.
