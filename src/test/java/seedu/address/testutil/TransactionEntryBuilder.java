package seedu.address.testutil;

import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;

//@@author ericyjw

/**
 * A utility class to help with building transaction {@code Entry} objects.
 */
public class TransactionEntryBuilder {
    public static final Integer DEFAULT_ENTRY_NUM = 1;
    public static final String DEFAULT_DATE = "06.04.2018";
    public static final Integer DEFAULT_AMOUNT = -200;
    public static final String DEFAULT_REMARKS = "Purchase of Equipment";

    private Integer entryNum;
    private Date date;
    private Amount amount;
    private Remarks remarks;

    /**
     * Construct a transaction entry with the default values.
     */
    public TransactionEntryBuilder() {
        this.entryNum = DEFAULT_ENTRY_NUM;
        this.date = new Date(DEFAULT_DATE);
        this.amount = new Amount(DEFAULT_AMOUNT);
        this.remarks = new Remarks(DEFAULT_REMARKS);
    }

    /**
     * Initializes the TransactionEntryBuilder with the data of {@code entryToCopy}.
     */
    public TransactionEntryBuilder(Entry entryToCopy) {
        this.entryNum = entryToCopy.getEntryNum();
        this.date = entryToCopy.getDate();
        this.amount = entryToCopy.getAmount();
        this.remarks = entryToCopy.getRemarks();
    }

    /**
     * Sets the {@code entryNum} of the {@code Entry} that we are building.
     */
    public TransactionEntryBuilder withEntryNum(Integer entryNum) {
        this.entryNum = entryNum;
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Entry} that we are building.
     */
    public TransactionEntryBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code Entry} that we are building.
     */
    public TransactionEntryBuilder withAmount(Integer amount) {
        this.amount = new Amount(amount);
        return this;
    }

    /**
     * Sets the {@code Remarks} of the {@code Entry} that we are building.
     */
    public TransactionEntryBuilder withRemarks(String remarks) {
        this.remarks = new Remarks(remarks);
        return this;
    }

    public Entry build() {
        return new Entry(entryNum, date, amount, remarks);
    }
}
