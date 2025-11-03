package seedu.edubook.logic.parser;

import static seedu.edubook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edubook.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.edubook.commons.core.LogsCenter;
import seedu.edubook.logic.commands.AddCommand;
import seedu.edubook.logic.commands.AssignCommand;
import seedu.edubook.logic.commands.ClearCommand;
import seedu.edubook.logic.commands.Command;
import seedu.edubook.logic.commands.DeleteCommand;
import seedu.edubook.logic.commands.EditCommand;
import seedu.edubook.logic.commands.ExitCommand;
import seedu.edubook.logic.commands.FindCommand;
import seedu.edubook.logic.commands.HelpCommand;
import seedu.edubook.logic.commands.LabelCommand;
import seedu.edubook.logic.commands.ListCommand;
import seedu.edubook.logic.commands.MarkCommand;
import seedu.edubook.logic.commands.UnassignCommand;
import seedu.edubook.logic.commands.UnlabelCommand;
import seedu.edubook.logic.commands.UnmarkCommand;
import seedu.edubook.logic.commands.ViewCommand;
import seedu.edubook.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     * <p>
     * The input is normalized by trimming extra spaces and converting the command word
     * to lowercase before parsing.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        // Trim and replace multiple spaces with a single space
        String normalizedInput = userInput.trim().replaceAll("\\s+", " ");

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(normalizedInput);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase(); // case-insensitive command word
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case AssignCommand.COMMAND_WORD:
            return new AssignCommandParser().parse(arguments);

        case UnassignCommand.COMMAND_WORD:
            return new UnassignCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case LabelCommand.COMMAND_WORD:
            return new LabelCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case UnlabelCommand.COMMAND_WORD:
            return new UnlabelCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
