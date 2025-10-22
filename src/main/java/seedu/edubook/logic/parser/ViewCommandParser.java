package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import java.util.stream.Stream;

import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.commands.ViewCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
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
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_NAME);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_NAME);

        if (noPrefixesPresent(argMultimap, PREFIX_PERSON_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_CLASS);

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLASS);

            if (noPrefixesPresent(argMultimap, PREFIX_CLASS)
                    || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ViewCommand.MESSAGE_USAGE));
            }

            TuitionClass tuitionClass =
                    ParserUtil.parseClass(argMultimap.getValue(PREFIX_CLASS).get());

            return new ViewCommand(tuitionClass);
        }

        PersonName name = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_PERSON_NAME).get());

        return new ViewCommand(name);
    }

    /**
     * Validates the prefixes provided for the {@code assign} command.
     * Ensures that the {@code assignment} prefix (a/) is present and exactly one of
     * either the person {@code name} name prefix (n/) or the {@code class} prefix (c/) is present.
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
            throw new ParseException(MESSAGE_CONFLICTING_PREFIXES);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_NAME, PREFIX_PERSON_NAME, PREFIX_CLASS);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean noPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return !Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
