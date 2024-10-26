package nl.saxion.cds.utils;

import nl.saxion.cds.solution.data_structures.MyArrayList;

import java.io.*;


public class CsvReader {
    private String currentLine;
    private MyArrayList<String> columns;
    private String separator;
    private BufferedReader bufferedReader;

    public MyArrayList<String> getColumns() {
        return columns;
    }

    public String getCurrentLine() {
        return currentLine;
    }

    /**
     * Creates a new CsvReader
     * @param filename the filename of the CSV file
     * @param skipFirstLine true if the first line should be skipped
     */
    public CsvReader(String filename, boolean skipFirstLine) throws FileNotFoundException {
        File file = new File(filename);
        bufferedReader = new BufferedReader(new FileReader(file));

        this.currentLine = null;
        this.columns = new MyArrayList<>();
        this.separator = ";";

        if(skipFirstLine) skipLine();
    }

    /**
     * Sets the separator for the CSV file
     * @param separator the separator
     */
    public void setSeparator(String separator) {
        if (separator == null || separator.isBlank()) throw new IllegalArgumentException("Invalid separator");
        this.separator = separator;
    }

    /**
     * Skips a line in the file
     */
    public void skipLine() {
        if(currentLine == null && columns == null) throw new IllegalStateException("No line to skip");

        readLine();
    }

    /**
     * Reads a line from the file
     * @return true if a line was read, false if the end of the file was reached
     */
    public boolean readLine() {
        try {
            if (bufferedReader != null && bufferedReader.ready()) {
                currentLine = bufferedReader.readLine();
                String[] items = currentLine.split(separator);
                columns.clear();

                for (String item:items){
                    columns.addLast(item);
                }

                return true;
            } else {
                this.currentLine = null;
                this.columns = null;
                close();

                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Reads a string from the specified index
     * @param index the index of the column
     * @return the string value
     */
    public String readString(int index){
        if(index > columns.size()) throw new IndexOutOfBoundsException("Invalid index");
        return columns.get(index);
    }

    /**
     * Reads an integer from the specified index, default exception is thrown if the value is not an integer
     * @param index the index of the column
     * @return the integer value
     */
    public int readInt(int index){
        String s = readString(index);
        return Integer.parseInt(s);
    }

    /**
     * Reads a double from the specified index, default exception is thrown if the value is not a double
     * @param index the index of the column
     * @return the double value
     */
    public double readDouble(int index){
        String s = readString(index);
        return Double.parseDouble(s);
    }

    /**
     * Reads a boolean from the specified index, default exception is thrown if the value is not a boolean
     * @param index the index of the column
     * @return the boolean value
     */
    public boolean readBoolean(int index){
        String s = readString(index);
        return Boolean.parseBoolean(s);
    }

    /**
     * Closes the reader
     */
    public void close() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
                bufferedReader = null;
            }
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }
}
