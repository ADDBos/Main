package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedCca.MISSING_CCA_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalCcas.BASKETBALL;
import static seedu.address.testutil.TypicalCcas.TRACK;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;
import seedu.address.testutil.Assert;

//@@author ericyjw
public class XmlAdaptedCcaTest {
    private static final String INVALID_CCA_NAME = "BASKETBALL(M)";
    private static final String INVALID_HEAD_NAME = "Peter-Parker";
    private static final String INVALID_VICE_HEAD_NAME = "Uncle_Ben";
    private static final String INVALID_BUDGET = "$500";
    private static final String INVALID_SPENT = "-$300";
    private static final String INVALID_OUTSTANDING = "$200";

    private static final String VALID_CCA_NAME = "BASKETBALL M";
    private static final String VALID_HEAD_NAME = "Peter Parker";
    private static final String VALID_VICE_HEAD_NAME = "Uncle Ben";
    private static final String VALID_BUDGET = "500";
    private static final String VALID_SPENT = "300";
    private static final String VALID_OUTSTANDING = "200";
    private static final List<XmlAdaptedEntry> VALID_TRANSACTION = BASKETBALL.getEntries().stream()
        .map(XmlAdaptedEntry::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validCcaDetails_returnsCca() throws Exception {
        XmlAdaptedCca cca = new XmlAdaptedCca(TRACK);
        assertEquals(TRACK, cca.toModelType());
    }

    @Test
    public void toModelType_invalidCcaName_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(INVALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = CcaName.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_nullCcaName_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(null, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT, CcaName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_invalidHeadName_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, INVALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_nullHeadName_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, null, VALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_invalidViceHeadName_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, INVALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_nullViceHeadName_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, null, VALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_invalidBudget_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, INVALID_BUDGET, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = Budget.MESSAGE_BUDGET_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_nullBudget_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, null, VALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_invalidSpent_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, INVALID_SPENT,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = Spent.MESSAGE_SPENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_nullSpent_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, null,
                VALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT, Spent.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_invalidOutstanding_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                INVALID_OUTSTANDING, VALID_TRANSACTION);
        String expectedMessage = Outstanding.MESSAGE_OUTSTANDING_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

    @Test
    public void toModelType_nullOutstanding_throwsIllegalValueException() {
        XmlAdaptedCca cca =
            new XmlAdaptedCca(VALID_CCA_NAME, VALID_HEAD_NAME, VALID_VICE_HEAD_NAME, VALID_BUDGET, VALID_SPENT,
                null, VALID_TRANSACTION);
        String expectedMessage = String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT, Outstanding.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, cca::toModelType);
    }

}
