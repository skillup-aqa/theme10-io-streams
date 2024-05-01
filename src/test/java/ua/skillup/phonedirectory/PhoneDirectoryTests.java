package ua.skillup.phonedirectory;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;

public class PhoneDirectoryTests {
    PhoneDirectory phoneDirectory;

    @BeforeMethod
    public void setUp() {
        phoneDirectory = new PhoneDirectory();
    }

    @Test
    public void testAddEntry() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        assertEquals(phoneDirectory.getName("1234567890"), "John Doe");
    }

    @Test
    public void testAddEntryWithExistingPhone() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        assertThrows(IllegalArgumentException.class, () -> phoneDirectory.addEntry("Jane Doe", "1234567890"));
    }

    @Test
    public void testGetPhone() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        phoneDirectory.addEntry("Jane Doe", "0987654321");

        List<String> results = phoneDirectory.searchByName("John Doe");
        assertNotNull(results);
        assertEquals(results.size(), 1);

        String result = results.get(0);
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("1234567890"));

        assertFalse(result.contains("Jane Doe"));
        assertFalse(result.contains("0987654321"));
    }

    @Test
    public void testGetMultiplePhones() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        phoneDirectory.addEntry("Jane Doe", "0987654321");

        List<String> results = phoneDirectory.searchByName("Doe");
        assertNotNull(results);
        assertEquals(results.size(), 2);

        assertTrue(results.get(0).contains("John Doe") || results.get(1).contains("John Doe"));
        assertTrue(results.get(0).contains("1234567890") || results.get(1).contains("1234567890"));

        assertTrue(results.get(0).contains("Jane Doe") || results.get(1).contains("Jane Doe"));
        assertTrue(results.get(0).contains("0987654321") || results.get(1).contains("0987654321"));
    }

    @Test
    public void testGetPhoneForMissingName() {
        List<String> results = phoneDirectory.searchByName("John Doe");
        assertNotNull(results);
        assertEquals(phoneDirectory.searchByName("John Doe").size(), 0);
    }

    @Test
    public void testGetName() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        assertEquals(phoneDirectory.getName("1234567890"), "John Doe");
    }

    @Test
    public void testGetNameForMissingPhone() {
        assertNull(phoneDirectory.getName("1234567890"));
    }

    @Test
    public void testRemoveEntry() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        assertTrue(phoneDirectory.removeEntry("1234567890"));
        assertNull(phoneDirectory.getName("1234567890"));
    }

    @Test
    public void testRemoveEntryForMissingPhone() {
        assertFalse(phoneDirectory.removeEntry("1234567890"));
        assertNull(phoneDirectory.getName("1234567890"));
    }

    @Test
    public void testUpdateName() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        phoneDirectory.updateName("1234567890", "Jane Doe");
        assertEquals(phoneDirectory.getName("1234567890"), "Jane Doe");
    }

    @Test
    public void testToString() {
        phoneDirectory.addEntry("John Doe", "1234567890");
        phoneDirectory.addEntry("Jane Doe", "0987654321");
        Pattern pattern = Pattern.compile("Jane Doe.*0987654321.*John Doe.*1234567890", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(phoneDirectory.toString());
        assertTrue(matcher.find());
    }
}
