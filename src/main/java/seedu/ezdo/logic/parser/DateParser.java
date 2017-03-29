package seedu.ezdo.logic.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateParser {

    public String value;

    public static final String USER_DATE_OUTPUT_FORMAT = "dd/MM/yyyy HH:mm";

    public static final String DAY_MONTH_YEAR_FORMAT = "^([0-9]{1,2}[-/])([0-9]{1,2}[-/])([0-9]{4}.*)";

    public final SimpleDateFormat userOutputDateFormat = new SimpleDateFormat(USER_DATE_OUTPUT_FORMAT);


    //@@author A0138907W
    public DateParser(String input) {
        // swap day and month as natty only accepts the MM/DD/YYYY format
        if (input.matches(DAY_MONTH_YEAR_FORMAT)) {
            input = swapDayAndMonth(input);
        }
        this.value = nattyManipulation(input);
    }

    /**
     * Swaps the day and month of a date in DD/MM/YYYY format for natty to parse.
     *
     * @param input A string representing a date in the DD/MM/YYYY format.
     * @return A string equal to the input date but in MM/DD/YYYY format.
     */
    private String swapDayAndMonth(String input) {
        Pattern dayMonthYearPattern = Pattern.compile(DAY_MONTH_YEAR_FORMAT);
        Matcher matcher = dayMonthYearPattern.matcher(input);

        matcher.matches();
        return matcher.group(2) + matcher.group(1) + matcher.group(3);
    }

    //@@author
    /**
     * Uses Natty dependency (natural language date parser) to manipulate date
     * input in String.
     */
    private String nattyManipulation(String taskDate) {
        try {
            // Initialises Natty parser
            Parser parser = new Parser();

            // Parses input String into a list of DateGroups
            List<DateGroup> dateGroupList = parser.parse(taskDate);

            // Retrieves parsed date
            Date parsedDate = dateGroupList.get(0).getDates().get(0);

            // Format parsed date to suit the UI

            return userOutputDateFormat.format(parsedDate);

        } catch (IndexOutOfBoundsException e) {
            return taskDate;
        }
    }

}
