package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Entry;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Room("A412"), new School("Fos"),
                getTagSet("soccer")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Room("B124"), new School("SoC"),
                getTagSet("basketball", "soccer")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Room("E216"), new School("Biz"),
                getTagSet("choir")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Room("A320"), new School("Engine"),
                getTagSet("frisbee")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Room("C403"), new School("Fass"),
                getTagSet("softball")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Room("D401"), new School("SDE"),
                getTagSet("track"))
        };
    }

    private static Cca[] getSampleCcas() {
        return new Cca[]{
            new Cca(
                new CcaName("Hockey"),
                new Name("MrYanDao"),
                new Name("XiaoMing"),
                new Budget(500),
                new Spent(300),
                new Outstanding(200),
                getTransactionEntrySet(
                    new Entry("1", "13.12.2013", "-100", "Purchase of Equipments"),
                    new Entry("2", "20.06.2014", "-400", "Camp"),
                    new Entry("3", "12.07.2014", "-200", "Fund Raising")
                )
            ),
            new Cca(
                new CcaName("Floorball"),
                new Name("XiaoBitch"),
                new Name("RisLow"),
                new Budget(600),
                new Spent(500),
                new Outstanding(100),
                getTransactionEntrySet(
                    new Entry("1", "13.11.2013", "-300", "Purchase of Equipment"),
                    new Entry("2", "20.03.2014", "-200", "Competition Fund")
                )
            ),
            new Cca(
                new CcaName("Handball"),
                new Name("Steven Lim"),
                new Name("Bumble Bee"),
                new Budget(100),
                new Spent(100),
                new Outstanding(0),
                getTransactionEntrySet()
            ),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyBudgetBook getSampleBudgetBook() {
        BudgetBook sampleBb = new BudgetBook();
        for (Cca sampleCca : getSampleCcas()) {
            sampleBb.addCca(sampleCca);
        }
        return sampleBb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

    //@@author ericyjw

    /**
     * Returns a transaction entry set containing the list of transaction entries given.
     */
    private static Set<Entry> getTransactionEntrySet(Entry... entries) {
        return Arrays.stream(entries)
            .collect(Collectors.toSet());
    }
}
