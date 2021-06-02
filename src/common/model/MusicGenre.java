package common.model;

import java.io.Serializable;

/**
 * Enumeration with music genre constants.
 */
public enum MusicGenre implements Serializable {
    PROGRESSIVE_ROCK,
    SOUL,
    POST_ROCK,
    PUNK_ROCK;

    /**
     * @return List of names of music genres.
     */
    public static String nameList() {
        String nameList = "";
        for (MusicGenre category : values()) {
            nameList += category.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }

    /**
     * @param argument of enum
     * @return Name of music genre.
     */
    public static MusicGenre toEnum(String argument) {
        switch (argument) {
            case "SOUL":
                return MusicGenre.SOUL;
            case "POST_ROCK":
                return MusicGenre.POST_ROCK;
            case "PROGRESSIVE_ROCK":
                return MusicGenre.PROGRESSIVE_ROCK;
            case "PUNK_ROCK":
                return MusicGenre.PUNK_ROCK;
            default:
                throw new IllegalArgumentException();
        }
    }
}