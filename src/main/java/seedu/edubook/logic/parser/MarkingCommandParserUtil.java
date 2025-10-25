package seedu.edubook.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.edubook.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.edubook.logic.Messages.MESSAGE_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.stream.Stream;

import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.Target;

/**
 * Contains common parsing logic for commands that mark or unmark assignments.
 * Supports marking an assignment for either a single person or an entire class.
 */
public class MarkingCommandParserUtil {

    /**
     * Parses the given {@code String} of arguments and returns a {@code ParsedPacket}
     * containing the assignment name and the target (person or class).
     *
     * @param args input string entered by the user.
     * @param messageUsage usage message of the calling command
     * @return a {@code ParsedPacket} containing assignment name and target.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public static ParsedPacket parseAssignmentAndTarget(String args, String messageUsage)
            throws ParseException {
        assert args != null : "args should not be null";
        assert messageUsage != null : "messageUsage should not be null";

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME, PREFIX_CLASS);

        // Validate prefixes, preamble, and duplicates
        validateCommandPrefixes(argMultimap, messageUsage);

        AssignmentName assignmentName = ParserUtil
                .parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get());

        Target target = parseTarget(argMultimap);

        return new ParsedPacket(assignmentName, target);
    }

    /**
     * Validates the prefixes provided for the mark/unmark commands.
     * Ensures that the {@code assignment} prefix (a/) is present and exactly one of
     * either the person {@code name} name prefix (n/) or the {@code class} prefix (c/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @param messageUsage usage message for displaying proper command format
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateCommandPrefixes(ArgumentMultimap argMultimap, String messageUsage)
            throws ParseException {
        boolean hasAssignment = argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent();
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClass = argMultimap.getValue(PREFIX_CLASS).isPresent();

        if (!hasAssignment || (!hasName && !hasClass)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        if (hasName && hasClass) {
            throw new ParseException(MESSAGE_CONFLICTING_PREFIXES);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME, PREFIX_CLASS);
    }

    /**
     * Creates a {@code Target} based on the provided argument multimap.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @return A {@code Target} representing the target.
     * @throws ParseException If parsing of the target fails.
     */
    private static Target parseTarget(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasNamePrefix = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClassPrefix = argMultimap.getValue(PREFIX_CLASS).isPresent();

        // Use XOR to assert that only one of n/ or c/ is present and not none or both.
        assert hasNamePrefix ^ hasClassPrefix : "Exactly one of n/ or c/ must be present.";

        if (argMultimap.getValue(PREFIX_PERSON_NAME).isPresent()) {
            PersonName personName = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_PERSON_NAME).get());
            return new NameTarget(personName);
        } else {
            TuitionClass tuitionClass = ParserUtil.parseClass(argMultimap.getValue(PREFIX_CLASS).get());
            return new ClassTarget(tuitionClass);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Holder class for returning parsed assignment and target (person or class) together.
     */
    public static class ParsedPacket {
        /** Assignment to be marked. */
        public final AssignmentName assignmentName;

        /** Target person. */
        public final PersonName person;

        /** Target for the assignment (person or class). */
        public final Target target;

        /**
         * Constructs a new ParsedPacket containing AssignmentName and PersonName.
         */
        public ParsedPacket(AssignmentName assignmentName, PersonName person) {
            requireNonNull(assignmentName);
            requireNonNull(person);
            this.assignmentName = assignmentName;
            this.person = person;
            this.target = null;
        }

        /**
         * Constructs a new {@code ParsedPacket}.
         *
         * @param assignmentName the assignment to mark
         * @param target the target (person or class) for the assignment
         */
        public ParsedPacket(AssignmentName assignmentName, Target target) {
            requireNonNull(assignmentName);
            requireNonNull(target);
            this.assignmentName = assignmentName;
            this.target = target;
            this.person = null;
        }
    }
}
