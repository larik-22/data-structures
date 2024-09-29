package nl.saxion.cds.data_structures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyEntryTest {
    private Entry<String, Integer> entry;

    @Test
    public void GivenEntry_WhenGettingKey_ConfirmCorrectKeyReturned() {
        assertEquals("key", entry.getKey());
    }

    @Test
    public void GivenEntry_WhenGettingValue_ConfirmCorrectValueReturned() {
        assertEquals(1, entry.getValue());
    }

    @Test
    public void GivenEntry_WhenComparingToSameObject_ConfirmEqualsMethodWorks() {
        assertTrue(entry.equals(entry));
    }

    @Test
    public void GivenEntry_WhenComparingToNull_ConfirmEqualsMethodReturnsFalse() {
        assertFalse(entry.equals(null));
    }

    @Test
    public void GivenEntry_WhenComparingToDifferentClass_ConfirmEqualsMethodReturnsFalse() {
        assertFalse(entry.equals("some string"));
    }

    @Test
    public void GivenEntry_WhenComparingToEntryWithDifferentKey_ConfirmEqualsMethodReturnsFalse() {
        Entry<String, Integer> differentEntry = new Entry<>("differentKey", 1);
        assertFalse(entry.equals(differentEntry));
    }

    @Test
    public void GivenEntry_WhenComparingToEntryWithSameKey_ConfirmEqualsMethodReturnsTrue() {
        Entry<String, Integer> sameKeyEntry = new Entry<>("key", 2);
        assertTrue(entry.equals(sameKeyEntry));
    }

    @BeforeEach
    public void setUp() {
        entry = new Entry<>("key", 1);
    }
}