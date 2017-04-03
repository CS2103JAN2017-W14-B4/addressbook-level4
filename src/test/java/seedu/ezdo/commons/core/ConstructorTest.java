package seedu.ezdo.commons.core;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import seedu.ezdo.commons.util.CollectionUtil;
import seedu.ezdo.commons.util.ConfigUtil;
import seedu.ezdo.commons.util.FxViewUtil;
import seedu.ezdo.commons.util.MultipleIndexCommandUtil;
import seedu.ezdo.commons.util.UrlUtil;
import seedu.ezdo.logic.parser.CliSyntax;
import seedu.ezdo.model.util.SampleDataUtil;
//@@author A0139248X
public class ConstructorTest {

    @Test
    public void construct_notNull() {
        assertNotNull(new Messages());
        assertNotNull(new SampleDataUtil());
        assertNotNull(new ConfigUtil());
        assertNotNull(new UrlUtil());
        assertNotNull(new CliSyntax());
        assertNotNull(new CollectionUtil());
        assertNotNull(new LogsCenter());
        assertNotNull(new FxViewUtil());
        assertNotNull(new MultipleIndexCommandUtil());
    }
}
