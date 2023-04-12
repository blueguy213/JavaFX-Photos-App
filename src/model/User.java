package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a user in the application. A user has a username and a list of albums.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private List<Album> albums;
    private List<Photo> photos;

    /**
     * Creates a new user with the given username.
     * @param username the username of the user
     */
    public User(String username) {
        this.username = username;
        this.albums = new ArrayList<>();
    }

    /**
     * Returns the username of the user.
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username the username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the list of albums of the user.
     * @return the list of albums of the user
     */
    public List<Album> getAlbums() {
        return albums;
    }

    /**
     * Sets the list of albums of the user.
     * @param albums the list of albums of the user
     */
    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    /**
     * Adds the given album to the list of albums of the user.
     * @param album the album to add
     */
    public void addAlbum(Album album) {
        this.albums.add(album);
    }

    /**
     * Adds the given photo to the list of photos of the user.
     * @param photo the photo to add
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    /**
     * Removes the given album from the list of albums of the user.
     * @param album the album to remove
     */
    public void removeAlbum(Album album) {
        this.albums.remove(album);
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
    public boolean hasDuplicateAlbumName(String name) {
        int count = 0;
        for (Album album : albums) {
            if (album.getName().equals(name)) {
                count++;
            }
        }
        return count > 1;
    }
}