package com.github.tobias_vogel.mergecontacts.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.tobias_vogel.mergecontacts.data.CardDavContact.CardDavContactAttributes;
import com.google.common.base.Joiner;

public abstract class AdditionalData {

    protected CardDavContactAttributes attribute;
    protected String value;

    private static final String NOTES_SPLIT_TOKEN = "<|>";

    private static final Pattern NOTES_SPLIT_PATTERN = Pattern.compile(NOTES_SPLIT_TOKEN);

    // TODO use named capturing groups
    private static final Pattern IS_ADDITIONAL_DATA_PATTERN = Pattern
            .compile("((" + OldData.IDENTIFIER + "|" + AlternativeData.IDENTIFIER + "))-(.+)=(.+)");





    /**
     * Parses the provided notes field value and extracts all additional data
     * fields. These fields are added to the provided additional data list. If
     * it is null, it is created. The notes field value is essentially clensed
     * from additional data and returned.
     * 
     * @param notesValue
     *            the value from the notes field
     * @param additionalData
     *            a (possibly <code>null</code>) list of already known
     *            additional data items
     * @return the notes field value without additional data
     */
    public static String parseNotesAndMergeWithAdditionalDataAndEnrichAdditionalData(String notesValue,
            List<AdditionalData> additionalData) {
        if (notesValue == null) {
            return null;
        }

        List<String> noteFragments = new ArrayList<>();

        if (additionalData == null) {
            additionalData = new ArrayList<>();
        }

        String[] parts = NOTES_SPLIT_PATTERN.split(notesValue);
        for (String part : parts) {
            if (isAdditionalData(part)) {
                AdditionalData additionalDataElement = createAdditionalDataFromString(part);
                additionalData.add(additionalDataElement);
            } else {
                noteFragments.add(part);
            }
        }

        String joinedNoteFragments = Joiner.on(", ").join(noteFragments);
        return joinedNoteFragments;
    }





    private static AdditionalData createAdditionalDataFromString(String serializedAdditionalData) {
        Matcher matcher = IS_ADDITIONAL_DATA_PATTERN.matcher(serializedAdditionalData);
        boolean matchSuccessful = matcher.matches();
        if (matchSuccessful) {
            String type = matcher.group("type");
            String attributeAsString = matcher.group("attribute");
            String value = matcher.group("value");

            CardDavContactAttributes attribute = CardDavContactAttributes.valueOf(attributeAsString);

            AdditionalData additionalData;

            switch (type) {
            case AlternativeData.IDENTIFIER:
                additionalData = new AlternativeData(attribute, value);
                break;

            case OldData.IDENTIFIER:
                additionalData = new OldData(attribute, value);
                break;

            default:
                throw new RuntimeException("The provided additional data type (\"" + type + "\" is unknown.");
            }

            return additionalData;
        } else
            throw new RuntimeException("The provided string (\"" + serializedAdditionalData
                    + "\") should have been a serialized additional data portion, but was not.");
    }





    private static boolean isAdditionalData(String part) {
        return IS_ADDITIONAL_DATA_PATTERN.matcher(part).matches();
    }





    public static String generateNotesFieldContent(List<AdditionalData> additionalDataItems,
            String currentNotesFieldContent) {
        List<String> resultTokens = new ArrayList<>(1 + additionalDataItems.size());
        resultTokens.add(currentNotesFieldContent);
        // TODO use java8 magic
        for (AdditionalData additionalDataItem : additionalDataItems) {
            resultTokens.add(additionalDataItem.createKeyValuePair());
        }

        return Joiner.on(NOTES_SPLIT_TOKEN).join(resultTokens);
    }





    private String createKeyValuePair() {
        return getIdentifier() + "-" + attribute + "=" + value;
    }





    public static List<String> getFilteredCopyOfAdditionalData(CardDavContactAttributes filterAttribute,
            Class<? extends AdditionalData> filterTypeOfAdditionalData, List<AdditionalData> additionalData) {
        // TODO use java8 magic
        List<String> result = new ArrayList<>();
        for (AdditionalData additionalDataItem : additionalData) {
            if (additionalDataItem.getClass() == filterTypeOfAdditionalData) {
                if (additionalDataItem.attribute == filterAttribute) {
                    result.add(additionalDataItem.value);
                }
            }
        }

        return result;
    }





    public abstract String getIdentifier();
}