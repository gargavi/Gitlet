package gitlet;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
/** This class serves as the commit object for the Gitlet structure.
 *  Essentially it contains
 * hash references to blobs, and itself contains some information
 * (data, message) and hash code
 * Commits can also have merges, and have parent id's and such.
 * @author aviga
 */

public class Commit implements Serializable {

    /** This the normal way to initialize.
     * @param tmessage the message for the commit
     * @param files the contents
     * @param parent the parent of the shit
     * @param branch the branch it belongs to*/
    public Commit(
            String tmessage,
            HashMap<String, String> files, String parent, String branch) {
        message = tmessage;
        contents = files;
        _parent = parent;
        merge = false;
        _mergeparent = null;
        _branch = branch;
        Date temp = new Date();
        SimpleDateFormat format = new SimpleDateFormat(
                "E MMM dd HH:mm:ss yyyy Z");
        time = format.format(temp);
    }
    /** This initalizes a merged commit.
     * @param firstp the first parent
     * @param secondp the second parent
     * @param files the second set of files
     * @param branch1 the branch it belongs to
     * @param branch2 the branch it belongs to */

    public Commit(
            String firstp,
            String secondp, HashMap<String, String>
                    files, String branch1, String branch2) {
        message  = "Merged " + branch2 + " into " + branch1 + ".";
        _parent = firstp;
        _mergeparent = secondp;
        merge = true;
        contents = files;
        _branch = branch1;
        Date temp = new Date();
        SimpleDateFormat format = new SimpleDateFormat(
                "E MMM dd HH:mm:ss yyyy Z");
        time = format.format(temp);
    }
    /** Creates a copy of another commit.
     * @param old the other hash value of the commit*/
    public Commit(Commit old) {
        message = old.getMessage();
        _parent = old.getParent();
        _mergeparent = old.getSParent();
        merge = old.merge;
        contents = old.getContents();
        _branch = old.getbranch();
        time = old.getTime();
    }
    /** Put an element in the contents.
     * @param name the name of the thing to put in
     * @param hash the hash value of the value*/
    public void feed(String name, String hash) {
        contents.put(name, hash);
    }
    /** Remove an element from the contents.
     * @param name the name of the file to remove. */
    public void rem(String name) {
        contents.remove(name);
    }

    /** Used to create the initial commit. */
    public Commit() {
        _parent = null;
        merge = false;
        _mergeparent = null;
        contents = new HashMap<String, String>();
        _branch = "master";
        Date temp = new Date(0);
        SimpleDateFormat format = new SimpleDateFormat(
                "E MMM dd HH:mm:ss yyyy Z");
        time = format.format(temp);
        message = "initial commit";
    }
    /** This is the way of retrieves the hash.
     * @return the string of the hash */
    private String hashval() {
        List<Object> total = new ArrayList<Object>();
        total.add(message);
        total.add(time);
        total.add(_branch);
        total.add("Commit");
        if (_parent == null) {
            return Utils.sha1(total);
        } else if (merge) {
            total.add(_mergeparent);
            for (String b: contents.values()) {
                total.add(b);
            }
            return Utils.sha1(total);
        } else {
            for (String b: contents.values()) {
                total.add(b);
            }
            return Utils.sha1(total);
        }
    }
    /** Return the hash value.
     * @return String of the hash */
    String gethash() {
        return hashval();
    }
    /** Return the branch this commit belongs too.
     * @return name of the branch */
    public String getbranch() {
        return _branch;
    }
    /** Get the message of the commit.
     * @return the message*/
    public String getMessage() {
        return message;
    }
    /** Get the time of the commit.
     * @return the time of the commit*/
    public String getTime() {
        return time;
    }
    /** Retrieves name of the parent.
     * @return the parent*/
    public String getParent() {
        return _parent;
    }
    /** Retrieves name of the second parent.
     * @return the mergedparent. */
    public String getSParent() {
        return _mergeparent;
    }
    /** Retrieves the hashes of the blobs.
     * @return the collection */
    public Collection<String> getBlobs() {
        return contents.values();
    }
    /** Retrieves the contents of the commit.
     * @return hashMap */
    public HashMap<String, String> getContents() {
        return contents;
    }
    /** Whether it was merged.*/
    private Boolean merge;
    /** Name of the branch. */
    private String _branch;
    /** Name of merge parent. */
    private String _mergeparent;
    /** Parent id via hash value. */
    private String _parent;
    /** Represents the time of the commit. */
    private String time;
    /** Represents a mapping from file name to the hash code of the file. */
    private HashMap<String, String> contents;
    /** Represents the message that is associated with the commit. */
    private String message;
}
