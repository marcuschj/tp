package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.ViewCommandIndex;
import seedu.address.logic.commands.ViewCommandName;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ParseException pe;

        //parse by index
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ViewCommandIndex(index);
        } catch (ParseException peIndex) {
            pe = peIndex;
        }

        //parse by name
        try {
            Name name = ParserUtil.parseName(args);
            return new ViewCommandName(name);
        } catch (ParseException peName) {
            pe = peName;
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
    }
}
