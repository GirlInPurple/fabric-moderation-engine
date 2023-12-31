package xyz.blurple.fme;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.CsvWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public static void startupFiles() {
        Gson gson = new Gson();

        try {Files.createDirectories(Path.of("./config/FME"));}
        catch (IOException e) {e.printStackTrace();}

        try {
            if (!Files.exists(Path.of("./config/FME/fme-config.json"))) {
                new FileWriter("./config/FME/fme-config.json").write(gson.toJson(new Object()));
            }
        } catch (IOException e) {e.printStackTrace();}

        try {
            if (!Files.exists(Path.of("./config/FME/fme-db.json"))) {
                new FileWriter("./config/FME/fme-db.json").write(gson.toJson(new Object()));
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    public static class JSON {
        public static JsonObject readJson(Path filepath) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                return gson.fromJson(new FileReader(filepath.toFile()), type);
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonObject();
            }
        }

        public static void writeJson(JsonObject json, Path filepath) {
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(json, writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class CSV {

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

        /**
         * @param filepath The .CSV file to read
         * @return Returns a {@link CSVFormat}
         * */
        public CSVFormat readCsv(Path filepath) {
            try (BufferedReader br = new BufferedReader(new FileReader(filepath.toFile()))) {
                String line;
                int lineIndex = 0;
                CSVFormat csv = new CSVFormat(new ArrayList<>(), new ArrayList<>());
                while ((line = br.readLine()) != null) {
                    List<String> values = List.of(line.split(","));
                    if (lineIndex == 0) {csv.Headers = values;}
                    else {csv.Rows.add(values);}
                    lineIndex += 1;
                }
                return csv;
            } catch (Exception e) {
                e.printStackTrace();
                return new CSVFormat(new ArrayList<>(), new ArrayList<>());
            }
        }

        /**
         * @param filepath The .CSV file to write to
         * @param csv The CSV data to write to the file
         * */
        public static void writeCsv(CSVFormat csv, Path filepath) {
            try (Writer writer = new FileWriter(filepath.toFile())) {
                CsvWriter.Header header = CsvWriter.makeHeader();
                for (String FileHeaders : csv.Headers) {header.addColumn(FileHeaders);}
                CsvWriter csvWriter = header.startBody(writer);
                for (List<String> FileData : csv.Rows) {csvWriter.printRow(FileData);}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
