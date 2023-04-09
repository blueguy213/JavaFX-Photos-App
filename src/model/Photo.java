package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String path;
    private String caption;
    private LocalDateTime dateTime;
    private Map<String, String> tags;

    public Photo(String path, String caption, LocalDateTime dateTime) {
        this.path = path;
        this.caption = caption;
        this.dateTime = dateTime;
        this.tags = new HashMap<>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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
