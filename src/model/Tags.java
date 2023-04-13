package model;

import java.io.Serializable;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

import javafx.util.Pair;

/**
 * This class represents a list of tags in the application. Each tag is a <key, value> pair.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class Tags implements Serializable {

    /**
     * The list of tags.
     */
    private Set<Pair<String, String>> tags;

    /**
     * Creates a new (empty) list of tags.
     */
    public Tags() {
        tags = new HashSet<Pair<String, String>>();
    }

    /**
     * Prints the list of tags.
     * @return the list of tags as a string
     */
    @Override
    public String toString() {
        String result = "";
        for (Pair<String, String> tag : tags) {
            result += tag.getKey() + ": " + tag.getValue() + ", ";
        }
        return result.substring(0, Math.max(0, result.length() - 2));
    }

    /**
     * Returns a list of key, value Pairs.
     * @return the list of (key, value) string pairs for searching
     */
    public List<Pair<String, String>> getPairs() {
        return new ArrayList<Pair<String, String>>(tags);
    }


    /**
     * Adds a tag to the list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     */
    public void add(String key, String value) {
        tags.add(new Pair<String, String>(key, value));
    }

    /**
     * Removes a tag from the list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     */
    public void remove(String key, String value) {
        tags.remove(new Pair<String, String>(key, value));
    }
}
