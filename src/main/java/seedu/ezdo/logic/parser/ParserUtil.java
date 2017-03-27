package seedu.ezdo.logic.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.commons.util.StringUtil;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.tag.UniqueTagList;
import seedu.ezdo.model.todo.DueDate;
import seedu.ezdo.model.todo.Name;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.StartDate;
import seedu.ezdo.model.todo.TaskDate;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    private static final Pattern INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    private static final Pattern SORT_CRITERIA_ARGS_FORMAT = Pattern.compile("(?<sortCriteria>.) ?(?<sortOrder>.)?");
    private static final Pattern INDEXES_ARGS_FORMAT = Pattern.compile("^([0-9]*\\s+)*[0-9]*$");
    private static final Pattern COMMAND_ALIAS_ARGS_FORMAT = Pattern.compile("(?<command>.+) (?<alias>.+)");;

    /**
     * Returns the specified index in the {@code command} if it is a positive unsigned integer
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<Integer> parseIndex(String command) {
        final Matcher matcher = INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Returns the specified indexes in the {@code command} if they are
     * positive unsigned integers separated by whitespaces.
     * Returns empty array list otherwise.
     */
    public static ArrayList<Integer> parseIndexes(String command) {
        final Matcher matcher = INDEXES_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return new ArrayList<Integer>();
        }
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (String index : command.trim().split("\\s+")) {
            indexes.add(Integer.parseInt(index));
        }
        return indexes;
    }

    /**
     * Returns the specified sorting criteria in the {@code command} if it is present.
     * Returns an {@code Optional.empty()} otherwise.
     */
    public static Optional<String[]> parseSortCriteria(String command) {
        final Matcher matcher = SORT_CRITERIA_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String sortCriteria = matcher.group("sortCriteria");
        System.out.println(sortCriteria);
        String sortOrder = matcher.group("sortOrder");
        System.out.println(sortOrder);
        String[] result = new String[] {sortCriteria, sortOrder};
        return Optional.of(result);
    }

    /**
     * Returns a string array of the specified command and alias in the {@code command} if both are present.
     * Returns an empty String {@code Array} otherwise.
     */
    public static String[] parseCommandAlias(String command) {
        final Matcher matcher = COMMAND_ALIAS_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return new String[0];
        }
        String commandToAlias = matcher.group("command");
        String alias = matcher.group("alias");
        return new String[] {commandToAlias, alias};
    }

    /**
     * Returns a new Set populated by all elements in the given list of strings
     * Returns an empty set if the given {@code Optional} is empty,
     * or if the list contained in the {@code Optional} is empty
     */
    public static Set<String> toSet(Optional<List<String>> list) {
        List<String> elements = list.orElse(Collections.emptyList());
        return new HashSet<>(elements);
    }

    /**
    * Splits a preamble string into ordered fields.
    * @return A list of size {@code numFields} where the ith element is the ith field value if specified in
    *         the input, {@code Optional.empty()} otherwise.
    */
    public static List<Optional<String>> splitPreamble(String preamble, int numFields) {
        return Arrays.stream(Arrays.copyOf(preamble.split("\\s+", numFields), numFields))
                .map(Optional::ofNullable)
                .collect(Collectors.toList());
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        assert name != null;
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> priority} into an {@code Optional<Priority>} if {@code priority} is present.
     */
    public static Optional<Priority> parsePriority(Optional<String> priority) throws IllegalValueException {
        assert priority != null;
        return priority.isPresent() ? Optional.of(new Priority(priority.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDate} into an {@code Optional<StartDate>} if {@code startDate} is present.
     */
    public static Optional<TaskDate> parseStartDate(Optional<String> startDate) throws IllegalValueException {
        assert startDate != null;
        return startDate.isPresent() ? Optional.of(new StartDate(startDate.get())) : Optional.empty();
    }

    public static Optional<TaskDate> parseStartDate(Optional<String> startDate, boolean isFind)
            throws IllegalValueException {
        assert startDate != null;
        return startDate.isPresent() ? Optional.of(new StartDate(startDate.get(), isFind)) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> dueDate} into an {@code Optional<DueDate>} if {@code dueDate} is present.
     */
    public static Optional<TaskDate> parseDueDate(Optional<String> dueDate) throws IllegalValueException {
        assert dueDate != null;
        return dueDate.isPresent() ? Optional.of(new DueDate(dueDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> dueDate} into an {@code Optional<DueDate>} if {@code dueDate} is present.
     */
    public static Optional<TaskDate> parseDueDate(Optional<String> dueDate, boolean isFind)
            throws IllegalValueException {
        assert dueDate != null;
        return dueDate.isPresent() ? Optional.of(new DueDate(dueDate.get(), isFind)) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into an {@code UniqueTagList}.
     */
    public static UniqueTagList parseTags(Collection<String> tags) throws IllegalValueException {
        assert tags != null;
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }
}
