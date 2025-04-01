package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SetSubmissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link Command} object
 */
public class SubmissionParser implements Parser<Command> {

    public static final String COMMAND_WORD = "submission";
    private final Map<String, Parser<? extends Command>> subcmds;
    private final String usage;

    SubmissionParser() {
        subcmds = new HashMap<>();

        subcmds.put(SetSubmissionCommand.COMMAND_WORD, new SetSubmissionCommandParser());

        usage = """
                        Usage: submission COMMAND
                        COMMAND: %s""".formatted(String.join(", ", subcmds.keySet()));
    }

    @Override
    public Command parse(String arguments) throws ParseException {
        var cmd = arguments.trim().split(" ");
        var rest = Arrays.stream(cmd).skip(1).collect(Collectors.joining(" "));

        if (!subcmds.containsKey(cmd[0])) {
            throw new ParseException(Messages.MESSAGE_INVALID_COMMAND_FORMAT.formatted(usage));
        }

        return subcmds.get(cmd[0]).parse(rest);
    }
}
