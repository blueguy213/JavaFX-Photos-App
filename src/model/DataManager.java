package model;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;

import java.util.ArrayList;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;

import utils.JavaFXUtils;

/**
 * This class contains methods for serializing and deserializing objects.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class DataManager {

    public static DataManager instance;
    private ArrayList<User> users;
    private ArrayList<Photo> photos;

    /**
     * Create a new DataManager object.
     */
    private DataManager() {
        users = new ArrayList<User>();
        photos = new ArrayList<Photo>();
        readUsers();
        // readPhotos();
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
                User stockUser = new User("stock");
                users.clear();
                users.add(stockUser);
                writeUsers();
                System.out.println("Created stock user");
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
            e.printStackTrace();
        }
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
    public void readPhotos() {
        try {
            File photosFile = new File("db/photos");
            if (!photosFile.exists()) {
                photosFile.getParentFile().mkdirs();
                photosFile.createNewFile();
                ArrayList<Photo> defaultPhotos = new ArrayList<Photo>();
                defaultPhotos.add(new Photo("db/stock/Erin_Chibi.jpg", "Erin_Chibi by platinumft"));
                defaultPhotos.add(new Photo("db/stock/Pisces_bonehorror.jpg", "Pisces_bonehorror by Jawjee"));
                writePhotos(defaultPhotos);
            }

            FileInputStream fis = new FileInputStream(photosFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object deserializedObject = ois.readObject();
            
            if (!(deserializedObject instanceof ArrayList)) {
                ois.close();
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "The photo file you are trying to read is not valid.");
            }

            ArrayList<?> deserializedList = (ArrayList<?>) deserializedObject;
            
            for (Object element : deserializedList) {
                if (element instanceof Photo) {
                    photos.add((Photo) element);
                } else {
                    ois.close();
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "The photo file you are trying to read is not valid.");
                }
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the users to the db/users file.
     * @param users The ArrayList containing the users to write.
     * 
     * @throws IOException If the file cannot be written to.
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
            e.printStackTrace();
        }
    }

    /**
     * Writes the users to the db/photos file.
     * @param photos The ArrayList containing the photos to write.
     * 
     * @throws IOException If the file cannot be written to.
     * 
     * @see java.io.ObjectOutputStream
     * @see java.io.FileOutputStream
     * @see java.io.File
     */
    public void writePhotos(ArrayList<Photo> photos) {
        File photosFile = new File("db/photos");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(photosFile))) { 
            oos.writeObject(photos);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
     * Gets the photo with the given path.
     * @param path The path of the photo to get.
     * @return The photo with the given path.
     */
    public Photo getPhoto(String path) {
        readPhotos();
        for (Photo photo : photos) {
            if (photo.getPath().equals(path)) {
                return photo;
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
        readAndPrintUsers();
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
        readAndPrintUsers();
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

    private void readAndPrintUsers() {
        readUsers();
        for (User user : users) {
            System.out.println(user.getUsername());
        }
    }
}
