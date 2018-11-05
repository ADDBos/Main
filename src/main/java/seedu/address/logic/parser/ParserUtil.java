package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImageCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.email.Content;
import seedu.address.model.email.Subject;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Remarks;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_DATE_CONSTRAINTS =
        "Date must be a non-negative integer and not greater than 31.";
    public static final String MESSAGE_HOUR_CONSTRAINTS =
        "Hour must be a non-negative integer and not greater than 23.";
    public static final String MESSAGE_MINUTE_CONSTRAINTS =
        "Minute must be a non-negative integer and not greater than 59";
    public static final String MESSAGE_TITLE_CONSTRAINTS =
        "Title must not be empty";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    //@@author EatOrBeEaten

    /**
     * Parses {@code String oneBasedIndexes} into a {@code Set<Index>}.
     */
    public static Set<Index> parseIndexes(String oneBasedIndexes) throws ParseException {
        requireNonNull(oneBasedIndexes);
        final String[] indexArray = oneBasedIndexes.split(" ");
        final Set<Index> indexSet = new HashSet<>();
        for (String index : indexArray) {
            indexSet.add(parseIndex(index));
        }
        return indexSet;
    }
    //@@author

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String room} into a {@code Room}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code room} is invalid.
     */
    //@@author javenseow
    public static Room parseRoom(String room) throws ParseException {
        requireNonNull(room);
        String trimmedRoom = room.trim();
        if (!Room.isValidRoom(trimmedRoom)) {
            throw new ParseException(Room.MESSAGE_ROOM_CONSTRAINTS);
        }
        return new Room(trimmedRoom);
    }

    /**
     * Parses a {@code String school} into a {@code School}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code school} is invalid.
     */
    public static School parseSchool(String school) throws ParseException {
        requireNonNull(school);
        String trimmedSchool = school.trim();
        if (!School.isValidSchool(trimmedSchool)) {
            throw new ParseException(School.MESSAGE_SCHOOL_CONSTRAINTS);
        }
        return new School(trimmedSchool);
    }
    //@@author

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if ("".equals(trimmedTag) || !Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    //@@author kengwoon

    /**
     * Parses a {@code String file} into a {@code File}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code file} is invalid.
     */
    public static File parseFile(String file) throws ParseException {
        requireNonNull(file);
        String trimmedFile = file.trim();
        if (!trimmedFile.contains(".xml")) {
            throw new ParseException(ImportCommand.MESSAGE_USAGE);
        }
        return new File(trimmedFile);
    }

    /**
     * Parses {@code String path} and {@code String filename} into a {@code Path}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code path} or {@code filename} is invalid.
     */
    public static Path parsePath(String path, String filename) throws ParseException {
        requireNonNull(path);
        requireNonNull(filename);
        String trimmedPath = path.trim();
        String trimmedFilename = filename.trim();
        if (!trimmedFilename.contains(".xml")) {
            throw new ParseException(ExportCommand.MESSAGE_USAGE);
        }
        return new File(trimmedPath + "/" + trimmedFilename).toPath();
    }

    //@@author javenseow
    /**
     * Parses a {@code String file} into a {@code File}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static File parseImage(String file) throws ParseException {
        requireNonNull(file);
        String trimmedFile = file.trim();
        if (!trimmedFile.contains(".jpg") && !trimmedFile.contains(".png")) {
            throw new ParseException(ImageCommand.MESSAGE_USAGE);
        }
        return new File(trimmedFile);
    }

    //@@author EatOrBeEaten

    /**
     * Parses a {@code String content} into an {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws ParseException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new ParseException(Content.MESSAGE_CONTENT_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }

    /**
     * Parses a {@code String subject} into an {@code Subject}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code subject} is invalid.
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!Subject.isValidSubject(trimmedSubject)) {
            throw new ParseException(Subject.MESSAGE_SUBJECT_CONSTRAINTS);
        }
        return new Subject(trimmedSubject);
    }

    //@@author GilgameshTC

    /**
     * Parses a {@code String month} into a {@code Month}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code month} is invalid.
     */
    public static Month parseMonth(String month) throws ParseException {
        requireNonNull(month);
        String trimmedMonth = month.trim();
        // Transform month to upper case
        trimmedMonth = trimmedMonth.toUpperCase();
        if (!Month.isValidMonthRegex(trimmedMonth) || !Month.isValidMonth(trimmedMonth)) {
            throw new ParseException(Month.MESSAGE_MONTH_CONSTRAINTS);
        }
        return new Month(trimmedMonth);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.trim();
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_YEAR_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    //@@author ericyjw
    /**
     * Parses a {@code String Budget} into a {@code Budget}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code budget} is invalid.
     */
    public static Budget parseBudget(String budget) throws ParseException {
        requireNonNull(budget);
        String trimmedBudget = budget.trim();
        if (!Budget.isValidBudget(trimmedBudget)) {
            throw new ParseException(Budget.MESSAGE_BUDGET_CONSTRAINTS);
        }
        return new Budget(Integer.parseInt(trimmedBudget));
    }

    /**
     * Parses a {@code String ccaName} into a {@code CcaName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code ccaName} is invalid.
     */
    public static CcaName parseCcaName(String ccaName) throws ParseException {
        requireNonNull(ccaName);
        String trimmedCcaName = ccaName.trim();
        if (!CcaName.isValidCcaName(trimmedCcaName)) {
            throw new ParseException(CcaName.MESSAGE_NAME_CONSTRAINTS);
        }
        return new CcaName(trimmedCcaName);
    }

    /**
     * Parses a {@code String Spent} into a {@code Spent}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Spent} is invalid.
     */
    public static Spent parseSpent(String spent) throws ParseException {
        requireNonNull(spent);
        String trimmedSpent = spent.trim();
        if (!Spent.isValidSpent(trimmedSpent)) {
            throw new ParseException(Spent.MESSAGE_SPENT_CONSTRAINTS);
        }
        return new Spent(Integer.parseInt(trimmedSpent));
    }

    /**
     * Parses a {@code String outstanding} into a {@code Outstanding}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Outstanding} is invalid.
     */
    public static Outstanding parseOutstanding(String outstanding) throws ParseException {
        requireNonNull(outstanding);
        String trimmedOutstanding = outstanding.trim();
        if (!Outstanding.isValidOutstanding(trimmedOutstanding)) {
            throw new ParseException(Outstanding.MESSAGE_OUTSTANDING_CONSTRAINTS);
        }
        return new Outstanding(Integer.parseInt(trimmedOutstanding));
    }

    /**
     * Parses a {@code String entryNum} into a {@code Integer}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code entryNum} is invalid.
     */
    public static Integer parseEntryNum(String entryNum) throws ParseException {
        requireNonNull(entryNum);
        try {
            int num = Integer.valueOf(entryNum);
            if (entryNum == null || num < 1) {
                throw new ParseException("Entry number should only be positive integer that is more than 0!");
            }
        } catch (NumberFormatException e) {
            throw new ParseException("Entry number should only be positive integer that is more than 0!");
        }

        return Integer.parseInt(entryNum);
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseEntryDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String amount} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        return new Amount(Integer.valueOf(trimmedAmount));
    }

    /**
     * Parses a {@code String remarks} into a {@code Remarks}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code remarks} is invalid.
     */
    public static Remarks parseRemarks(String remarks) throws ParseException {
        requireNonNull(remarks);
        if (!Remarks.isValidRemark(remarks)) {
            throw new ParseException(Remarks.MESSAGE_REMARKS_CONSTRAINTS);
        }
        return new Remarks(remarks);
    }

    //@@author
    /**
     * Parses a {@code String date} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static int parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        int dateInt = Integer.parseInt(trimmedDate);
        if (dateInt < 0 || dateInt > 31) {
            throw new ParseException(MESSAGE_DATE_CONSTRAINTS);
        }
        return dateInt;
    }

    /**
     * Parses a {@code String hour} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code hour} is invalid.
     */
    public static int parseHour(String hour) throws ParseException {
        requireNonNull(hour);
        String trimmedHour = hour.trim();
        int hourInt = Integer.parseInt(trimmedHour);
        if (hourInt < 0 || hourInt > 23) {
            throw new ParseException(MESSAGE_HOUR_CONSTRAINTS);
        }
        return hourInt;
    }

    /**
     * Parses a {@code String minute} into a {@code int}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code minute} is invalid.
     */
    public static int parseMinute(String minute) throws ParseException {
        requireNonNull(minute);
        String trimmedMinute = minute.trim();
        int minuteInt = Integer.parseInt(trimmedMinute);
        if (minuteInt < 0 || minuteInt > 59) {
            throw new ParseException(MESSAGE_MINUTE_CONSTRAINTS);
        }
        return minuteInt;
    }

    /**
     * Parses a {@code String title} making sure it's not empty.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static String parseTitle(String title) throws ParseException {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        if (trimmedTitle.isEmpty()) {
            throw new ParseException(MESSAGE_TITLE_CONSTRAINTS);
        }
        return trimmedTitle;
    }

}
