package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents an album in the application. An album is a collection of photos with a name.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class Album implements Serializable {

    
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Photo> photos;

    /**
     * Creates a new album with the given name.
     * 
     * @param name the name of the album
     */
    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
    }

    /**
     * Returns the name of the album.
     * 
     * @return the name of the album
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the album.
     * 
     * @param name the name of the album
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the list of photos in the album.
     * 
     * @return the list of photos in the album
     */
    public List<Photo> getPhotos() {
        return photos;
    }

    /**
     * Get the photo at the given index.
     * @param index the index of the photo
     */
    public Photo getPhotoAtIndex(int index) {
        return photos.get(index);
    }

    /**
     * Sets the list of photos in the album.
     * 
     * @param photos the list of photos in the album
     */
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    /**
     * Adds a photo to the album.
     * 
     * @param photo the photo to add
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    /**
     * Removes a photo from the album.
     * 
     * @param photo the photo to remove
     */
    public void removePhoto(Photo photo) {
        this.photos.remove(photo);
    }

    /**
     * Checks if the album contains the photo with the given pathname.
     * @param path the pathname of the photo
     * @return true if the album contains the photo with the given pathname, false otherwise
     */
    public boolean hasPhoto(String path) {
        return getPhotoByPath(path) != null; 
    }
    
    /**
     * Returns the photo with the given path.
     * 
     * @param path the path of the photo
     * @return the photo with the given path or null if no photo with the given path exists
     */
    public Photo getPhotoByPath(String path) {
        for (Photo photo : photos) {
            if (photo.getPath().equals(path)) {
                return photo;
            }
        }
        return null;
    }

    /**
     * Overrides the toString method to return the name of the album.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Overrides the equals method to compare two albums by their names.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Album other = (Album) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Computes the most recent and most ancient datetimes for photos in the album instance and displays them in a formatted text.
     * 
     * @return the date range of the album in a formatted text
     */
    public String getDateRange() {
        if (photos.isEmpty()) {
            return "Empty album";
        }

        Optional<LocalDateTime> mostRecent = photos.stream()
                .map(Photo::getDateTime)
                .max(LocalDateTime::compareTo);

        Optional<LocalDateTime> mostAncient = photos.stream()
                .map(Photo::getDateTime)
                .min(LocalDateTime::compareTo);

        if (mostRecent.isPresent() && mostAncient.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String mostRecentFormatted = mostRecent.get().format(formatter);
            String mostAncientFormatted = mostAncient.get().format(formatter);

            return String.format("%s - %s", mostAncientFormatted, mostRecentFormatted);
        }

        return "No valid date range";
    }
}
