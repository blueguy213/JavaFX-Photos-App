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
class Tags implements Serializable {

    /**
     * The list of tags.
     */
    private Set<Pair<String, Pair<Boolean, String>>> tags;

    /**
     * Creates a new (empty) list of tags.
     */
    public Tags() {
        tags = new HashSet<Pair<String, Pair<Boolean, String>>>();
    }

    /**
     * Prints the list of tags.
     * @return the list of tags as a string
     */
    @Override
    public String toString() {
        String result = "";
        for (Pair<String, Pair<Boolean, String>> tag : tags) {
            result += tag.getKey() + ": " + tag.getValue().getValue() + ", ";
        }
        return result.substring(0, Math.max(0, result.length() - 2));
    }

    /**
     * Returns a list of key, value Pairs.
     * @return the list of (key, value) string pairs for searching
     */
    public List<Pair<String, Pair<Boolean, String>>> getPairs() {
        return new ArrayList<Pair<String, Pair<Boolean, String>>>(tags);
    }


    /**
     * Adds a tag to the list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     */
    public void add(String key, String value, boolean isUnique) {
        tags.add(new Pair<String, Pair<Boolean, String>>(key, new Pair<Boolean, String>(isUnique, value)));
    }

    /**
     * Removes a tag from the list of tags.
     * @param key the key of the tag
     * @param value the value of the tag
     */
    public void remove(String key, String value) {
        tags.remove(new Pair<String, Pair<Boolean, String>>(key, new Pair<Boolean, String>(false, value)));
        tags.remove(new Pair<String, Pair<Boolean, String>>(key, new Pair<Boolean, String>(true, value)));
    }

    /**
     * Checks if the list of tags contains the given tag.
     */
    public boolean contains(String key, String value) {
        return tags.contains(new Pair<String, Pair<Boolean, String>>(key, new Pair<Boolean, String>(false, value))) || tags.contains(new Pair<String, Pair<Boolean, String>>(key, new Pair<Boolean, String>(true, value)));
    }

    /**
     * Checks if the list of tags contains a tag with the given key. Returns 0 -> no key, 1 -> unique key, 2 -> non-unique key.
     */
    public boolean isKeyUnique(String key) {
        for (Pair<String, Pair<Boolean, String>> tag : tags) {
            if (tag.getKey().equals(key) && tag.getValue().getKey()) {
                return true;
            }
        }
        return false;
    }
}
