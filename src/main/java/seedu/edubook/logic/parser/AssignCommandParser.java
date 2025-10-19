package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.stream.Stream;

import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.assign.AssignTarget;
import seedu.edubook.model.assign.ClassAssignTarget;
import seedu.edubook.model.assign.NameAssignTarget;
import seedu.edubook.model.assignment.Assignment;
import seedu.edubook.model.assignment.AssignmentName;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;

/**
 * Parses input arguments and creates a new {@link AssignCommand} object.
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     *
     * @param args User input arguments.
     * @return An {@link AssignCommand} representing the parsed assignment and target.
     * @throws ParseException If the input does not conform to the expected format.
     */
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME, PREFIX_CLASS);

        // Validate prefixes, preamble, and duplicates
        validateAssignCommandPrefixes(argMultimap);

        AssignmentName assignmentName = ParserUtil
                .parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get());
        Assignment assignment = new Assignment(assignmentName);

        AssignTarget target = parseAssignTarget(argMultimap);

        return new AssignCommand(assignment, target);
    }

    /**
     * Validates the prefixes provided for the {@code assign} command.
     * Ensures that the assignment prefix (a/) is present and exactly one of
     * either the person name prefix (n/) or the class prefix (c/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateAssignCommandPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasAssignment = argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent();
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClass = argMultimap.getValue(PREFIX_CLASS).isPresent();

        if (!hasAssignment || (!hasName && !hasClass)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        if (hasName && hasClass) {
            throw new ParseException("Specify only n/NAME or c/CLASS, not both.");
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }


        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME, PREFIX_CLASS);
    }

    /**
     * Creates an {@code AssignTarget} based on the provided argument multimap.
     * Assumes that validation has already ensured exactly one of n/ or c/ is present.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @return An {@code AssignTarget} representing the assignment target.
     * @throws ParseException If parsing of the target fails.
     */
    private static AssignTarget parseAssignTarget(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_PERSON_NAME).isPresent()) {
            PersonName personName = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_PERSON_NAME).get());
            return new NameAssignTarget(personName);
        } else {
            TuitionClass tuitionClass = ParserUtil.parseClass(argMultimap.getValue(PREFIX_CLASS).get());
            return new ClassAssignTarget(tuitionClass);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
