package seedu.address.model.transaction;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.transaction.Amount.MESSAGE_AMOUNT_CONSTRAINTS;
import static seedu.address.model.transaction.Date.MESSAGE_DATE_CONSTRAINTS;
import static seedu.address.model.transaction.Remarks.MESSAGE_REMARKS_CONSTRAINTS;

import java.util.Optional;

//@@uthor ericyjw
/**
 * Represents a Transaction Entry in the cca book.
 * Guarantees: details are present and not null, field values are validated.
 *
 * @author ericyjw
 */
public class Entry {
    public static final String MESSAGE_ENTRY_CONSTRAINTS = "Entry should contain a valid entry number, date, amount "
        + "and remarks";
    public static final String ENTRY_NUM_VALIDATION_REGEX = "[1-9][0-9]*";

    private static final String MESSAGE_ENTRY_NUM_CONSTRAINTS = "Entry number should contain only positive integers "
        + "and must not be less than 1.";

    private Integer entryNum;
    private Date date;
    private Amount amount;
    private Remarks remarks;

    /**
     * Creates a transaction {@code Entry} for a specific Cca.
     *
     * @param entryNum the entry number for this transaction entry
     * @param date the date of the transaction
     * @param amount the amount in the transaction
     * @param remarks the remarks given for this transaction
     */
    public Entry(Integer entryNum, Date date, Amount amount, Remarks remarks) {
        requireAllNonNull(entryNum, date, amount, remarks);
        this.entryNum = entryNum;
        this.date = date;
        this.amount = amount;
        this.remarks = remarks;
    }

    /**
     * Creates a transaction {@code Entry} for a specific Cca.
     * Used to convert from xml to {@code Entry} object.
     *
     * @param entryNum the entry number for this transaction entry
     * @param date the date of the transaction
     * @param amount the amount in the transaction
     * @param remarks the remarks given for this transaction
     */
    public Entry(String entryNum, String date, String amount, String remarks) {
        requireAllNonNull(entryNum, date, amount, remarks);

        try {
            int num = Integer.valueOf(entryNum);
            this.entryNum = num;
        } catch (NumberFormatException e) {
            checkArgument(false, MESSAGE_ENTRY_NUM_CONSTRAINTS);
        }

        checkArgument(Date.isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        checkArgument(Amount.isValidAmount(amount), MESSAGE_AMOUNT_CONSTRAINTS);
        checkArgument(Remarks.isValidRemark(remarks), MESSAGE_REMARKS_CONSTRAINTS);
        this.date = new Date(date);
        this.amount = new Amount(Integer.valueOf(amount));
        this.remarks = new Remarks(remarks);
    }

    public int getEntryNum() {
        return entryNum;
    }

    public Date getDate() {
        return date;
    }

    public String getDateValue() {
        return this.date.getDate();
    }

    public Amount getAmount() {
        return amount;
    }

    public int getAmountValue() {
        return this.amount.getAmount();
    }

    public Remarks getRemarks() {
        return remarks;
    }

    public String getRemarkValue() {
        return this.remarks.getRemarks();
    }

    /**
     * Update the transaction entry number of a specific transaction entry in a specific Cca.
     *
     * @param index the {@code Optional} new date to be updated
     */
    public void updateEntryNum(int index) {
        this.entryNum = index;
    }

    /**
     * Update the transaction date of a specific transaction entry in a specific Cca.
     *
     * @param toUpdate the {@code Optional} new date to be updated
     */
    public void updateDate(Optional<Date> toUpdate) {
        Date date = toUpdate.get();
        this.date = date;
    }

    /**
     * Update the transaction amount of a specific transaction entry in a specific Cca.
     *
     * @param toUpdate the {@code Optional} new amount to be updated
     */
    public void updateAmount(Optional<Amount> toUpdate) {
        Amount amount = toUpdate.get();
        this.amount = amount;
    }

    /**
     * Update the transaction remark of a specific transaction remark in a specific Cca.
     *
     * @param toUpdate the {@code Optional} new amount to be updated
     */
    public void updateRemarks(Optional<Remarks> toUpdate) {
        Remarks remarks = toUpdate.get();
        this.remarks = remarks;
    }

    /**
     * Check the if the parameters given to create an {@code Entry} object are valid.
     * Return true if all of the parameters are valid.
     *
     * @param entryNum the entry number for this transaction entry
     * @param date the date of the transaction
     * @param amount the amount in the transaction
     * @param remarks the remarks given for this transaction
     */
    public static boolean isValidEntry(String entryNum, String date, String amount, String remarks) {
        return entryNum.matches(ENTRY_NUM_VALIDATION_REGEX)
            && Date.isValidDate(date)
            && Amount.isValidAmount(amount)
            && Remarks.isValidRemark(remarks);
    }

    /**
     * Check the if the given {@code Entry} object is valid.
     * Return true if the give entry is valid.
     *
     * @param toCheck the entry to check
     */
    public static boolean isValidEntry(Entry toCheck) {
        if (!Optional.ofNullable(toCheck).isPresent()) {
            return false;
        }
        return String.valueOf(toCheck.getEntryNum()).matches(ENTRY_NUM_VALIDATION_REGEX)
            && Date.isValidDate(toCheck.getDateValue())
            && Amount.isValidAmount(String.valueOf(toCheck.getAmountValue()))
            && Remarks.isValidRemark(toCheck.getRemarkValue());
    }

    /**
     * Returns true if both transaction entries have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Entry)) {
            return false;
        }

        Entry otherEntry = (Entry) other;
        return otherEntry.entryNum.equals(this.entryNum)
            && otherEntry.date.equals(this.date)
            && otherEntry.amount.equals(this.amount)
            && otherEntry.remarks.equals(this.remarks);
    }
}
