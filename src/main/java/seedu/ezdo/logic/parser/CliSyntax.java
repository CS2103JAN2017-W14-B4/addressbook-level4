package seedu.ezdo.logic.parser;

import java.util.regex.Pattern;

import seedu.ezdo.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_STARTDATE = new Prefix("s/");
    public static final Prefix PREFIX_DUEDATE = new Prefix("d/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_MONTH = new Prefix("month/");
    public static final Prefix PREFIX_YEAR = new Prefix("year/");
    public static final Prefix PREFIX_DAY = new Prefix("day/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
