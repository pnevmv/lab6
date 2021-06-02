package server.utility;

import common.model.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;

public class FileManager {
    private String variable;

    public FileManager(String string){
        this.variable = string;
    }

    public HashMap<Integer, MusicBand> readCollection() {

        /**
         * Reads collection from a file.
         * @return Reads collection.
         */
        HashMap<Integer, MusicBand> bands = new HashMap<>();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(variable))) {
            System.out.println("File reading in progress...");
            while ((line = bufferedReader.readLine()) != null) {
                String[] fileLines = line.split(", ");
                Coordinates coordinates = new Coordinates(Double.parseDouble(fileLines[1].trim()), Long.parseLong(fileLines[2].trim()));
                Studio studio = new Studio(fileLines[6].trim());
                MusicBand band = new MusicBand(1,
                        fileLines[0].trim(), coordinates, LocalDateTime.now(), Long.parseLong(fileLines[3].trim()),
                        fileLines[4], MusicGenre.toEnum(fileLines[5].trim()), studio);
                bands.put(band.getId(), band);
            }
            bufferedReader.close();
            System.out.println("File reading completed!");
        } catch (IOException e) {
            System.out.println("File " + variable + " is not found!");
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid input data format!");
        }
        return bands;
    }

    /**
     * Writes collection to a file.
     * @param str Collection to write.
     */
    public void writeCollection(String str) {
        String file = "d:/Study/Java/lab5/outputBandsCollection.csv";
        try(FileOutputStream out = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(out))
        {
            bos.write(str.getBytes(StandardCharsets.UTF_8));
        }
        catch(IOException ex){
            System.out.println("File not found!");
        }
    }

    @Override
    public String toString() {
        return "FileManager - class for working with files";
    }
}
