package utils;

import java.io.File;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.Instant;

/**
 * This class contains utility methods for working with image files.
 * @author Sree Kommalapati and Shreeti Patel
 */
public class ImageUtils {
    /**
     * Returns the last modified date and time of the file at the given path.
     * @param path the path of the file
     */
    public static LocalDateTime getLastModifiedDateTime(String path) {

        // Create a File object from the path
        File file = new File(path);

        // Read the file's last modified time in milliseconds since the Unix epoch
        long lastModifiedEpochMillis = file.lastModified();
    
        // Convert the epoch milliseconds to LocalDateTime using the system's default time zone
        LocalDateTime lastModifiedDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(lastModifiedEpochMillis), ZoneId.systemDefault());
    
        return lastModifiedDateTime;
    }
}
