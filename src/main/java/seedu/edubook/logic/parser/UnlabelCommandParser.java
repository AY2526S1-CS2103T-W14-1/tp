package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.logic.commands.UnlabelCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.person.PersonName;
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
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON_NAME);

        // Validate prefixes, preamble, and duplicates
        validateUnlabelCommandPrefixes(argMultimap);

        Target target = parseUnlabelTarget(argMultimap);

        return new UnlabelCommand(target);
    }

    /**
     * Validates the prefixes provided for the {@code unlabel} command.
     * either the person {@code name} name prefix (n/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateUnlabelCommandPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();

        if (!hasName) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlabelCommand.MESSAGE_USAGE));
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlabelCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PERSON_NAME);
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

        // Use XOR to assert that only n/ is present.
        assert hasNamePrefix : "n/ must be present.";

        PersonName personName = ParserUtil.parsePersonName(argMultimap.getValue(PREFIX_PERSON_NAME).get());
        return new NameTarget(personName);
    }
}
