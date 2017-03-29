package seedu.ezdo.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.ezdo.model.todo.ReadOnlyTask;
//@@author A0139248X
/*
 * Checks for dates
 */
public class DateUtil {

    /**
     * Checks whether a task's dates are valid
     * @return true if the start date is earlier than or equal to the due date OR if either date is empty
     * @throws ParseException if any task date cannot be parsed
     */
    public static boolean isTaskDateValid(ReadOnlyTask task) throws ParseException {
        assert task != null;
        if (task.getStartDate().toString().isEmpty() || task.getDueDate().toString().isEmpty()) {
            return true;
        }
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy H:mm");
        Date startDate = df.parse(task.getStartDate().toString());
        Date dueDate = df.parse(task.getDueDate().toString());
        return (startDate.compareTo(dueDate) <= 0);
    }


    //@@author A0138907W
    /**
     * Compares two dates strings. Both strings must be in the format dd/MM/yyyy hh:mm.
     * Empty strings are always considered to be of lower value than non-empty strings.
     *
     * @return An int representing the comparison result of the two date strings.
     * @throws ParseException if any of the date strings cannot be parsed.
     */
    public static int compareDateStrings(String dateString1, String dateString2, Boolean isSortedAscending) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date1 = null;
        Date date2 = null;

        // Empty dates are always considered lower in value so that they show at the bottom of the list
        if (dateString1.isEmpty() && dateString2.isEmpty()) {
            return 0;
        } else if (dateString1.isEmpty()) {
            return 1;
        } else if (dateString2.isEmpty()) {
            return -1;
        }

        try {
            date1 = dateFormat.parse(dateString1);
            date2 = dateFormat.parse(dateString2);
        } catch (ParseException pe) {
            assert false : "The date format should not be invalid.";
        }
        int result = date1.compareTo(date2);

        // If the sort order is descending, return a negative value to invert the order.
        return isSortedAscending ? result : -result;
    }

}
