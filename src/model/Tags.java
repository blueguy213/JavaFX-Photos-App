package model;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * This class represents a list of tags in the application. Each tag is a <key, value> pair.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class Tags {

    /**
     * The list of tags.
     */
    private List<Pair<String, String>> tags;

    /**
     * Creates a new (empty) list of tags.
     */
    public Tags() {
        tags = new ArrayList<Pair<String, String>>();
    }

    /**
     * Prints the list of tags.
     * @return the list of tags as a string
     */
    @Override
    public String toString() {
        String result = "";
        for (Pair<String, String> tag : tags) {
            result += tag.getKey() + ":" + tag.getValue() + ", ";
        }
        return result.substring(0, result.length() - 2);
    }

    /**
     * Adds a tag to the list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     * @return the index of the tag in the list of tags on success, -1 on empty key or value, -2 on duplicate tag
     */
    public int add(String key, String value) {
        if (key == null || value == null) {
            return -1;
        } else if (tags.contains(new Pair<String, String>(key, value))) {
            return -2;
        } else {
            tags.add(new Pair<String, String>(key, value));
            return tags.indexOf(new Pair<String, String>(key, value));
        }
    }

    /**
     * Deletes a tag from the list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     * @return the index of the tag in the list of tags on success, -1 on empty key or value, -2 on non-existent tag
     */
    public int remove(String key, String value) {
        if (key == null || value == null) {
            return -1;
        } else if (!tags.contains(new Pair<String, String>(key, value))) {
            return -2;
        } else {
            tags.remove(new Pair<String, String>(key, value));
            return tags.indexOf(new Pair<String, String>(key, value));
        }
    }
}
