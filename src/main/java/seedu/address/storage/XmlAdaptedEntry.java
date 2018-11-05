package seedu.address.storage;

import static seedu.address.commons.util.AppUtil.checkArgument;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Entry;

//@@author ericyjw
/**
 * JAXB-friendly adapted version of the Transaction Entry.
 * @author ericyjw
 */
public class XmlAdaptedEntry {

    @XmlElement
    private String entryNum;
    @XmlElement
    private String date;
    @XmlElement
    private String amount;
    @XmlElement
    private String log;

    public XmlAdaptedEntry() {}

    /**
     * Constructs a {@code XmlAdaptedEntry} with the given {@code entryNum, date, amount and log}.
     */
    public XmlAdaptedEntry(String entryNum, String date, String amount, String log) {
        this.entryNum = entryNum;
        this.date = date;
        this.amount = amount;
        this.log = log;
    }

    /**
     * Converts a given Transaction entry into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEntry(Entry source) {
        checkArgument(Entry.isValidEntry(source), Entry.MESSAGE_ENTRY_CONSTRAINTS);
        entryNum = String.valueOf(source.getEntryNum());
        date = String.valueOf(source.getDateValue());
        amount = String.valueOf(source.getAmountValue());
        log = String.valueOf(source.getRemarkValue());
    }

    /**
     * Converts this jaxb-friendly adapted Tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Entry toModelType() throws IllegalValueException {
        if (!Entry.isValidEntry(entryNum, date, amount, log)) {
            throw new IllegalValueException(Entry.MESSAGE_ENTRY_CONSTRAINTS);
        }
        return new Entry(entryNum, date, amount, log);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEntry)) {
            return false;
        }

        XmlAdaptedEntry otherEntry = (XmlAdaptedEntry) other;
        return entryNum.equals(otherEntry.entryNum)
            && date.equals(otherEntry.date)
            && amount.equals(otherEntry.amount)
            && log.equals(otherEntry.log);
    }
}

