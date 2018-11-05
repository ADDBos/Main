package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final ProfilePicture profilePicture;
    private final School school;
    private final Room room;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * All fields except {@code profilePicture} are present and not null.
     */
    public Person(Name name, Phone phone, Email email, Room room, School school, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, room, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.room = room;
        this.school = school;
        this.profilePicture = null;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Room room, School school, ProfilePicture profilePicture,
                  Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.room = room;
        this.school = school;
        this.profilePicture = profilePicture;
        this.tags.addAll(tags);
    }

    // ToDo: testing
    public Person(Name name) {
        this.name = name;
        this.phone = null;
        this.email = null;
        this.room = null;
        this.school = null;
        this.profilePicture = null;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Room getRoom() {
        return room;
    }

    public School getSchool() {
        return school;
    }

    public ProfilePicture getProfilePicture() {
        return profilePicture;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    //@@author javenseow
    /**
     * Returns an immutable set containing tags, school and room, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getFields() {
        Set<Tag> fields = new HashSet<>();
        fields.addAll(tags);
        fields.add(new Tag(this.school.value));
        fields.add(new Tag(this.room.value));
        return Collections.unmodifiableSet(fields);
    }

    //@@author
    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && otherPerson.getName().equals(getName())
            && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
    }

    //@@author ericyjw
    /**
     * Returns true if both persons of the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePersonName(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
            && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getRoom().equals(getRoom())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, room, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Room: ")
                .append(getRoom())
                .append(" School: ")
                .append(getSchool())
                .append(" Profile Picture: ")
                .append(getProfilePicture())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
