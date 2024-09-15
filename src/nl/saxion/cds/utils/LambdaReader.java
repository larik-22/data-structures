package nl.saxion.cds.utils;

import nl.saxion.cds.data_structures.MyArrayList;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

public class LambdaReader<T> extends CsvReader {
    private final Function<MyArrayList<String>, T> creator;

    public LambdaReader(String filename, boolean skipFirstLine, Function<MyArrayList<String>, T> creator) throws FileNotFoundException {
        super(filename, skipFirstLine);
        this.creator = creator;
    }

    public LambdaReader(String filename, boolean skipFirstLine, Function<MyArrayList<String>, T> creator, String separator) throws FileNotFoundException {
        super(filename, skipFirstLine);
        super.setSeparator(separator);

        this.creator = creator;
    }

    public MyArrayList<T> readObjects() {
        MyArrayList<T> result = new MyArrayList<>();
        while(readLine()){
            T el = creator.apply(getColumns());
            result.addLast(el);
        }

        return result;
    }
}
