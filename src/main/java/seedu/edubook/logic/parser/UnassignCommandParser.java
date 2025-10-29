package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.logic.commands.UnassignCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.Target;

/**
 * Parses input arguments and creates a new UnassignCommand object.
 */
public class UnassignCommandParser implements Parser<UnassignCommand> {

    /**
     * /**
     * Parses the given {@code String} of arguments in the context of the UnassignCommand
     * and returns an UnassignCommand object for execution.
     *
     * @param args User input arguments.
     * @return An {@link UnassignCommand} representing the parsed assignment and target.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public UnassignCommand parse(String args) throws ParseException {
        assert args != null : "args should never be null when parsing UnassignCommand.";

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME, PREFIX_CLASS);

        // Validate prefixes, preamble, and duplicates
        validateUnassignCommandPrefixes(argMultimap);

        AssignmentName assignmentName = ParserUtil
                .parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get());

        Target target = parseUnassignTarget(argMultimap);

        return new UnassignCommand(assignmentName, target);
    }

    /**
     * Validates the prefixes provided for the {@code unassign} command.
     * Ensures that the {@code assignment} prefix (a/) is present and exactly one of
     * either the person {@code name} name prefix (n/) or the {@code class} prefix (c/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateUnassignCommandPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasAssignment = argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent();
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClass = argMultimap.getValue(PREFIX_CLASS).isPresent();

        if (!hasAssignment || (!hasName && !hasClass)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        }

        if (hasName && hasClass) {
            throw new ParseException(MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
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
    private static Target parseUnassignTarget(ArgumentMultimap argMultimap) throws ParseException {
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
}
