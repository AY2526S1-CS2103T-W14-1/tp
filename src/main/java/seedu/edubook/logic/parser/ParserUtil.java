package seedu.edubook.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.edubook.commons.core.index.Index;
import seedu.edubook.commons.util.StringUtil;
import seedu.edubook.logic.parser.exceptions.ExceedLengthException;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.person.Email;
import seedu.edubook.model.commons.Name;
import seedu.edubook.model.person.Phone;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);

        String trimmedName = name.trim();

        if (!Name.isValidLength(trimmedName)) {
            throw new ExceedLengthException(Name.MESSAGE_LENGTH_CONSTRAINTS);
        }

        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }

        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String assignment} into an {@code Assignment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code assignment} is invalid.
     */
    public static Assignment parseAssignment(String assignment) throws ParseException {
        requireNonNull(assignment);

        String trimmedAssignment = assignment.trim();

        if (!Assignment.isValidLength(trimmedAssignment)) {
            throw new ExceedLengthException(Assignment.MESSAGE_LENGTH_CONSTRAINTS);
        }

        if (!Assignment.isValidAssignment(trimmedAssignment)) {
            throw new ParseException(Assignment.MESSAGE_CONSTRAINTS);
        }

        Name assignmentName = parseName(assignment);
        return new Assignment(assignmentName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);

        String trimmedPhone = phone.trim();

        if (!Phone.isValidLength(trimmedPhone)) {
            throw new ExceedLengthException(Phone.MESSAGE_LENGTH_CONSTRAINTS);
        }

        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String tuitionClass} into an {@code TuitionClass}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tuitionClass} is invalid.
     */
    public static TuitionClass parseClass(String tuitionClass) throws ParseException {
        requireNonNull(tuitionClass);

        String trimmedClass = tuitionClass.trim();

        if (!TuitionClass.isValidLength(trimmedClass)) {
            throw new ExceedLengthException(TuitionClass.MESSAGE_LENGTH_CONSTRAINTS);
        }

        if (!TuitionClass.isValidClass(trimmedClass)) {
            throw new ParseException(TuitionClass.MESSAGE_CONSTRAINTS);
        }

        return new TuitionClass(trimmedClass);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);

        String trimmedEmail = email.trim();

        if (!Email.isValidLength(trimmedEmail)) {
            throw new ExceedLengthException(Email.MESSAGE_LENGTH_CONSTRAINTS);
        }

        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
