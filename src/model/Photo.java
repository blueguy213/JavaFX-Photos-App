package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import utils.ImageUtils;

/**
 * This class represents a photo in the application. A photo has a path, caption, date and time, and a dictionary of tags.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String path;
    private String caption;
    private LocalDateTime dateTime;
    private Map<String, String> tags;

    /**
     * Creates a new photo with the given path, caption, and date and time.
     * @param path the path of the photo
     * @param caption the caption of the photo
     */
    public Photo(String path, String caption) {
        this.path = path;
        this.caption = caption;
        this.dateTime = ImageUtils.getLastModifiedDateTime(path);
        this.tags = new HashMap<>();
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
     * Returns the dictionary of tags of the photo.
     * @return the dictionary of tags of the photo
     */
    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }

    public void addTag(String name, String value) {
        this.tags.put(name, value);
    }

    public void removeTag(String name) {
        this.tags.remove(name);
    }

    public String getTagValue(String name) {
        return this.tags.get(name);
    }
}
