package seedu.address.storage;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Entry;

/**
 * JAXB-friendly version of the CCA.
 *
 * @author ericyjw
 */
public class XmlAdaptedCca {

    public static final String MISSING_CCA_FIELD_MESSAGE_FORMAT = "CCA's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String head;
    @XmlElement(required = true)
    private String viceHead;
    @XmlElement(required = true)
    private String budget;
    @XmlElement(required = true)
    private String spent;
    @XmlElement(required = true)
    private String outstanding;

    @XmlElement
    private List<XmlAdaptedEntry> transaction = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCca.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCca() {
    }

    /**
     * Constructs an {@code XmlAdaptedCca} with the given CCA details.
     */
    public XmlAdaptedCca(String name, String head, String viceHead, String budget, String spent, String outstanding,
                         List<XmlAdaptedEntry> transaction) {
        this.name = name;
        this.head = head;
        this.viceHead = viceHead;
        this.budget = budget;
        this.spent = spent;
        this.outstanding = outstanding;
        this.transaction = transaction;
    }

    /**
     * Converts a given CCA into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCca
     */
    public XmlAdaptedCca(Cca source) {
        name = source.getCcaName();
        budget = String.valueOf(source.getBudgetAmount());

        head = source.getHeadName();
        viceHead = source.getViceHeadName();
        budget = String.valueOf(source.getBudgetAmount());
        spent = String.valueOf(source.getSpentAmount());
        outstanding = String.valueOf(source.getOutstandingAmount());
        transaction = source.getEntries().stream()
            .map(XmlAdaptedEntry::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted cca object into the model's CCA object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted cca
     */
    public Cca toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT,
                CcaName.class.getSimpleName()));
        }
        if (!CcaName.isValidCcaName(name)) {
            throw new IllegalValueException(CcaName.MESSAGE_NAME_CONSTRAINTS);
        }
        final CcaName modelName = new CcaName(name);

        if (head == null) {
            throw new IllegalValueException(String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName()));
        }
        if (!Name.isValidName(head)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        final Name modelHeadName = new Name(head);

        if (viceHead == null) {
            throw new IllegalValueException(String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName()));
        }
        if (!Name.isValidName(viceHead)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        final Name modelViceHeadName = new Name(viceHead);

        if (budget == null) {
            throw new IllegalValueException(String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT,
                Budget.class.getSimpleName()));
        }
        if (!Budget.isValidBudget(budget)) {
            throw new IllegalValueException(Budget.MESSAGE_BUDGET_CONSTRAINTS);
        }
        final Budget modelBudget = new Budget(Integer.parseInt(budget));

        if (spent == null) {
            throw new IllegalValueException(String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT,
                Spent.class.getSimpleName()));
        }
        if (!Spent.isValidSpent(spent)) {
            throw new IllegalValueException(Spent.MESSAGE_SPENT_CONSTRAINTS);
        }

        final Spent modelSpent = new Spent(Integer.parseInt(spent));

        if (outstanding == null) {
            throw new IllegalValueException(String.format(MISSING_CCA_FIELD_MESSAGE_FORMAT,
                Outstanding.class.getSimpleName()));
        }
        if (!Outstanding.isValidOutstanding(outstanding)) {
            throw new IllegalValueException(Outstanding.MESSAGE_OUTSTANDING_CONSTRAINTS);
        }

        final Outstanding modelOutstanding = new Outstanding(Integer.parseInt(outstanding));

        final List<Entry> transactionEntries = new ArrayList<>();
        for (XmlAdaptedEntry entry : transaction) {
            transactionEntries.add(entry.toModelType());
        }

        final Set<Entry> modelTransaction = new LinkedHashSet<>(transactionEntries);

        return new Cca(modelName, modelHeadName, modelViceHeadName, modelBudget, modelSpent, modelOutstanding,
            modelTransaction);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCca)) {
            return false;
        }

        XmlAdaptedCca otherCca = (XmlAdaptedCca) other;
        return Objects.equals(name, otherCca.name)
            && Objects.equals(head, otherCca.head)
            && Objects.equals(viceHead, otherCca.viceHead)
            && Objects.equals(budget, otherCca.budget)
            && Objects.equals(spent, otherCca.spent)
            && Objects.equals(outstanding, otherCca.outstanding)
            && Objects.equals(transaction, otherCca.transaction);
    }
}
