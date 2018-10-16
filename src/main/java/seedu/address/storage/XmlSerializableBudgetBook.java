package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BudgetBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.cca.Cca;

/**
 * An Immutable BudgetBook that is serializable to XML format
 */
@XmlRootElement(name = "ccabook")
public class XmlSerializableBudgetBook {

    public static final String MESSAGE_DUPLICATE_CCA = "CCA list contains duplicate CCA(s).";

    @XmlElement
    private List<XmlAdaptedCca> ccas;

    /**
     * Creates an empty XmlSerializableBudgetBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableBudgetBook() {
        ccas = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableBudgetBook(ReadOnlyBudgetBook src) {
        this();
        ccas.addAll(src.getCcaList().stream().map(XmlAdaptedCca::new).collect(Collectors.toList()));
    }

    /**
     * Converts this budgetbook into the model's {@code BudgetBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCca}.
     */
    public BudgetBook toModelType() throws IllegalValueException {
        BudgetBook budgetBook = new BudgetBook();
        for (XmlAdaptedCca c : ccas) {
            Cca cca = c.toModelType();
            if (budgetBook.hasCca(cca)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CCA);
            }
            budgetBook.addCca(cca);
        }
        return budgetBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableBudgetBook)) {
            return false;
        }
        return ccas.equals(((XmlSerializableBudgetBook) other).ccas);
    }
}
