package xyz.blurple.fme.files;

import java.util.List;

public class CSVFormat {
    List<String> Headers;
    List<List<String>> Rows;

    /**
     * A class that roughly recreates the .CSV file format using nested lists.
     * @param CSVHeaders A list of headers for the data
     * @param CSVRows A list of lists of data.
     * */
    public CSVFormat(List<String> CSVHeaders, List<List<String>> CSVRows) {
        this.Headers = CSVHeaders;
        this.Rows = CSVRows;
    }
}
