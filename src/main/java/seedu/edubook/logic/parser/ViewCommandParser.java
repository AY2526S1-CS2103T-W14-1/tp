package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.logic.commands.ViewCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.target.AssignmentTarget;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.Target;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @param args User input arguments.
     * @return An {@link ViewCommand} representing the parsed target.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_NAME, PREFIX_CLASS, PREFIX_ASSIGNMENT_NAME);

        validateViewCommandPrefixes(argMultimap);

        Target target = parseViewTarget(argMultimap);

        return new ViewCommand(target);
    }

    /**
     * Validates the prefixes provided for the {@code view} command.
     * Ensures that exactly one of either the person {@code name} name prefix (n/)
     * or the {@code class} prefix (c/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateViewCommandPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClass = argMultimap.getValue(PREFIX_CLASS).isPresent();
        boolean hasAssignment = argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent();

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        if (!hasName && !hasClass && !hasAssignment) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        if (hasName && hasClass && !hasAssignment) {
            throw new ParseException(MESSAGE_CONFLICTING_PREFIXES);
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_NAME, PREFIX_CLASS, PREFIX_ASSIGNMENT_NAME);
    }

    /**
     * Creates a {@code ViewTarget} based on the provided argument multimap.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @return A {@code Target} representing the viewing target.
     * @throws ParseException If parsing of the target fails.
     */
    private static Target parseViewTarget(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasNamePrefix = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClassPrefix = argMultimap.getValue(PREFIX_CLASS).isPresent();
        boolean hasAssignmentPrefix = argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent();

        // Use XOR to assert that only one of n/ or c/ is present and not none or both.
        assert hasNamePrefix ^ hasClassPrefix ^ hasAssignmentPrefix: "Exactly one of n/, c/ or a/ must be present.";

        if (argMultimap.getValue(PREFIX_PERSON_NAME).isPresent()) {
            PersonName personName = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_PERSON_NAME).get());
            return new NameTarget(personName);
        } else if (argMultimap.getValue(PREFIX_CLASS).isPresent()) {
            TuitionClass tuitionClass = ParserUtil.parseClass(argMultimap.getValue(PREFIX_CLASS).get());
            return new ClassTarget(tuitionClass);
        } else {
            AssignmentName assignmentName =
                    ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get());
            return new AssignmentTarget(assignmentName);
        }
    }
}
