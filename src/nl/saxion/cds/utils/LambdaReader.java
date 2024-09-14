package nl.saxion.cds.utils;

import java.util.ArrayList;
import java.util.function.Function;

public class LambdaReader<T> extends CsvReader {
    private final Function<String[], T> creator;

    public LambdaReader(String filename, boolean skipFirstLine, Function<String[], T> creator) {
        super(filename, skipFirstLine);
        this.creator = creator;
    }

    public LambdaReader(String filename, boolean skipFirstLine, Function<String[], T> creator, String separator) {
        super(filename, skipFirstLine);
        super.setSeparator(separator);

        this.creator = creator;
    }

    public ArrayList<T> readObjects(){
        ArrayList<T> result = new ArrayList<>();
        while(readLine()){
            T el = creator.apply(getColumns());
            result.add(el);
        }

        return result;
    }
}
