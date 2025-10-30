package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.Messages.MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_CLASS;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.edubook.logic.parser.CliSyntax.PREFIX_PERSON_NAME;

import seedu.edubook.logic.commands.LabelCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;
import seedu.edubook.model.label.Label;
import seedu.edubook.model.person.PersonName;
import seedu.edubook.model.person.TuitionClass;
import seedu.edubook.model.target.ClassTarget;
import seedu.edubook.model.target.NameTarget;
import seedu.edubook.model.target.Target;

/**
 * Parses input arguments and creates a new {@link LabelCommand} object.
 */
public class LabelCommandParser implements Parser<LabelCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LabelCommand
     * and returns an LabelCommand object for execution.
     *
     * @param args User input arguments.
     * @return An {@link LabelCommand} representing the parsed label and target.
     * @throws ParseException If the input does not conform to the expected format.
     */
    public LabelCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LABEL, PREFIX_PERSON_NAME, PREFIX_CLASS);

        // Validate prefixes, preamble, and duplicates
        validateLabelCommandPrefixes(argMultimap);

        Label label = ParserUtil
                .parseLabel(argMultimap.getValue(PREFIX_LABEL).get());

        Target target = parseLabelTarget(argMultimap);

        return new LabelCommand(label, target);
    }

    /**
     * Validates the prefixes provided for the {@code label} command.
     * Ensures that the {@code label} prefix (l/) is present and exactly one of
     * either the person {@code name} name prefix (n/) is present.
     * Also checks that no unexpected preamble exists and that there are no duplicate prefixes.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @throws ParseException If the validation fails due to missing or conflicting prefixes,
     *                        unexpected preamble, or duplicate prefixes.
     */
    private static void validateLabelCommandPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasLabel = argMultimap.getValue(PREFIX_LABEL).isPresent();
        boolean hasName = argMultimap.getValue(PREFIX_PERSON_NAME).isPresent();
        boolean hasClass = argMultimap.getValue(PREFIX_CLASS).isPresent();

        if (!hasLabel || (!hasName && !hasClass)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE));
        }

        if (hasName && hasClass) {
            throw new ParseException(MESSAGE_NAME_CLASS_CONFLICTING_PREFIXES);
        }

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LabelCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LABEL, PREFIX_PERSON_NAME, PREFIX_CLASS);
    }

    /**
     * Creates an {@code LabelTarget} based on the provided argument multimap.
     *
     * @param argMultimap The tokenized arguments containing prefixes and values.
     * @return An {@code LabelTarget} representing the assignment target.
     * @throws ParseException If parsing of the target fails.
     */
    private static Target parseLabelTarget(ArgumentMultimap argMultimap) throws ParseException {
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
