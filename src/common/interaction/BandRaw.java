package common.interaction;

import common.model.*;

import java.io.Serializable;

/**
 * Class for get Bands value.
 */
public class BandRaw implements Serializable {
    private String name;
    private Coordinates coordinates;
    private Long numberOfParticipants;
    private String description;
    private MusicGenre musicGenre;
    private Studio studio;

    public BandRaw(String name, Coordinates coordinates, Long numberOfParticipants, String description, MusicGenre musicGenre, Studio studio) {
        this.name = name;
        this.coordinates = coordinates;
        this.numberOfParticipants = numberOfParticipants;
        this.description = description;
        this.musicGenre = musicGenre;
        this.studio = studio;
    }

    /**
     * @return Name of the band.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Coordinates of the band.
     */
    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * @return Health of the band.
     */
    public Long getNumberOfParticipants() {
        return this.numberOfParticipants;
    }

    /**
     * @return Category of the band.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return Music Genre of the band.
     */
    public MusicGenre getMusicGenre() {
        return this.musicGenre;
    }

    /**
     * @return Studio of the band.
     */
    public Studio getStudio() {
        return this.studio;
    }

    @Override
    public String toString() {
        String info = "";
        info += "\"Raw\" band";
        info += "\n Name: " + this.name;
        info += "\n Coordinates: " + this.coordinates;
        info += "\n Number of participants: " + this.numberOfParticipants;
        info += "\n Description: " + this.description;
        info += "\n Music genre: " + this.musicGenre;
        info += "\n Studio: " + this.studio;
        return info;
    }

    @Override
    public int hashCode() {
        return name.hashCode() + coordinates.hashCode() + numberOfParticipants.hashCode() + description.hashCode() + musicGenre.hashCode() +
                studio.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof MusicBand) {
            MusicBand bandObj = (MusicBand) obj;
            return name.equals(bandObj.getName()) && coordinates.equals(bandObj.getCoordinates()) &&
                    (numberOfParticipants == bandObj.getNumberOfParticipants()) && (description == bandObj.getDescription()) &&
                    (musicGenre.equals(bandObj.getGenre())) && (studio.equals(bandObj.getStudio()));
        }
        return false;
    }
}
