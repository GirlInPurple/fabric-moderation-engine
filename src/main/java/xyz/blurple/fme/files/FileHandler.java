package xyz.blurple.fme.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.CsvWriter;
import xyz.blurple.fme.FMEInit;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    public static class Config {
        public static void CheckFiles() {
            Gson gson = new Gson();
            List<String> NeededFiles = new ArrayList<>(
                    Arrays.asList(
                            "./config/FME/fme-config.json",
                            "./config/FME/fme-db.json",
                            "./config/FME/fme-areas.json"
                    )
            );

            try {Files.createDirectories(Path.of("./config/FME"));
            } catch (IOException e) {e.printStackTrace();}

            for (String path : NeededFiles) {
                try {
                    if (!Files.exists(Path.of(path))) {
                        new FileWriter(path).write(gson.toJson(new Object()));
                        if (path.equals("./config/FME/fme-config.json")) {SetConfig();}
                    }
                } catch (IOException e) {e.printStackTrace();}
            }
        }

        private static void SetConfig() throws IOException {
            OutputStream out = Files.newOutputStream(Path.of("./config/FME/fme-config.json"));
            InputStream blankConfigInputStream = FMEInit.class.getClassLoader().getResourceAsStream("blankConfigs.json");
            if (blankConfigInputStream == null) {
                throw new IOException("Could not load default_config.yaml");
            }
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = blankConfigInputStream.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            blankConfigInputStream.close();
            out.close();
        }
    }

    public static class NOFORMAT {
        /**
         * @param filepath The .JSON file to read
         * @return Returns a String with the file contents
         * */
        public Object readTXT(Path filepath, Object returnAs) {
            try (BufferedReader br = new BufferedReader(new FileReader(filepath.toFile()))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {content.append(line).append("\n");}
                return content.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        /**
         * @param data A String
         * @param filepath The .JSON file to read
         * */
        public void writeTXT(String data, Path filepath) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filepath.toFile()))) {
                writer.write(data);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public static class JSON {
        /**
         * @param filepath The .JSON file to read
         * @return Returns a {@link JsonObject}
         * */
        public static JsonObject readJSON(Path filepath) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                return gson.fromJson(new FileReader(filepath.toFile()), type);
            } catch (Exception e) {
                e.printStackTrace();
                return new JsonObject();
            }
        }

        /**
         * @param json A {@link JsonObject}
         * @param filepath The .JSON file to read
         * */
        public static void writeJSON(JsonObject json, Path filepath) {
            try (FileWriter writer = new FileWriter(filepath.toFile())) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(json, writer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class CSV {
        /**
         * @param filepath The .CSV file to read
         * @return Returns a {@link CSVFormat}
         * */
        public CSVFormat readCSV(Path filepath) {
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
        public static void writeCSV(CSVFormat csv, Path filepath) {
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
