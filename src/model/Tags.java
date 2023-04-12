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
     */
}
