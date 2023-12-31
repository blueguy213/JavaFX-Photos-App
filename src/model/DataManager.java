package model;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

import java.time.LocalDate;
import java.time.ZoneId;

import javafx.scene.layout.TilePane;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.util.Pair;

import utils.JavaFXUtils;

/**
 * This class interfaces between the internal model and the UI controllers.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class DataManager {

    private static final int THUMBNAIL_WIDTH = 80;
    private static final int THUMBNAIL_HEIGHT = 80;

    public static DataManager instance;

    private User loggedInUser;
    private Album openedAlbum;
    private int selectedPhotoIndex;

    private List<User> users;
    private List<Photo> searchResults;

    /**
     * Create a new DataManager object.
     */
    private DataManager() {
        users = new ArrayList<User>();
        searchResults = new ArrayList<Photo>();

        loggedInUser = null;
        openedAlbum = null;

        selectedPhotoIndex = 0;
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
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", e.getMessage());
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
                //System.out.println("Getting user " + username);
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
     * Returns the set of photos for the user that is currently logged in.
     * @return The set of photos for the user that is currently logged in.
     */
    public List<Photo> getAllPhotos() {
        return new ArrayList<Photo>(loggedInUser.getPhotos());
    }

    /**
     * Updates the given ListView with all the albums for the user that is currently logged in.
     * @param listView The ListView to update.
     */
    public void displayAlbumsOn(ListView<String> listView) {
        listView.getItems().clear();
        for (Album album : loggedInUser.getAlbums()) {
            listView.getItems().add(album.getName());
        }
    }

    /**
     * Updates the given Labels with the information of the currently opened album.
     */
    public void displayAlbumInfoOn(Label albumName, Label photoCount, Label dateRange, String selectedAlbumName) {

        // Get the selectedAlbum
        Album selectedAlbum = loggedInUser.getAlbumByName(selectedAlbumName);

        if (selectedAlbum == null) {
            // Set the selectedAlbum information to empty
            albumName.setText("");
            photoCount.setText("");
            dateRange.setText("");
            return;
        }

        // Implement the logic for updating the selectedAlbum information (name, photo count, and date range)
        albumName.setText(selectedAlbum.getName());
        if (selectedAlbum.getPhotos().size() == 1) {
            photoCount.setText(selectedAlbum.getPhotos().size() + " photo");
        } else {
            photoCount.setText(selectedAlbum.getPhotos().size() + " photos");
        }
        dateRange.setText(selectedAlbum.getDateRange());
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
    public void addAlbum(String albumname) {
        loggedInUser.addAlbum(new Album(albumname));
        writeUsers();
    }

    /**
     * Changes the selected album name to the given name.
     */
    public void renameAlbum(String oldName, String newName) {
        loggedInUser.getAlbumByName(oldName).setName(newName);
        writeUsers();
    }

    /**
     * Removes the album with the given name from the user that is currently logged in.
     * @param albumName The name of the album to remove.
     */
    public void removeAlbum(String albumname) {
        loggedInUser.removeAlbum(new Album(albumname));
        loggedInUser.updatePhotoSet();
        writeUsers();
    }

    /**
     * Sets the currently selected album.
     * @param album The album to set as the currently selected album.
     */
    public void openAlbum(String albumname) {
        openedAlbum = loggedInUser.getAlbumByName(albumname);
        selectedPhotoIndex = 0;
    }

    /**
     * Gets the opened album name.
     */
    public String getOpenedAlbumName() {
        return openedAlbum.getName();
    }

    /**
     * Checks if the album that is currently opened has a photo with the given pathname.
     */
    public boolean hasPhoto(String pathname) {
        for (Photo photo : openedAlbum.getPhotos()) {
            if (photo.getPath().equals(pathname)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the caption of the currently selected photo.
     */
    public void setCaption(String caption) {
        openedAlbum.getPhotos().get(selectedPhotoIndex).setCaption(caption);
        writeUsers();
    }

    /**
     * Add the given photo to the currently selected album.
     * @param photo The photo to add.
     */
    public void addPhotoToOpenedAlbum(String pathname, String caption) {
        // Create the photo.
        Photo photo = new Photo(pathname, caption);
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
    public void removeSelectedPhoto() {
        // Remove the photo from the album.
        openedAlbum.removePhoto(openedAlbum.getPhotos().get(selectedPhotoIndex));
        // Update the user's photo set
        loggedInUser.updatePhotoSet();
        // Update the selected photo index if necessary.
        selectedPhotoIndex = Math.min(selectedPhotoIndex, openedAlbum.getPhotos().size() - 1);
        // Display the selected photo.
        // Write the users to the database.
        writeUsers();
    }

    /**
     * Display the selcted photo in the given ImageView.
     * @param imageView The ImageView to display the photo in.
     */
    public void displaySelectedPhotoOn(ImageView imageView, TextArea textArea) {
        if (openedAlbum == null) {
            // Display a stock photo if the album is empty.
            if (searchResults.size() == 0) {
                try {
                    FileInputStream inputStream = new FileInputStream("./stock/NO.gif");
                    Image noImageInAlbumStockImage = new Image(inputStream);
                    imageView.setImage(noImageInAlbumStockImage);
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    // Handle the exception if the image file is not found
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Stock image not found.", "Fix needed :/");
                } catch (IOException e) {
                    // Handle the exception if there's an error closing the FileInputStream
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Error closing FileInputStream.", "Fix needed :/");
                }
                return;
            }
            Photo selectedPhoto = searchResults.get(selectedPhotoIndex);
            selectedPhoto.displayOn(imageView);
            textArea.setText(selectedPhoto.getDescription());
        } else {
            // Display a stock photo if the album is empty.
            if (openedAlbum.getPhotos().size() == 0) {
                try {
                    FileInputStream inputStream = new FileInputStream("./stock/NO.gif");
                    Image noImageInAlbumStockImage = new Image(inputStream);
                    imageView.setImage(noImageInAlbumStockImage);
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    // Handle the exception if the image file is not found
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Stock image not found.", "Fix needed :/");
                } catch (IOException e) {
                    // Handle the exception if there's an error closing the FileInputStream
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Error closing FileInputStream.", "Fix needed :/");
                }
                return;
            }
            Photo selectedPhoto = openedAlbum.getPhotoAtIndex(selectedPhotoIndex);
            selectedPhoto.displayOn(imageView);
            textArea.setText(selectedPhoto.getDescription());
        }
        
    }

    /**
     * Move to the next photo in the currently opened album.
     */
    public void nextPhoto() {
        if (openedAlbum == null) {
            selectedPhotoIndex = (selectedPhotoIndex + 1) % searchResults.size();
        } else {
            selectedPhotoIndex = (selectedPhotoIndex + 1) % openedAlbum.getPhotos().size();
        }
    }

    /**
     * Move to the previous photo in the currently opened album.
     */
    public void previousPhoto() {
        if (openedAlbum == null) {
            selectedPhotoIndex = (selectedPhotoIndex - 1 + searchResults.size()) % searchResults.size();
        } else {
            selectedPhotoIndex = (selectedPhotoIndex - 1 + openedAlbum.getPhotos().size()) % openedAlbum.getPhotos().size();
        }
        
    }

    /**
     * Gets the currently selected photo index.
     */
    public int getSelectedPhotoIndex() {
        return selectedPhotoIndex;
    }

    /**
     * Displays the thumbnails of the photos in the album.
     */
    public void displayThumbnailsOn(TilePane thumbnailTilePane) {
        thumbnailTilePane.getChildren().clear(); // Clear the existing children
        if (openedAlbum == null) {
            for (Photo photo : searchResults) {
                Image image = new Image(photo.getPath(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, true, true);
                ImageView thumbnail = new ImageView(image);
                thumbnail.setPreserveRatio(true);
                thumbnail.setFitWidth(THUMBNAIL_WIDTH);
                thumbnail.setFitHeight(THUMBNAIL_HEIGHT);
                thumbnail.setUserData(photo);
                thumbnailTilePane.getChildren().add(thumbnail);
            }
        } else {
            for (Photo photo : openedAlbum.getPhotos()) {
                Image image = new Image(photo.getPath(), THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, true, true);
                ImageView thumbnail = new ImageView(image);
                thumbnail.setPreserveRatio(true);
                thumbnail.setFitWidth(THUMBNAIL_WIDTH);
                thumbnail.setFitHeight(THUMBNAIL_HEIGHT);
                thumbnail.setUserData(photo);
                thumbnailTilePane.getChildren().add(thumbnail);
            }
        }
    }

    /**
     * Display the creatable choices of tags on the given key and value ComboBoxes.
     * @param keyBox The ComboBox to display the keys on.
     * @param valueBox The ComboBox to display the valyes on.
     */
    public void displayCreatableTagsOn(ComboBox<String> keyBox, ComboBox<String> valueBox) {
        // Clear the existing items in the ComboBoxes
        keyBox.getItems().clear();
        valueBox.getItems().clear();
    
        // Loop through the tags of the current user
        for (Pair<String, Pair<Boolean, String>> tag : loggedInUser.getTags().getPairs()) {
            // Add the tag to the ComboBox items
            keyBox.getItems().add(tag.getKey());
            valueBox.getItems().add(tag.getValue().getValue());
        }

        // If there are any tags, select the first one by default
        if (!keyBox.getItems().isEmpty()) {
            keyBox.getSelectionModel().select(0);
            valueBox.getSelectionModel().select(0);
        }
    }

    /**
     * Check if the given tag exists in the current user's tags.
     * @param key The key of the tag.
     * @param value The value of the tag.
     */
    public boolean hasTagKey(String key) {
        if (openedAlbum == null) {
            return searchResults.get(selectedPhotoIndex).getTags().containsKey(key);
        } else {
            return openedAlbum.getPhotoAtIndex(selectedPhotoIndex).getTags().containsKey(key);
        }
    }

    /**
     * Add a tag to the currently selected photo using the given key and value.
     * @param key The key of the tag.
     * @param value The value of the tag.
     * @param isUnique The repeatability of the tag.
     */
    public void addTagToSelectedPhoto(String key, String value, boolean isUnique) {
        //System.out.println("Key: '" + key + "' Value: '" + value + "'");
        //System.out.println("Is Unique: " + isUnique);
        if (openedAlbum == null) {
            // Prevent the user from adding a tag to a photo that is not in an album.
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Cannot add tag to photo not in an album.", "Fix needed :/");
        } else {
            // Check if the tag already exists.
            if (openedAlbum.getPhotoAtIndex(selectedPhotoIndex).getTags().contains(key, value)) {
                // Prevent the user from adding a tag that already exists.
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Tag already exists.", "Add a new tag.");
                return;
            } else if (openedAlbum.getPhotoAtIndex(selectedPhotoIndex).getTags().containsKey(key)) { // Check if the tag already exists for the photo.
                // Get the repeatability of the existing tag.
                boolean isOldTagUnique = openedAlbum.getPhotoAtIndex(selectedPhotoIndex).getTags().isKeyUnique(key);
                //System.out.println("Is Old Tag Unique: " + isOldTagUnique);
                // Check if the new and old tags match.
                if (isUnique != isOldTagUnique) {
                    // Prevent the user from adding a tag that already exists.
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Tag already exists.", "Make sure both tags are unique or not.");
                    return;
                } else if (isUnique || isOldTagUnique) {
                    // Prevent the user from adding a tag that already exists.
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Tag already exists.", "Add a new tag.");
                    return;
                }
            }
            // Add the tag to the photo and user.
            openedAlbum.getPhotoAtIndex(selectedPhotoIndex).addTag(key, value, isUnique);
            loggedInUser.addTag(key, value, isUnique);
             // Write the users to the database.
            writeUsers();
        }
       
    }

    /**
     * Display the choices of unopened albums on the given ChoiceBox.
     * @param listView The ChoiceBox to display the unopened albums on.
     */
    public void displayUnopenedAlbumsOn(ChoiceBox<String> choiceBox) {
        // Clear the existing items in the ChoiceBox
        choiceBox.getItems().clear();
    
        // Loop through the albums of the current user
        for (Album album : getCurrentUserAlbums()) {
            // Check if the album is not currently opened
            if (!album.equals(openedAlbum)) {
                // Add the unopened album to the ChoiceBox items
                choiceBox.getItems().add(album.getName());
            }
        }
    
        // If there are any unopened albums, select the first one by default
        if (!choiceBox.getItems().isEmpty()) {
            choiceBox.getSelectionModel().select(0);
        }
    }

    /**
     * Display the deletable choices of tags on the given ChoiceBox.
     * @param choiceBox The ChoiceBox to display the tags on.
     */
    public void displayDeletableTagsOn(ChoiceBox<String> choiceBox) {

        // Return without adding choices if there are no photos in the album.
        if (openedAlbum.getPhotos().isEmpty()) {
            return;
        }
        // Clear the existing items in the ChoiceBox
        choiceBox.getItems().clear();
        
        // Loop through the tags of the current user
        for (Pair<String, Pair<Boolean, String>> tag : openedAlbum.getPhotoAtIndex(selectedPhotoIndex).getTags().getPairs()) {
            // Add the tag to the ChoiceBox items
            choiceBox.getItems().add(tag.getKey() + ": " + tag.getValue().getValue());
        }

        // If there are any tags, select the first one by default
        if (!choiceBox.getItems().isEmpty()) {
            choiceBox.getSelectionModel().select(0);
        }
    }

    /**
     * Delete the tag specificied by the given String in the format "key-value" from the currently selected photo.
     * @param tag The tag to delete in the format "key-value".
     */
    public void deleteTagFromSelectedPhoto(String tag) {
        if (openedAlbum == null) {
            // Prevent the user from deleting a tag from a photo that is not in an album.
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Cannot delete tag from photo not in an album.", "Open an album first");
        } else {
            if (tag == null || tag.isEmpty()) {
                // Prevent the user from deleting a tag from a photo that is not in an album.
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Cannot delete an empty tag.", "Pick a tag");
                return;
            }
            // Split the tag into its key and value.
            String[] tagParts = tag.split(": ");
            //System.out.println(tagParts[0] + " " + tagParts[1]);
            // Delete the tag from the photo.
            openedAlbum.getPhotoAtIndex(selectedPhotoIndex).removeTag(tagParts[0], tagParts[1]);
            // Write the users to the database.
            writeUsers();
        }
    }

    /**
     * Copy the currently selected photo to the given album.
     * @param albumName The name of the album to copy the photo to.
     * @return The index of the photo in the album. -1 if the photo was not copied.
     */
    public int copySelectedPhotoToAlbum(String albumName) {
        if (openedAlbum == null) {
            // Prevent the user from copying a photo that is not in an album.
            JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Cannot copy photo not in an album.", "Open an album");
            return -1;
        } else {
            // Check if the photo is already in the album.
            if (loggedInUser.getAlbumByName(albumName).hasPhoto(openedAlbum.getPhotoAtIndex(selectedPhotoIndex).getPath())) {
                // Prevent the user from copying a photo that is already in the album.
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Cannot copy photo already in album.", "Rename photo");
                return -1;
            }

            // Get the album to copy the photo to.
            Album album = loggedInUser.getAlbumByName(albumName);
            // Copy the photo to the album.
            album.addPhoto(openedAlbum.getPhotoAtIndex(selectedPhotoIndex));
            // Write the users to the database.
            writeUsers();
            // Return the index of the photo in the album.
            return album.getPhotos().indexOf(openedAlbum.getPhotoAtIndex(selectedPhotoIndex));
        }
    }

    /**
     * Closes the currently opened album.
     */
    public void closeAlbum() {
        selectedPhotoIndex = 0;
        openedAlbum = null;
        writeUsers();
    }

    /**
     * Prepares the search results.
     */
    public void prepareSearchResults() {
        searchResults.clear();
        searchResults.addAll(loggedInUser.getPhotos());
    }

    /**
     * Display all the user's tags on the given ChoiceBox.
     * @param choiceBox The ChoiceBox to display the tags on.
     */
    public void displayAllTagTypesOn(ChoiceBox<String> choiceBox) {

        // Clear the existing items in the ChoiceBox
        choiceBox.getItems().clear();

        // Loop through the tags of the current user
        for (Pair<String, Pair<Boolean, String>> tagType : loggedInUser.getTags().getPairs()) {
            // Add the tagType to the ChoiceBox items
            choiceBox.getItems().add(tagType.getKey() + ": " + tagType.getValue().getValue());
        }
    }

    /**
     * Filter the search results by the date range
     */
    public void filterSearchResultsByDateRange(LocalDate startDate, LocalDate endDate) {

        prepareSearchResults();

        Date start = startDate != null ? Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;
        Date end = endDate != null ? Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()) : null;

        searchResults = searchResults.stream()
            .filter(photo -> {
                Date photoDate = Date.from(photo.getDate().atZone(ZoneId.systemDefault()).toInstant());
                return (start == null || !photoDate.before(start)) && (end == null || !photoDate.after(end));
            }).collect(Collectors.toList());
        selectedPhotoIndex = 0; 
    }

    /**
     * Filter the search results by the single give tag
     * @param key The key of the tag.
     * @param value The value of the tag.
     */
    public void filterSearchResultsByOneTag(String key, String value) {

        prepareSearchResults();

        searchResults = searchResults.stream()
            .filter(photo -> photo.getTags().getPairs().stream()
                .anyMatch(tag -> tag.getKey().equals(key) && tag.getValue().getValue().equals(value)))
            .collect(Collectors.toList());
        selectedPhotoIndex = 0;
    }


    /**
     * Filter the search results by the two given tags (AND)
     * @param key1 The key of the first tag.
     * @param value1 The value of the first tag.
     * @param key2 The key of the second tag.
     * @param value2 The value of the second tag.
     */
    public void filterSearchResultsByTwoTagsAnd(String key1, String value1, String key2, String value2) {

        prepareSearchResults();

        searchResults = searchResults.stream()
            .filter(photo -> photo.getTags().getPairs().stream()
                .anyMatch(tag -> tag.getKey().equals(key1) && tag.getValue().getValue().equals(value1)))
            .filter(photo -> photo.getTags().getPairs().stream()
                .anyMatch(tag -> tag.getKey().equals(key2) && tag.getValue().getValue().equals(value2)))
            .collect(Collectors.toList());
        selectedPhotoIndex = 0;
    }

    /**
     * Filter the search results by the two given tags (OR)
     * @param key1 The key of the first tag.
     * @param value1 The value of the first tag.
     * @param key2 The key of the second tag.
     * @param value2 The value of the second tag.
     */
    public void filterSearchResultsByTwoTagsOr(String key1, String value1, String key2, String value2) {

        prepareSearchResults();

        searchResults = searchResults.stream()
            .filter(photo -> photo.getTags().getPairs().stream()
                .anyMatch((tag -> tag.getKey().equals(key1) && tag.getValue().getValue().equals(value1) || (tag.getKey().equals(key2) && tag.getValue().getValue().equals(value2)))))
            .collect(Collectors.toList());
        selectedPhotoIndex = 0;
    }

    /**
     * Export the search results to a new album with the given name.
     */
    public void exportSearchResultsToAlbum(String albumName) {
        // Create a new album with the given name.
        Album album = new Album(albumName);
        // Add each of the search results to the album.
        for (Photo photo : searchResults) {
            album.addPhoto(photo);
        }
        // Add the album to the user.
        loggedInUser.addAlbum(album);
        // Write the users to the database.
        writeUsers();
    }
    
}
