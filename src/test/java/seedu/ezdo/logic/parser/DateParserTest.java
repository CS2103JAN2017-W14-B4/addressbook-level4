package seedu.ezdo.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.ezdo.logic.parser.DateParser.USER_DATE_OUTPUT_FORMAT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * Tests the behaviour of the date parser
 */
public class DateParserTest {

    private DateParser dateParser;

    /**
     * Checks if dates in natural language are parsed correctly
     * @throws Exception
     */
    @Test
    public void checkNaturalParsing() throws Exception {
        DateFormat expectedFormat = new SimpleDateFormat(USER_DATE_OUTPUT_FORMAT);
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);

        dateParser = new DateParser("today");
        String expectedValue = expectedFormat.format(dt);
        assertTrue(expectedValue.equals(dateParser.value));

        dateParser = new DateParser("tomorrow");
        c.add(Calendar.DATE, 1); // add 1 day to get tomorrow's date
        dt = c.getTime();
        expectedValue = expectedFormat.format(dt);
        assertTrue(expectedValue.equals(dateParser.value));
    }

    /**
     * Checks if dates formatted in DD/MM/YYYY are parsed correctly
     */
    @Test
    public void checkFormatParsing() throws Exception {
        dateParser = new DateParser("31/12/2017 11:00");
        assertEquals(dateParser.value, "31/12/2017 11:00");

        dateParser = new DateParser("1/1/2016 12:00");
        assertEquals(dateParser.value, "01/01/2016 12:00");

        dateParser = new DateParser("15/01/2016 00:00");
        assertEquals(dateParser.value, "15/01/2016 00:00");
    }
}
