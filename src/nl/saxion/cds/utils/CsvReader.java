package nl.saxion.cds.utils;

import java.io.*;

public class CsvReader {
    private String currentLine;
    private String[] columns;
    private String separator;
    private BufferedReader bufferedReader;

    public String[] getColumns() {
        return columns;
    }

    public String getCurrentLine() {
        return currentLine;
    }

    public CsvReader(String filename, boolean skipFirstLine) {
        File file = new File(filename);
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
        this.currentLine = null;
        this.columns = null;
        this.separator = ";";

        if(skipFirstLine) skipLine();
    }
    public void setSeparator(String separator) {
        if (separator == null || separator.isBlank()) throw new IllegalArgumentException("Invalid separator");
        this.separator = separator;
    }

    public void skipLine() {
        try {
            if (bufferedReader != null && bufferedReader.ready()) {
                bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readLine() {
        try {
            if (bufferedReader != null && (this.currentLine = bufferedReader.readLine()) != null) {
                this.columns = currentLine.split(separator);
                return true;
            } else {
                this.currentLine = null;
                this.columns = null;
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String readString(int index){
        if(index > columns.length) throw new IndexOutOfBoundsException("Invalid index");
        return columns[index];
    }

    public int readInt(int index, int defaultValue){
        String s = readString(index);
        int answer;

        try {
            answer = Integer.parseInt(s);
        } catch (NumberFormatException err){
            answer = defaultValue;
        }

        return answer;
    }

    public double readDouble(int index, double defaultValue){
        String s = readString(index);
        double answer;

        try {
            answer = Double.parseDouble(s);
        } catch (Exception err){
            answer = defaultValue;
        }

        return answer;
    }

    public boolean readBoolean(int index){
        String s = readString(index);
        return Boolean.parseBoolean(s);
    }

    public void close() {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
