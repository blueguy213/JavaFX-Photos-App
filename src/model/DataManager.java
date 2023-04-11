package model;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File;

import java.util.ArrayList;

import javafx.scene.control.Alert.AlertType;

import utils.JavaFXUtils;

/**
 * This class contains methods for serializing and deserializing objects.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class DataManager {

    ArrayList<User> users = new ArrayList<>();
    ArrayList<Photo> photos = new ArrayList<>();

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
                ArrayList<User> defaultUsers = new ArrayList<>();
                users.add(new User("stock"));
                writeUsers(defaultUsers);
            }

            FileInputStream fis = new FileInputStream(usersFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object deserializedObject = ois.readObject();
            
            if (!(deserializedObject instanceof ArrayList)) {
                ois.close();
                JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "The user file you are trying to read is not valid.");
            }

            ArrayList<?> deserializedList = (ArrayList<?>) deserializedObject;
            
            for (Object element : deserializedList) {
                if (element instanceof User) {
                    users.add((User) element);
                } else {
                    ois.close();
                    JavaFXUtils.showAlert(AlertType.ERROR, "Error", "Invalid File", "The user file you are trying to read is not valid.");
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
            oos.close();
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
        File usersFile = new File("db/users");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) { 
            oos.writeObject(photos);
            oos.flush();
            oos.close();
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
    public static Photo getPhoto(String path) {
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
    public static void addUser(String username) {
        users.add(new User(username));
    }
}
