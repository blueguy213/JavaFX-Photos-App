package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a user in the application. A user has a username and a list of albums.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private List<Album> albums;
    private Set<Photo> photos;

    /**
     * Creates a new user with the given username.
     * @param username the username of the user
     */
    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<Album>();
        this.photos = new HashSet<Photo>();
    }

    /**
     * Returns the username of the user.
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the list of albums of the user.
     * @return the list of albums of the user
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Adds the given album to the list of albums of the user.
     * @param album the album to add
     */
    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    /**
     * Removes the given album from the list of albums of the user.
     * @param album the album to remove
     */
    public void removeAlbum(Album album) {
        this.albums.remove(album);
        for (Photo photo : album.getPhotos()) {
            if (photos.contains(photo)) {
                photos.remove(photo);
            }
        }
    }

    /**
     * Returns the album with the given name.
     * @param name the name of the album
     * @return the album with the given name
     */
    public Album getAlbumByName(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Returns true if the user already has an album with the given name.
     * @param name the name of the album
     * @return true if the user already has an album with the given name
     */
    public boolean hasAlbum(String name) {
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the set of photos of the user.
     */
    public void updatePhotoSet() {
        photos.clear();
        for (Album album : albums) {
            for (Photo photo : album.getPhotos()) {
                photos.add(photo);
            }
        }
    }

    /**
     * Adds the given photo to the set of photos of the user if it does not already exist.
     * @param photo the photo to add
     */
    public void addPhoto(Photo photo) {
        photos.add(photo);
    }
}