package seedu.edubook.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.edubook.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.edubook.testutil.Assert.assertThrows;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.edubook.logic.parser.exceptions.ExceedLengthException;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ASSIGNMENT = "Tutorial@3";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_CLASS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_ASSIGNMENT_1 = "Homework 1";
    private static final String VALID_ASSIGNMENT_2 = "Quiz 1";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_CLASS = "W-14";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_CLASS_MAXLENGTH =
            String.join("", java.util.Collections.nCopies(20, "t"));
    private static final String VALID_EMAIL_MAXLENGTH =
            String.join("", java.util.Collections.nCopies(238, "t")) + "@example.com";
    private static final String VALID_NAME_MAXLENGTH =
            String.join("", java.util.Collections.nCopies(100, "t"));
    private static final String VALID_PHONE_MAXLENGTH =
            String.join("", java.util.Collections.nCopies(20, "1"));
    private static final String VALID_ASSIGNMENT_MAXLENGTH =
            String.join("", java.util.Collections.nCopies(100, "A"));

    private static final String INVALID_CLASS_MAXLENGTH_PLUSONE =
            String.join("", java.util.Collections.nCopies(20, "t")) + "1";
    private static final String INVALID_EMAIL_MAXLENGTH_PLUSONE =
            String.join("", java.util.Collections.nCopies(238, "t")) + "@example.com" + "1";
    private static final String INVALID_NAME_MAXLENGTH_PLUSONE =
            String.join("", java.util.Collections.nCopies(100, "t")) + "1";
    private static final String INVALID_PHONE_MAXLENGTH_PLUSONE =
            String.join("", java.util.Collections.nCopies(20, "1")) + "1";
    private static final String INVALID_ASSIGNMENT_MAXLENGTH_PLUSONE =
            String.join("", java.util.Collections.nCopies(100, "A")) + "1";

    private static final String WHITESPACE = " \t\r\n";

    private static final String INVALID_CLASS_LENGTH =
            String.join("", java.util.Collections.nCopies(10, "test"));
    private static final String INVALID_EMAIL_LENGTH =
            String.join("", java.util.Collections.nCopies(80, "test")) + "@example.com";
    private static final String INVALID_NAME_LENGTH =
            String.join("", java.util.Collections.nCopies(50, "test"));
    private static final String INVALID_PHONE_LENGTH =
            String.join("", java.util.Collections.nCopies(10, "1234"));
    private static final String INVALID_ASSIGNMENT_LENGTH =
            String.join("", java.util.Collections.nCopies(200, "A"));

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parsePersonName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePersonName((String) null));
    }

    @Test
    public void parsePersonName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePersonName(INVALID_NAME));
    }

    @Test
    public void parsePersonName_invalidLength_ExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parsePersonName(INVALID_NAME_LENGTH));
    }

    @Test
    public void parsePersonName_maxLength_success() throws Exception {
        PersonName expectedName = new PersonName(VALID_NAME_MAXLENGTH);
        PersonName actualName = ParserUtil.parsePersonName(VALID_NAME_MAXLENGTH);
        assertEquals(expectedName, actualName);
    }

    @Test
    public void parsePersonName_maxLengthPlusOne_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parsePersonName(INVALID_NAME_MAXLENGTH_PLUSONE));
    }

    @Test
    public void parsePersonName_validValueWithoutWhitespace_returnsName() throws Exception {
        PersonName expectedName = new PersonName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parsePersonName(VALID_NAME));
    }

    @Test
    public void parsePersonName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        PersonName expectedName = new PersonName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parsePersonName(nameWithWhitespace));
    }

    @Test
    public void parseAssignmentName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAssignmentName(null));
    }

    @Test
    public void parseAssignmentName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignmentName(INVALID_ASSIGNMENT));
    }

    @Test
    public void parseAssignmentName_invalidLength_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parseAssignmentName(INVALID_ASSIGNMENT_LENGTH));
    }

    @Test
    public void parseAssignmentName_maxLength_success() throws Exception {
        AssignmentName expectedAssignmentName = new AssignmentName(VALID_ASSIGNMENT_MAXLENGTH);
        AssignmentName actualAssignmentName = ParserUtil.parseAssignmentName(VALID_ASSIGNMENT_MAXLENGTH);
        assertEquals(expectedAssignmentName, actualAssignmentName);
    }

    @Test
    public void parseAssignmentName_maxLengthPlusOne_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class,
                () -> ParserUtil.parseAssignmentName(INVALID_ASSIGNMENT_MAXLENGTH_PLUSONE));
    }

    @Test
    public void parseAssignmentName_validValueWithoutWhitespace_returnsAssignmentName() throws Exception {
        AssignmentName expectedAssignmentName = new AssignmentName(VALID_ASSIGNMENT_1);
        assertEquals(expectedAssignmentName, ParserUtil.parseAssignmentName(VALID_ASSIGNMENT_1));
    }

    @Test
    public void parseAssignmentName_validValueWithWhitespace_returnsTrimmedAssignmentName() throws Exception {
        String assignmentWithWhitespace = WHITESPACE + VALID_ASSIGNMENT_1 + WHITESPACE;
        AssignmentName expectedAssignmentName = new AssignmentName(VALID_ASSIGNMENT_1);
        assertEquals(expectedAssignmentName, ParserUtil.parseAssignmentName(assignmentWithWhitespace));
    }

    @Test
    public void parseAssignments_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAssignments(null));
    }

    @Test
    public void parseAssignments_collectionWithInvalidAssignments_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAssignments(
                Arrays.asList(VALID_ASSIGNMENT_1, INVALID_ASSIGNMENT)));
    }

    @Test
    public void parseAssignments_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAssignments(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAssignments_collectionWithValidAssignments_returnsTagSet() throws Exception {
        Set<Assignment> actualAssignmentSet = ParserUtil.parseAssignments(
                Arrays.asList(VALID_ASSIGNMENT_1, VALID_ASSIGNMENT_2));
        Set<Assignment> expectedAssignmentSet = new HashSet<Assignment>(
                Arrays.asList(new Assignment(new AssignmentName(VALID_ASSIGNMENT_1)),
                              new Assignment(new AssignmentName(VALID_ASSIGNMENT_2))));

        assertEquals(expectedAssignmentSet, actualAssignmentSet);
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_invalidLength_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_LENGTH));
    }

    @Test
    public void parsePhone_maxLength_success() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE_MAXLENGTH);
        Phone actualPhone = ParserUtil.parsePhone(VALID_PHONE_MAXLENGTH);
        assertEquals(expectedPhone, actualPhone);
    }

    @Test
    public void parsePhone_maxLengthPlusOne_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_MAXLENGTH_PLUSONE));
    }
    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseClass_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseClass((String) null));
    }

    @Test
    public void parseClass_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClass(INVALID_CLASS));
    }

    @Test
    public void parseClass_invalidLength_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parseClass(INVALID_CLASS_LENGTH));
    }

    @Test
    public void parseClass_maxLength_success() throws Exception {
        TuitionClass expectedClass = new TuitionClass(VALID_CLASS_MAXLENGTH);
        TuitionClass actualClass = ParserUtil.parseClass(VALID_CLASS_MAXLENGTH);
        assertEquals(expectedClass, actualClass);
    }

    @Test
    public void parseClass_maxLengthPlusOne_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parseClass(INVALID_CLASS_MAXLENGTH_PLUSONE));
    }

    @Test
    public void parseClass_validValueWithoutWhitespace_returnsClass() throws Exception {
        TuitionClass expectedClass = new TuitionClass(VALID_CLASS);
        assertEquals(expectedClass, ParserUtil.parseClass(VALID_CLASS));
    }

    @Test
    public void parseClass_validValueWithWhitespace_returnsTrimmedClass() throws Exception {
        String classWithWhitespace = WHITESPACE + VALID_CLASS + WHITESPACE;
        TuitionClass expectedClass = new TuitionClass(VALID_CLASS);
        assertEquals(expectedClass, ParserUtil.parseClass(classWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_invalidLength_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL_LENGTH));
    }

    @Test
    public void parseEmail_maxLength_success() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL_MAXLENGTH);
        Email actualEmail = ParserUtil.parseEmail(VALID_EMAIL_MAXLENGTH);
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    public void parseEmail_maxLengthPlusOne_throwsExceedLengthException() {
        assertThrows(ExceedLengthException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL_MAXLENGTH_PLUSONE));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
