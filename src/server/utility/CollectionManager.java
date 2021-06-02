package server.utility;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import common.model.MusicBand;
import common.model.Coordinates;

/**
 * Operates the collection itself.
 */
public class CollectionManager {
    private HashMap<Integer, MusicBand> bandsCollection = new HashMap<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
        this.lastInitTime = null;
        this.lastSaveTime = null;
        loadCollection();
    }

    /**
     * Loads the collection from file.
     */
    private void loadCollection() {
        bandsCollection = fileManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * @return Last initialization time or null if there wasn't initialization.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Last save time or null if there wasn't saving.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return Name of the collection's type.
     */
    public String collectionType() {
        return bandsCollection.getClass().getName();
    }

    /**
     * @return Size of the collection.
     */
    public int collectionSize() {
        return bandsCollection.size();
    }

    /**
     * @param key Key of the band.
     * @return A band by his key or null if band isn't found.
     */
    public MusicBand getByKey(Integer key) {
        return bandsCollection.get(key);
    }

    /**
     * @param id ID of the band.
     * @return A band by his ID or null if band isn't found.
     */
    public MusicBand getById(int id) {
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand value = (MusicBand) entry.getValue();
            int sId = value.getId();
            if (sId == id) {
                return value;
            }
        }
        return null;
    }

    /**
     * @param id Key of the band.
     * removes element by ID.
     */
    public void removeById(int id) {
        Integer key = -1;
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand value = (MusicBand) entry.getValue();
            int sId = value.getId();
            if (sId == id) {
                key = (Integer) entry.getKey();
            }
        }
        if (key != -1) bandsCollection.remove(key);
    }

    /**
     * @return Average if number of participants or 0 if collection is empty.
     */
    public double getAverageOfNumberOfParticipants() {
        double sum = 0;
        int count = 0;
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand value = (MusicBand) entry.getValue();
            sum += value.getNumberOfParticipants();
            count++;
        }
        return sum/count;
    }

    /**
     * @param description Start of description of element to find.
     * @return Descriptions.
     */
    public String descriptionFilter(String description) {
        String results = "";
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand value = (MusicBand) entry.getValue();
            if (value.getDescription().indexOf(description) != -1) {
                results += value;
            }
        }
        return results;
    }

    /**
     * @return All descriptions.
     */
    public String getAllDescriptions() {
        String results = "";
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand value = (MusicBand) entry.getValue();
            results += value.getDescription() + "\n";
        }
        return results;
    }

    /**
     * @param number of element
     * @param band
     * adds element to collection.
     */
    public void addToCollection(Integer number, MusicBand band) {
        bandsCollection.put(number ,band);
    }

    /**
     * @param number of element
     * removes element from collection.
     */
    public void removeFromCollection(Integer number) {
        bandsCollection.remove(number);
    }

    /**
     * @param bandToCompare
     * removes elements if it's value is lower.
     */
    public void removeLower(MusicBand bandToCompare) {
        int keys[] = new int[bandsCollection.size() + 1];
        int i = 0;
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand value = (MusicBand) entry.getValue();
            if (value.compareTo(bandToCompare) > 0) {
                keys[i] = (Integer) entry.getKey();
                i++;
            }
        }
        for (int j = 0; j <= i; j++) {
            bandsCollection.remove(keys[j]);
        }
    }

    /**
     * @param key
     * removes elements from collection if it's key is lower.
     */
    public void removeLowerKey(int key) {
        int keys[] = new int[bandsCollection.size() + 1];
        int i = 0;
        for (Map.Entry entry: bandsCollection.entrySet()) {
            int sKey = (int) entry.getKey();
            if (sKey < key) {
                keys[i] = sKey;
                i++;
            }
        }
        for (int j = 0; j <= i; j++) {
            bandsCollection.remove(keys[j]);
        }
    }

    /**
     * clears collection.
     */
    public void clearCollection() {
        bandsCollection.clear();
    }

    /**
     * save collection.
     */
    public void saveCollection() {
        fileManager.writeCollection(this.toCSV());
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * @return id for next element.
     */
    public Integer generateNextId() {
        if (bandsCollection.isEmpty()) return 1;
        return (bandsCollection.size()) + 1;
    }

    /**
     * @param number of element
     * @param band our band
     * replaces element if it's greater.
     */
    public void replaceIfGreater(Integer number, MusicBand band) {
        if (bandsCollection.get(number).compareTo(band) > 0) {
            bandsCollection.put(number, band);
        }
    }

    /**
     * @return fields of element in csv format.
     */
    public String toCSV() {
        String csv = "";
        for (Map.Entry entry: bandsCollection.entrySet()) {
            MusicBand band = (MusicBand) entry.getValue();
            Coordinates coordinates = band.getCoordinates();
            csv += band.getId() + ", " + band.getName() + ", " + coordinates.getX() + ", "
                    + coordinates.getY() + ", " + band.getCreationDate() + ", " + band.getNumberOfParticipants() + ", "
                    + band.getDescription() + ", " + band.getGenre() + ", " + band.getStudio() + "\n";
        }
        return csv;
    }

    @Override
    public String toString() {
        if (bandsCollection.isEmpty()) return "Collection is empty.";
        String info = "";
        for (Map.Entry entry: bandsCollection.entrySet()) {
            info += "Key - " + entry.getKey() + "\n";
            info += entry.getValue();
        }
        return info;
    }

}
