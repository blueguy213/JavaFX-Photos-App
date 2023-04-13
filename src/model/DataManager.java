package model;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import utils.JavaFXUtils;

/**
 * This class contains methods for serializing and deserializing objects.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class DataManager {

    public static DataManager instance;

    private User loggedInUser;
    private Album openedAlbum;
    private int selectedPhotoIndex;

    private ArrayList<User> users;

    /**
     * Create a new DataManager object.
     */
    private DataManager() {
        users = new ArrayList<User>();
        loggedInUser = null;
        openedAlbum = null;
        selectedPhotoIndex = -1;
        readUsers();
    }

    /**
     * Returns the DataManager instance.
     * @return the DataManager instance
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    /**
     * Reads the users from the users file and adds them to the users ArrayList.
     * @param users The ArrayList to add the users to.
     * 
     * @throws IOException If the file cannot be read.
     * @throws ClassNotFoundException If the file cannot be deserialized.
     * 
     * @see java.io.ObjectInputStream
     * @see java.io.FileInputStream
     * @see java.io.File
     */
    public void readUsers() {
        try {
            File usersFile = new File("db/users");
            if (!usersFile.exists()) {
                usersFile.getParentFile().mkdirs();
                usersFile.createNewFile();
                users.clear();
                writeUsers();
                return;
            }

            FileInputStream fis = new FileInputStream(usersFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object deserializedObject = ois.readObject();
            
            if (!(deserializedObject instanceof ArrayList)) {
                ois.close();
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "The user file you are trying to read is not valid. Not an ArrayList.");
            }

            ArrayList<?> deserializedList = (ArrayList<?>) deserializedObject;

            users.clear();
            for (Object element : deserializedList) {
                if (element instanceof User) {
                    users.add((User) element);
                } else {
                    ois.close();
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "The user file you are trying to read is not valid. Not a User.");
                }
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "IOException | ClassNotFoundException");
        }
    }

    /**
     * Writes the users to the db/users file.
     * @param users The ArrayList containing the users to write.
     * 
     * 
     * @see java.io.ObjectOutputStream
     * @see java.io.FileOutputStream
     * @see java.io.File
     */
    public void writeUsers() {
        File usersFile = new File("db/users");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) { 
            oos.writeObject(users);
            oos.flush();
        } catch (IOException e) {
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "IOException");
        }
    }

    /**
     * Checks if the given username is already taken.
     * @param username The username to check.
     * @return True if the username is taken, false otherwise.
     */
    public boolean isUsernameTaken(String username) {
        readUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the user with the given username.
     * @param username The username of the user to get.
     * @return The user with the given username.
     */
    public User getUser(String username) {
        readUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a user to the users ArrayList.
     * @param username The name of the user to add.
     */
    public void addUser(String username) {
        users.add(new User(username));
        writeUsers();
    }

    /**
     * Removes a user with the given username from the users ArrayList if it exists.
     * @param username The username of the user to remove.
     */
    public void removeUser(String username) {
        // Remove the user from the users ArrayList.
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                break;
            }
        }
        writeUsers();
    }

    /**
     * Adds all users in the users ArrayList to the given ListView.
     * @param listView The ListView to add the users to.
     */
    public void updateUserListView(ListView<String> listView) {
        readUsers();
        listView.getItems().clear();
        for (User user : users) {
            listView.getItems().add(user.getUsername());
        }
    }

    /**
     * Log in the user with the given username.
     * @param username The username of the user to log in.
     */
    public void logIn(String username) {
        loggedInUser = getUser(username);
    }

    /**
     * Log out the currently logged in user.
     */
    public void logOut() {
        closeAlbum();
        loggedInUser = null;
    }

    /**
     * Returns all the albums for the user that is currently logged in.
     * @return The albums for the user that is currently logged in.
     */
    public List<Album> getCurrentUserAlbums() {
        return loggedInUser.getAlbums();
    }

    /**
     * Updates the given ListView with all the albums for the user that is currently logged in.
     * @param listView The ListView to update.
     */
    public void updateAlbumListView(ListView<Album> listView) {
        listView.getItems().clear();
        for (Album album : loggedInUser.getAlbums()) {
            listView.getItems().add(album);
        }
    }

    /**
     * Checks if the given album name is already taken by the user that is currently logged in.
     * @param albumName The album name to check.
     */
    public boolean hasAlbum(String albumName) {
        for (Album album : loggedInUser.getAlbums()) {
            if (album.getName().equals(albumName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an album with the given name to the user that is currently logged in.
     * @param albumName The name of the album to add.
     */
    public void addAlbum(Album album) {
        loggedInUser.addAlbum(album);
        writeUsers();
    }

    /**
     * Removes the album with the given name from the user that is currently logged in.
     * @param albumName The name of the album to remove.
     */
    public void removeAlbum(Album album) {
        loggedInUser.removeAlbum(album);
        writeUsers();
    }

    /**
     * Sets the currently selected album.
     * @param album The album to set as the currently selected album.
     */
    public void openAlbum(Album album) {
        openedAlbum = album;
        if (openedAlbum.getPhotos().size() > 0) {
            selectedPhotoIndex = 0;
        } else {
            selectedPhotoIndex = -1;
        }
    }

    /**
     * Gets the opened album name.
     */
    public String getOpenedAlbumName() {
        return openedAlbum.getName();
    }

    /**
     * Add the given photo to the currently selected album.
     * @param photo The photo to add.
     */
    public void addPhotoToOpenedAlbum(Photo photo) {
        // Check if the photo is already in the album.
        for (Photo p : openedAlbum.getPhotos()) {
            if (p.equals(photo)) {
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Photo already in album.", "Please select a different photo.");
                return;
            }
        }
        // Add the photo to the album and the user.
        openedAlbum.addPhoto(photo);
        loggedInUser.addPhoto(photo);
        // Select the photo.
        selectedPhotoIndex = openedAlbum.getPhotos().size() - 1;
        // Write the users to the database.
        writeUsers();
    }

    /**
     * Remove the given photo from the currently selected album.
     * @param photo The photo to remove.
     */
    public void removePhotoFromOpenedAlbum(Photo photo) {
        // Remove the photo from the album.
        openedAlbum.removePhoto(photo);
        writeUsers();
    }

    /**
     * Display the selcted photo in the given ImageView.
     * @param imageView The ImageView to display the photo in.
     */
    public void displaySelectedPhoto(ImageView imageView) {
        if (selectedPhotoIndex == -1) {
            Image noImageInAlbumStockImage = new Image(getClass().getResourceAsStream("stock/Shibukawa_Kiyohiko_Meme.jpg"));
            imageView.setImage(noImageInAlbumStockImage);
            return;
        }
        Photo selectedPhoto = openedAlbum.getPhotoAtIndex(selectedPhotoIndex);
        selectedPhoto.displayOn(imageView);
    }

    /**
     * Move to the next photo in the currently opened album.
     */
    public void nextPhoto() {
        selectedPhotoIndex = (selectedPhotoIndex + 1) % openedAlbum.getPhotos().size();
    }

    /**
     * Move to the previous photo in the currently opened album.
     */
    public void previousPhoto() {
        selectedPhotoIndex = (selectedPhotoIndex - 1 + openedAlbum.getPhotos().size()) % openedAlbum.getPhotos().size();
    }

    /**
     * Closes the currently opened album.
     */
    public void closeAlbum() {
        openedAlbum = null;
    }
}
