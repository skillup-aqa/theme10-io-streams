package ua.skillup.phonedirectory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PhoneDirectory {
    private final Map<String, String> phoneDirectory = new HashMap<>();

    /**
     * Adds a new entry to the phone directory.
     *
     * @param name  the name of the person
     * @param phone the phone number of the person
     * @throws IllegalArgumentException if the phone number already exists in the phone directory
     */
    public void addEntry(String name, String phone) {
        if(phoneDirectory.containsKey(phone)) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        phoneDirectory.put(phone, name);
    }

    /**
     * Returns the phone number of the person with the given name.
     *
     * @param name the name of the person
     * @return the list of phone numbers and names matching the given name
     * or an empty list if the person with the given name does not exist in the phone directory
     */
    public List<String> searchByName(String name) {
        return phoneDirectory.entrySet().stream()
                .filter(entry -> entry.getValue().contains(name))
                .map(entry -> String.format("%s: %s", entry.getValue(), entry.getKey()))
                .collect(Collectors.toList());
    }

    /**
     * Returns the name of the person with the given phone number.
     *
     * @param phone the phone number of the person
     * @return the name of the person or null if the person with the given phone number does not exist in the phone directory
     */
    public String getName(String phone) {
        return phoneDirectory.entrySet().stream()
                .filter(entry -> entry.getKey().equals(phone))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes the entry with the given name from the phone directory.
     *
     * @param phone the phone number of the person
     * @return true if the entry was removed, false if the entry with the given name does not exist in the phone directory
     */

    public boolean removeEntry(String phone) {
        return phoneDirectory.remove(phone) != null;
    }

    /**
     * Updates the name of the person with the given phone number.
     *
     * @param phone the phone number of the person
     * @param name  the new name of the person
     */
    public void updateName(String phone, String name) {
        phoneDirectory.put(phone, name);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("=== Phone Directory ===\n\n");
        phoneDirectory.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> builder.append(e.getValue()).append(": ").append(e.getKey()).append("\n"));
        return builder.toString();
    }
}