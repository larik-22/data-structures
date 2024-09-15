package utils;

import nl.saxion.cds.utils.CsvReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import java.io.FileNotFoundException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class CsvReaderTest {
    private CsvReader csvReader;

    @Test
    public void GivenNonExistingCsvFile_WhenCreatingCsvReader_ConfirmExceptionIsThrown() {
        assertThrows(FileNotFoundException.class, () -> {
            new CsvReader("resources/non_existing_file.csv", true);
        });
    }

    @Test
    public void GivenCsvReader_WhenSettingInvalidSeparator_ConfirmExceptionIsThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            csvReader.setSeparator(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            csvReader.setSeparator("  ");
        });

    }

    @Test
    public void GivenCsvReader_WhenSkippingLineAndNextLineIsPresent_ConfirmNoExceptionIsThrown() {
        assertDoesNotThrow(() -> {
            csvReader.skipLine();
            csvReader.skipLine();
            csvReader.skipLine();
        });
    }

    @Test
    public void GivenCsvFileWithOneRecord_WhenSkippingLastLine_ConfirmExceptionIsThrown() {
        while(csvReader.readLine()){
            // Do nothing
        }

        assertThrows(IllegalStateException.class, () -> {
            csvReader.skipLine();
        });
    }

    @Test
    public void GivenCsvFile_WhenReadingDouble_ConfirmNoExceptionIsThrown() {
        while (csvReader.readLine()) {
            assertDoesNotThrow(() -> {
                csvReader.readDouble(5);
            });
        }
    }

    @Test
    public void GivenCsvFile_WhenReadingDoubleAtIndex_ConfirmExpectedDoubleIsRead() {
        while (csvReader.readLine()) {
            assertEquals(5.0855555534363, csvReader.readDouble(5));
        }
    }

    @Test
    public void GivenCsvFile_WhenReadingStringAtInvalidIndex_ConfirmExceptionIsThrown() {
        while (csvReader.readLine()) {
            assertThrows(IndexOutOfBoundsException.class, () -> {
                csvReader.readString(-1);
            });
            assertThrows(IndexOutOfBoundsException.class, () -> {
                csvReader.readString(100);
            });
        }
    }

    @Test
    public void GivenCsvFile_WhenReadingStringAtIndex_ConfirmExpectedStringIsRead() {
        while (csvReader.readLine()) {
            assertEquals("Hoorn Kersenboogerd", csvReader.readString(1));
            assertEquals("HNK", csvReader.readString(0));
            assertEquals("NL", csvReader.readString(2));
            assertEquals("stoptreinstation", csvReader.readString(3));
        }
    }

    @Test
    public void GivenCsvFile_WhenReadingIntAtIndex_ConfirmExpectedIntIsRead() {
        while (csvReader.readLine()) {
            assertEquals(2, csvReader.readInt(6));
        }
    }

    @Test
    public void GivenCsvFile_WhenReadingBooleanAtIndex_ConfirmExpectedBooleanIsRead() {
        while (csvReader.readLine()) {
            assertTrue(csvReader.readBoolean(7));
        }
    }

    @Test
    public void GivenCsvReader_WhenClosingStream_ConfirmNoExceptionIsThrown() {
        assertDoesNotThrow(() -> {
            csvReader.close();
        });
    }

    @Test
    public void GivenClosedReader_WhenReadingLine_ConfirmNoLineIsRead() {
        csvReader.close();
        assertFalse(csvReader.readLine());
        assertNull(csvReader.getColumns());
        assertNull(csvReader.getCurrentLine());
        assertThrows(IllegalStateException.class, () -> {
            csvReader.skipLine();
        });
    }

    @Test
    void test() throws FileNotFoundException {
        CsvReader reader = new CsvReader("resources/stations.csv", true);
        reader.setSeparator(",");

        HashSet<String> set = new HashSet<>();
        while (reader.readLine()){
            set.add(reader.readString(3));
        }

        set.forEach(System.out::println);
    }

    @BeforeEach
    public void setUp() {
        try {
            csvReader = new CsvReader("resources/test_stations.csv", true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        csvReader.setSeparator(",");
    }
}
