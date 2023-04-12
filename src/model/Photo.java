package model;

import java.io.Serializable;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

import utils.ImageUtils;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class represents a photo in the application. A photo has a path, caption, date and time, and a dictionary of tags.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String path;
    private String caption;
    private LocalDateTime dateTime;
    private Map<String, List<String>> tags;

    /**
     * Creates a new photo with the given path, caption, and date and time.
     * @param path the path of the photo
     * @param caption the caption of the photo
     */
    public Photo(String path, String caption) {
        this.path = path;
        this.caption = caption;
        this.dateTime = ImageUtils.getLastModifiedDateTime(path);
        this.tags = new HashMap<String, List<String>>();
    }

    /**
     * Returns the path of the photo.
     * @return the path of the photo
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the caption of the photo.
     * @return the caption of the photo
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the caption of the photo.
     * @param caption the caption of the photo
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

    /**
     * Returns the date and time of the photo.
     * @return the date and time of the photo
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns the list of tags for the photo.
     * @return the list of tags for the photo.
     */
    public List<String> getTags() {
        return tags.keySet().stream().collect(Collectors.toList());
    }

    public void addTag(String name, String value) {
        if (this.tags.containsKey(name)) {
            this.tags.get(name).add(value);
        } else {
            this.tags.put(name, new ArrayList<String>());
            this.tags.get(name).add(value);
        }
    }

    public void removeTag(String name) {
        this.tags.remove(name);
    }

    public List<String> getTagValues(String name) {
        return this.tags.get(name);
    }

    /**
     * Display the photo on the given JavaFX ImageView.
     * @param imageView the JavaFX ImageView to display the photo in
     */
    public void displayOn(ImageView imageView) {
        Image image = new Image("file:" + path);
        imageView.setImage(image);
    }

    /**
     * Returns a string representation of the tags.
     * @return a string representation of the tags
     */
    private String printTags() {
        Iterator<String> it = tags.keySet().iterator();
        List<String> tempList = null;

        String ret = "";

        while (it.hasNext()) {
            String key = it.next().toString();             
            tempList = tags.get(key);
            if (tempList != null) {
                for (String value: tempList) {
                    ret += key + ": " + value + ", ";
                }
            }
        }

        return ret;
    }

    /**
     * Returns a description of the photo.
     * @return a description of the photo as a string
     */
    public String getDescription() {
        String oneLineResponse = "Caption: " + caption + "Datetime: " + dateTime.toString() + "Tags: " + printTags();
        String response = "";
        for (int i = 0; i < oneLineResponse.length(); i += 50) {
            response += oneLineResponse.substring(i, Math.min(i + 50, oneLineResponse.length())) + "\n";
        }
        return response;
    }
}
