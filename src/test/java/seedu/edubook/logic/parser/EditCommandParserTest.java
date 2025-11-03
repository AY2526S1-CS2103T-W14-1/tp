package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.CLASS_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_CLASS_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_LABEL_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.edubook.logic.commands.CommandTestUtil.LABEL_DESC_BAD;
import static seedu.edubook.logic.commands.CommandTestUtil.LABEL_DESC_GOOD;
import static seedu.edubook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.edubook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_HOMEWORK;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_TUTORIAL;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_CLASS_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_LABEL_GOOD;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.edubook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edubook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.edubook.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.logic.Messages;
import seedu.edubook.logic.commands.EditCommand;
import seedu.edubook.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.label.Label;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;
import seedu.edubook.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    private static final String ASSIGNMENT_EMPTY = " " + PREFIX_ASSIGNMENT_NAME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, PersonName.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_CLASS_DESC, TuitionClass.MESSAGE_CONSTRAINTS); // invalid class
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag
        assertParseFailure(parser, "1" + INVALID_LABEL_DESC, Label.MESSAGE_CONSTRAINTS); // invalid label
        assertParseFailure(parser,
                "1" + INVALID_ASSIGNMENT_DESC, AssignmentName.MESSAGE_CONSTRAINTS); // invalid assignment

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_ASSIGNMENT} alone will reset the assignments of the {@code Person} being edited,
        // parsing it together with a valid assignment results in error
        assertParseFailure(parser,
                "1" + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_DESC_TUTORIAL + ASSIGNMENT_EMPTY,
                AssignmentName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_EMPTY + ASSIGNMENT_DESC_TUTORIAL,
                AssignmentName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + ASSIGNMENT_EMPTY + ASSIGNMENT_DESC_HOMEWORK + ASSIGNMENT_DESC_TUTORIAL,
                AssignmentName.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_CLASS_AMY + VALID_PHONE_AMY,
                PersonName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND + ASSIGNMENT_DESC_HOMEWORK
                + EMAIL_DESC_AMY + CLASS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND + ASSIGNMENT_DESC_TUTORIAL
                + LABEL_DESC_GOOD;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withClass(VALID_CLASS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withAssignments(VALID_ASSIGNMENT_HOMEWORK,
                        VALID_ASSIGNMENT_TUTORIAL).withLabel(VALID_LABEL_GOOD).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // classes
        userInput = targetIndex.getOneBased() + CLASS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withClass(VALID_CLASS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // label
        userInput = targetIndex.getOneBased() + LABEL_DESC_GOOD;
        descriptor = new EditPersonDescriptorBuilder().withLabel(VALID_LABEL_GOOD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // assignments
        userInput = targetIndex.getOneBased() + ASSIGNMENT_DESC_HOMEWORK;
        descriptor = new EditPersonDescriptorBuilder().withAssignments(VALID_ASSIGNMENT_HOMEWORK).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + CLASS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + ASSIGNMENT_DESC_HOMEWORK + PHONE_DESC_AMY + CLASS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + ASSIGNMENT_DESC_HOMEWORK + PHONE_DESC_BOB + CLASS_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + ASSIGNMENT_DESC_TUTORIAL + LABEL_DESC_GOOD + LABEL_DESC_BAD;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LABEL, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_CLASS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_CLASS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_CLASS_DESC + INVALID_EMAIL_DESC + INVALID_LABEL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL, PREFIX_CLASS, PREFIX_PHONE));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetAssignments_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + ASSIGNMENT_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withAssignments().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
