package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The goal of this is to serve as a container for the files that we have,
 * with ability to compare two different blobs to each other to
 * make the commit class easier.
 * A blob should contain the name of the file and the contents of a file
 * @author aviga
 */


public class Blob implements Serializable {

    /** This initializes the blob class.
     * @param filename name of file**/
    public Blob(String filename) {
        name = filename;
        try {
            File file = new File(filename);
            byteme = Utils.readContents(file);
            word = Utils.readContentsAsString(file);
        } catch (IllegalArgumentException expr) {
            System.out.println(filename);
            throw Utils.error("File Does Not Exist");
        }
        List<Object> temp = new ArrayList<Object>();
        temp.add(byteme);
        temp.add(name);
        temp.add(word);
        temp.add("Blob");
        hash = Utils.sha1(temp);
    }
    /** This initializes the blob class.
     * @param filename name of file
     * @param location name of location**/
    public Blob(String filename, String location) {
        name = filename;
        try {
            File file = new File(location + filename);
            byteme = Utils.readContents(file);
            word = Utils.readContentsAsString(file);
        } catch (IllegalArgumentException expr) {
            throw Utils.error("File Does Not Exist");
        }
        List<Object> temp = new ArrayList<Object>();
        temp.add(byteme);
        temp.add(name);
        temp.add(word);
        temp.add("Blob");
        hash = Utils.sha1(temp);
    }

    /** Retrieve the name of the file.
     * @return the name of the string. */
    String getName() {
        return name;
    }
    /** Return the string of the file contents.
     * @return the word.  */
    String getSContents() {
        return word;
    }
    /** Return the byte array of the file contents.
     * @return the byte array*/
    byte[] getBContents() {
        return byteme;
    }
    /** Return the hash value.
     * @return the hashvalue*/
    String gethash() {
        return hash;
    }

    /** The name of the file. */
    private String name;
    /** contents of the file as a byte array. */
    private byte[] byteme;
    /** Contents of the file as a string. */
    private String word;
    /** Hash Value of the Blob. */
    private String hash;

}
