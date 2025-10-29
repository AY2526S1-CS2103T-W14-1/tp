package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.logic.commands.UnlabelCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.Target;

/**
 * Parses input arguments and creates a new UnassignCommand object.
 */
public class UnlabelCommandParser implements Parser<UnlabelCommand> {

    /**
     * /**
     * Parses the given {@code String} of arguments in the context of the UnlabelCommand
     * and returns an UnlabelCommand object for execution.
     *
     * @param args User input arguments.
     * @return An {@link UnlabelCommand} representing the parsed target.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public UnlabelCommand parse(String args) throws ParseException {
        assert args != null : "args should never be null when parsing UnlabelCommand.";

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_NAME, PREFIX_CLASS);

        // Validate prefixes, preamble, and duplicates
        validateUnlabelCommandPrefixes(argMultimap);

        Target target = parseUnlabelTarget(argMultimap);

        return new UnlabelCommand(target);
    }

    /**
     * Validates the prefixes provided for the {@code unlabel} command.
     * exactly one of either the person {@code name} name prefix (n/) or the {@code class} prefix (c/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateUnlabelCommandPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClass = argMultimap.getValue(PREFIX_CLASS).isPresent();

        if (!hasName && !hasClass) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlabelCommand.MESSAGE_USAGE));
        }

        if (hasName && hasClass) {
            throw new ParseException(MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlabelCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_NAME, PREFIX_CLASS);
    }

    /**
     * Creates a {@code Target} based on the provided argument multimap.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @return A {@code Target} representing the target.
     * @throws ParseException If parsing of the target fails.
     */
    private static Target parseUnlabelTarget(ArgumentMultimap argMultimap) throws ParseException {
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
