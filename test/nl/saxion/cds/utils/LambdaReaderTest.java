package nl.saxion.cds.utils;

import nl.saxion.cds.solution.data_structures.MyArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

public class LambdaReaderTest {
    private LambdaReader<TestPerson> lambdaReader;

    @Test
    public void GivenCsvFileWithPeople_WhenReadingPeople_ConfirmCorrectListCreated(){
        MyArrayList<TestPerson> people = lambdaReader.readObjects();
        assertEquals(4, people.size());

        assertEquals("Damien", people.get(0).getName());
        assertEquals(18, people.get(0).getAge());

        assertEquals("Vlad", people.get(1).getName());
        assertEquals(20, people.get(1).getAge());

        assertEquals("Daniel", people.get(2).getName());
        assertEquals(99, people.get(2).getAge());

        assertEquals("Melisse", people.get(3).getName());
        assertEquals(22, people.get(3).getAge());
    }

    @BeforeEach
    public void setUp() {
        try {
            lambdaReader = new LambdaReader<TestPerson>(
                    "resources/test_persons.csv",
                    true,
                    TestPerson::parsePerson,
                    ","
            );
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class TestPerson {
    private final String name;
    private final int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public TestPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + ", " + age + ";";
    }

    public static TestPerson parsePerson(MyArrayList<String> data) {
        return new TestPerson(data.get(0), Integer.parseInt(data.get(1)));
    }

}
