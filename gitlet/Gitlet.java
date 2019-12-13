package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.List;

/** This will help us manage the git repository and commands we'll be using.
 * @author avigarg
 */

public class Gitlet implements Serializable {

    /**
     * Name of the file.
     */
    private String rootdir;
    /**
     * Name of the initial commit.
     */
    private String initialcommit;
    /**
     * Nme of the root directory.
     */
    private String workingdirectory;
    /**
     * Keeps track of untracked files.
     */
    private ArrayList<String> untracked;
    /**
     * Keeps track of the tracked files at any given point in time.
     */
    private Set<String> tracked;
    /**
     * The staging area.
     */
    private Stage staged;
    /**
     * The names of all branches in the file.
     */
    private ArrayList<String> branches;
    /**
     * The is all the commits.
     */
    private ArrayList<String> commits;
    /**
     * This is the branchheads mapping.
     */
    private HashMap<String, String> branchHeads;
    /**
     * This is the currentbranch we are on.
     */
    private String currentbranch;
    /**
     * This is the current commit hash val.
     */
    private String head;
    /**
     * This is the state of initalized.
     */
    private boolean inited;
    /**
     * This is for the merge class.
     */
    private int lowest;
    /**
     * This is for the merge method.
     */
    private String globalances;
    /**
     * This is for the extra credit.
     */
    private HashMap<String, String> remoterepos;

    /**
     * This initializes the Gitlet class.
     */
    public Gitlet() {
        inited = false;
        untracked = new ArrayList<String>();
        tracked = new HashSet<String>();
        commits = new ArrayList<String>();
        branchHeads = new HashMap<String, String>();
        branches = new ArrayList<String>();
        staged = new Stage();
        lowest = Integer.MAX_VALUE;
        remoterepos = new HashMap<String, String>();
        rootdir = ".gitlet" + System.getProperty("file.separator");
        workingdirectory = System.getProperty("user.dir");
    }

    /**
     * This runs our gitlet.
     *
     * @param args this just has arguments
     */
    public void run(String... args) throws IOException {
        ArrayList<String> firstgroup = new ArrayList<String>(Arrays.asList(
                "add", "rm", "commit", "init", "log", "global-log", "checkout"
        ));
        ArrayList<String> secondgroup = new ArrayList<String>(Arrays.asList(
                "status", "branch", "rm-branch", "reset", "find", "merge"
        ));
        ArrayList<String> thirdgroup = new ArrayList<String>(Arrays.asList(
                "add-remote", "rm-remote", "push", "fetch", "pull"
        ));
        if (firstgroup.contains(args[0])) {
            firstgroupfunc(args);
        } else if (secondgroup.contains(args[0])) {
            secondgroupfunc(args);
        } else if (thirdgroup.contains(args[0])) {
            thirdgroupfunc(args);
        } else {
            throw Utils.error("No command with that name exists.");
        }

    }

    /**
     * This runs our third group func.
     *
     * @param args the arguments
     */
    public void thirdgroupfunc(String... args) {
        if (args[0].equals("add-remote")) {
            if (args.length == 3) {
                addremote(args[1], args[2]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("rm-remote")) {
            if (args.length == 2) {
                removeremote(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("push")) {
            if (args.length == 3) {
                push(args[1], args[2]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("fetch")) {
            if (args.length == 3) {
                fetch(args[1], args[2]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("pull")) {
            if (args.length == 3) {
                pull(args[1], args[2]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        }
    }

    /**
     * This runs our second group func.
     *
     * @param args the arguments
     */
    public void secondgroupfunc(String... args) {
        if (args[0].equals("status")) {
            if (args.length == 1) {
                status();
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("branch")) {
            if (args.length == 2) {
                branch(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("rm-branch")) {
            if (args.length == 2) {
                removebranch(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("reset")) {
            if (args.length == 2) {
                reset(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("find")) {
            if (args.length == 2) {
                find(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("merge")) {
            if (args.length == 2) {
                merge(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        }
    }

    /**
     * This runs our first group func.
     *
     * @param args the arguments
     */
    public void firstgroupfunc(String... args) throws IOException {
        if (args[0].equals("add")) {
            if (args.length == 2) {
                add(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("rm")) {
            if (args.length == 2) {
                remove(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("commit")) {
            if (args.length == 2) {
                commit(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("init")) {
            if (args.length == 1) {
                init();
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("log")) {
            if (args.length == 1) {
                log();
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("global-log")) {
            if (args.length == 1) {
                globall();
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        } else if (args[0].equals("checkout")) {
            if (args[1].equals("--")) {
                if (args.length == 3) {
                    checkoutn(args[2]);
                } else {
                    throw Utils.error("Incorrect Operands.");
                }

            } else if (args.length > 2 && args[2].equals("--")) {
                if (args.length == 4) {
                    checkout(args[3], args[1]);
                } else {
                    throw Utils.error("Incorrect Operands.");
                }
            } else if (args.length == 2) {
                checkoutb(args[1]);
            } else {
                throw Utils.error("Incorrect Operands.");
            }
        }
    }


    /**
     * Init should create a gitlet repository if one doesn't exist.
     * And should create a new commit to put in it
     */
    public void init() throws IOException {
        if (inited) {
            String error = "A Gitlet "
                    + "version-control system "
                    + "already exists in the current directory.";
            throw Utils.error(error);
        } else {
            inited = true;
            Files.createDirectory(Paths.get(".gitlet"));
            Commit initial = new Commit();
            String initName = initial.gethash();
            commits.add(initName);
            File loc = Utils.join(rootdir, initName);
            Utils.writeObject(loc, initial);
            branches.add("master");
            currentbranch = "master";
            head = initName;
            globalances = head;
            branchHeads.put("master", initName);
            initialcommit = initName;
        }
    }

    /**
     * This is the add function for the gitlet class.
     *
     * @param file the name of the file
     */
    public void add(String file) {
        Blob blob;
        File relevant = new File(file);
        if (relevant.exists()) {
            blob = new Blob(file);
        } else {
            throw Utils.error("File does not exist");
        }
        tracked.add(file);
        if (untracked.contains(file)) {
            untracked.remove(file);
        }
        String hashed = blob.gethash();
        File prev = Utils.join(rootdir, head);
        Commit previous = Utils.readObject(prev, Commit.class);
        if (previous.getBlobs().contains(hashed)) {
            staged.remove(file);
        } else {
            staged.add(file);
        }
    }

    /**
     * Need to unstage a file and mark it to be not tracked during next comm.
     * Also delete the file
     *
     * @param file the name of the file
     */
    public void remove(String file) {
        Commit header = Utils.readObject(
                Utils.join(rootdir, head), Commit.class);
        if (header.getContents().containsKey(file) || staged.contains(file)) {
            if (header.getContents().containsKey(file)) {
                untracked.add(file);
                File temp = new File(file);
                Utils.restrictedDelete(temp);
            }
            if (staged.contains(file)) {
                staged.remove(file);
            }
        } else {
            throw Utils.error("No reason to remove the file.");
        }
    }

    /**
     * Make a new commit and add it to whatever the fuck.
     *
     * @param message the message associated with
     */
    public void commit(String message) {
        if (message.equals("")) {
            throw Utils.error("Please enter a commit message.");
        } else {
            File parent = Utils.join(rootdir, head);
            Commit old = Utils.readObject(parent, Commit.class);
            HashMap<String, String> prev = old.getContents();
            HashMap<String, String> staging = staged.getContents();
            HashMap<String, String> cur = new HashMap<String, String>();
            for (String name : tracked) {
                if (staging.containsKey(name)) {
                    cur.put(name, staging.get(name));
                } else if (!untracked.contains(name)
                        && prev.containsKey(name)) {
                    cur.put(name, prev.get(name));
                }
            }
            if (prev.equals(cur)) {
                throw Utils.error(" No changes added to the commit.");
            } else {
                staged = new Stage();
                Commit current = new Commit(message, cur, head, currentbranch);
                String name = current.gethash();
                branchHeads.put(currentbranch, name);
                commits.add(name);
                head = name;
                File loc = Utils.join(rootdir, name);
                Utils.writeObject(loc, current);
                untracked.clear();
            }
        }
    }

    /**
     * Displays the commits from current head to the initial commit.
     */
    public void log() {
        File cur = Utils.join(rootdir, head);
        Commit current = Utils.readObject(cur, Commit.class);
        while (current.getParent() != null) {
            System.out.println("===");
            System.out.println("commit " + current.gethash());
            if (current.getSParent() != null) {
                String first = current.getParent().substring(0, 7);
                String second = current.getSParent().substring(0, 7);
                System.out.println("Merge: " + first + " " + second);
            }
            System.out.println("Date: " + current.getTime());
            System.out.println(current.getMessage());
            System.out.println();
            String temp = current.getParent();
            cur = new File(rootdir + temp);
            current = Utils.readObject(cur, Commit.class);
        }
        System.out.println("===");
        System.out.println("commit " + current.gethash());
        System.out.println("Date: " + current.getTime());
        System.out.println(current.getMessage());
        System.out.println();
    }

    /**
     * This is the way to call all the logs.
     */
    public void globall() {
        for (String b : commits) {
            File cur = new File(rootdir + b);
            Commit current = Utils.readObject(cur, Commit.class);
            System.out.println("===");
            System.out.println("commit " + current.gethash());
            if (current.getSParent() != null) {
                String first = current.getParent().substring(0, 6);
                String second = current.getSParent().substring(0, 6);
                System.out.println("Merge: " + first + " " + second);
            }
            System.out.println("Date: " + current.getTime());
            System.out.println(current.getMessage());
            System.out.println();
        }
    }

    /**
     * This will find a particular message.
     *
     * @param mess the message
     */
    public void find(String mess) {
        boolean found = false;
        for (String b : commits) {
            File cur = new File(rootdir + b);
            Commit current = Utils.readObject(cur, Commit.class);
            if (mess.equals(current.getMessage())) {
                System.out.println(current.gethash());
                found = true;
            }
        }
        if (!found) {
            throw Utils.error("Found no commit with that message.");
        }
    }

    /**
     * This will print out the status of the entire thing.
     */
    public void status() {
        List<String> workingdir = Utils.plainFilenamesIn(
                workingdirectory);
        Commit current = Utils.readObject(
                Utils.join(rootdir, head), Commit.class);
        System.out.println("=== Branches ===");
        SortedMap<String, String> sorted = new TreeMap<
                String, String>(branchHeads);
        for (String b : sorted.keySet()) {
            if (b.equals(currentbranch)) {
                System.out.println("*" + b);
            } else {
                System.out.println(b);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        for (String b : staged.getContents().keySet()) {
            System.out.println(b);
        }
        System.out.println();
        System.out.println("=== Removed Files ===");
        for (String b : untracked) {
            System.out.println(b);
        }
        System.out.println();
        System.out.println("=== Modifications Not Staged For Commit ===");
        for (String file : current.getContents().keySet()) {
            if (workingdir.contains(file)) {
                Blob temp = new Blob(file);
                if (!temp.gethash().equals(current.getContents().get(
                        file)) && !staged.contains(file)) {
                    System.out.println(file + " (modified)");
                }
            } else {
                if (!untracked.contains(file)) {
                    System.out.println(file + " (deleted)");
                }
            }
        }
        for (String file : staged.getContents().keySet()) {
            if (workingdir.contains(file)) {
                Blob temp = new Blob(file);
                if (!temp.gethash().equals(
                        staged.getContents().get(file))) {
                    System.out.println(file + " (modified)");
                }
            } else {
                System.out.println(file + " (deleted)");
            }
        }
        System.out.println();
        System.out.println("=== Untracked Files ===");
        for (String b : workingdir) {
            if (!tracked.contains(b) && !staged.contains(b)) {
                System.out.println(b);
            }
        }
        System.out.println();
    }

    /**
     * This will change the file to the file we want.
     *
     * @param name   the name of the file
     * @param commit the commit of the file
     */
    public void checkout(String name, String commit) {
        int length = head.length();
        if (commit.length() >= length) {
            File temp = new File(rootdir + commit);
            if (!temp.exists()) {
                throw Utils.error("No commit with that id exists.");
            }
            Commit header = Utils.readObject(temp, Commit.class);
            String hash = header.getContents().get(name);
            if (header.getContents().containsKey(name)) {
                temp = new File(rootdir + hash);
                Blob blob = Utils.readObject(temp, Blob.class);
                temp = new File(name);
                Utils.writeContents(temp, blob.getBContents());
            } else {
                throw Utils.error("File does not exist in that commit.");
            }
        } else if (commit.length() < length) {
            boolean found = false;
            for (String b : commits) {
                if (commit.equals(b.substring(0, commit.length()))) {
                    checkout(name, b);
                    found = true;
                }
            }
            if (!found) {
                throw Utils.error("No commit with that id exists.");
            }
        }
    }

    /**
     * This is the way to checkout file in the current commit.
     *
     * @param name the name of the current commit
     */
    public void checkoutn(String name) {
        checkout(name, head);
    }

    /**
     * This is how we checkout a branch.
     *
     * @param branchName the name of the branch
     */
    public void checkoutb(String branchName) {
        branchName = branchName.replace("/", File.separator);
        if (branchHeads.keySet().contains(branchName)) {
            if (branchName.equals(currentbranch)) {
                throw Utils.error("No need to checkout the current branch.");
            } else {
                File cur = new File(
                        rootdir + branchHeads.get(branchName));
                Commit branch = Utils.readObject(cur, Commit.class);
                HashMap<String, String> files = branch.getContents();
                if (!staged.getContents().isEmpty()) {
                    throw Utils.error("You have uncommitted changes.");
                }
                List<String> workingdir = Utils.plainFilenamesIn(
                        workingdirectory);
                for (String ter : workingdir) {
                    if (!tracked.contains(ter)
                            && files.keySet().contains(ter)) {
                        throw Utils.error("There "
                                + "is an untracked file in the "
                                + "way; delete it or add it first.");
                    }
                }
                for (String c : tracked) {
                    if (!files.containsKey(c)) {
                        Utils.restrictedDelete(c);
                    }
                }
                tracked.clear();
                for (String b : files.keySet()) {
                    String hash = files.get(b);
                    Blob c = Utils.readObject(
                            new File(rootdir + hash), Blob.class);
                    Utils.writeContents(new File(b), c.getBContents());
                    tracked.add(b);
                }
                currentbranch = branchName;
                head = branchHeads.get(branchName);
                staged.clear();
            }
        } else {
            throw Utils.error("No such branch exists.");
        }
    }

    /**
     * This is how we add a branch.
     *
     * @param bname the name of the branch
     */
    public void branch(String bname) {
        if (branches.contains(bname)) {
            throw Utils.error("branch with that name already exists.");
        } else {
            branchHeads.put(bname, head);
            branches.add(bname);
        }
    }

    /**
     * This removes a branch and its corresponding head.
     *
     * @param bname the name of the branch to remove
     */
    public void removebranch(String bname) {
        if (!branches.contains(bname)) {
            throw Utils.error("A branch with that name does not exist.");
        } else if (bname.equals(currentbranch)) {
            throw Utils.error("Cannot remove the current branch.");
        } else {
            branchHeads.remove(bname);
            branches.remove(bname);
        }
    }

    /**
     * This is how we reset to a previous commit.
     *
     * @param commmit a string for hash value of commit to reset to
     */
    public void reset(String commmit) {
        if (commits.contains(commmit)) {
            Commit temp = Utils.readObject(new File(
                    rootdir + commmit), Commit.class);
            HashMap<String, String> files = temp.getContents();
            List<String> workingdir = Utils.plainFilenamesIn(workingdirectory);
            for (String ter : workingdir) {
                if (!tracked.contains(ter) && files.keySet().contains(ter)) {
                    throw Utils.error("There "
                            + "is an untracked file in "
                            + "the way; delete it or add it first.");
                }
            }
            for (String ter : workingdir) {
                if (tracked.contains(ter) && !files.keySet().contains(ter)) {
                    new File(ter).delete();
                }
            }
            tracked.clear();
            for (String b : files.keySet()) {
                tracked.add(b);
                checkout(b, commmit);
            }
            staged.clear();
            head = commmit;
            branchHeads.put(currentbranch, commmit);
        } else {
            throw Utils.error("No commit with that id exists.");
        }
    }

    /**
     * This is a helper function for reaffirm.
     *
     * @param first  the first
     * @param branch the branch
     * @param total  the total
     */
    public void reaffirm(String first, String branch, int total) {
        Commit temp = Utils.readObject(
                Utils.join(rootdir, first), Commit.class);
        if (temp.getbranch().equals(branch)) {
            if (total < lowest) {
                lowest = total;
                globalances = first;
            }
        } else if (temp.getParent() != null) {
            if (temp.getSParent() != null) {
                reaffirm(temp.getSParent(), branch, total + 1);
            }
            reaffirm(temp.getParent(), branch, total + 1);
        }
    }

    /**
     * This is an ancestor locator.
     *
     * @param fir     the first
     * @param sec     the second
     * @param branch1 the branch1
     * @param branch2 the branch2
     * @return the String
     */
    public String ancestorlocater(
            String fir, String sec, String branch1, String branch2) {
        reaffirm(fir, branch2, 0);
        reaffirm(sec, branch1, 0);
        return globalances;
    }

    /**
     * This is a helper function.
     *
     * @param allfiles    this is allfiles
     * @param givenval    the given values
     * @param ancestorval the ancestor values
     * @param currentval  the current values
     * @param bname       the branch name
     * @param given       the commit of the given
     * @return boolean whether it was a conflict
     */

    public boolean helperfuncmerge(HashSet<String> allfiles,
                                   HashMap<String, String> givenval,
                                   HashMap<String, String> ancestorval,
                                   HashMap<String, String> currentval,
                                   String bname,
                                   Commit given) {
        boolean conflict = false;
        for (String c : allfiles) {
            if (givenval.containsKey(c)) {
                if (ancestorval.get(c) != null && !givenval.get(
                        c).equals(ancestorval.get(c))
                        && currentval.get(c).equals(ancestorval.get(c))) {
                    checkout(c, given.gethash());
                    staged.add(c);
                } else if (!ancestorval.containsKey(c)
                        && !currentval.containsKey(c)) {
                    checkout(c, given.gethash());
                    staged.add(c);
                } else if (currentval.containsKey(c)
                        && !currentval.get(c).equals(givenval.get(c))) {
                    Blob fir = Utils.readObject(
                            Utils.join(rootdir, currentval.get(c)),
                            Blob.class);
                    Blob sec = Utils.readObject(
                            Utils.join(rootdir, givenval.get(c)),
                            Blob.class);
                    String concat = "<<<<<<< HEAD\n"
                            + fir.getSContents()
                            + "=======\n" + sec.getSContents()
                            + ">>>>>>>\n";
                    Utils.writeContents(new File(c), concat);
                    add(c);
                    conflict = true;
                } else if (!currentval.containsKey(c)
                        && !givenval.get(c).equals(ancestorval.get(c))) {
                    Blob sec = Utils.readObject(
                            Utils.join(rootdir, givenval.get(c)),
                            Blob.class);
                    String concat = "<<<<<<< HEAD\n"
                            + "=======\n" + sec.getSContents()
                            + ">>>>>>>\n";
                    Utils.writeContents(new File(c), concat);
                    add(c);
                    conflict = true;
                }

            } else {
                if (ancestorval.containsKey(c)) {
                    if (currentval.containsKey(c) && currentval.get(c).equals(
                            ancestorval.get(c))) {
                        new File(c).delete();
                        untracked.add(c);
                    } else if (currentval.containsKey(c)
                            && !currentval.get(c).equals(
                            ancestorval.get(c))) {
                        anotherhelper(currentval, bname, c);
                        conflict = true;
                    }
                }
            }
        }
        return conflict;
    }

    /**
     * This is how this works.
     *
     * @param currentval a hashmap
     * @param bname      the name of a branch
     * @param c          the name of value
     */
    private void anotherhelper(HashMap<String, String> currentval,
                               String bname,
                               String c) {
        Blob fir = Utils.readObject(
                Utils.join(rootdir, currentval.get(c)),
                Blob.class);
        String concat = "<<<<<<< HEAD\n"
                + fir.getSContents()
                + "=======\n"
                + ">>>>>>>\n";
        Utils.writeContents(new File(c), concat);
        add(c);
    }

    /**
     * This is the merge function.
     * @param bname the name of the branch to merge
     */
    public void merge(String bname) {
        String givenhash = branchHeads.get(bname);
        String currenthash = branchHeads.get(currentbranch);
        String ances = ancestorlocater(
                givenhash, currenthash, bname, currentbranch);
        Commit ancestor = Utils.readObject(
                Utils.join(rootdir, ances), Commit.class);
        Commit given = Utils.readObject(
                Utils.join(rootdir, givenhash), Commit.class);
        Commit current = Utils.readObject(
                Utils.join(rootdir, currenthash), Commit.class);
        if (ancestor.gethash().equals(givenhash)) {
            throw Utils.error(
                    "Given branch is an ancestor of the current branch");
        } else if (ancestor.gethash().equals(currenthash)) {
            branchHeads.put(currentbranch, givenhash);
            throw Utils.error("Current branch fast-forwarded.");
        }
        lowest = Integer.MAX_VALUE;
        globalances = head;
        HashSet<String> allfiles = new HashSet<String>();
        for (String b : ancestor.getContents().keySet()) {
            allfiles.add(b);
        }
        for (String b : given.getContents().keySet()) {
            allfiles.add(b);
        }
        for (String b : current.getContents().keySet()) {
            allfiles.add(b);
        }
        HashMap<String, String> givenval = given.getContents();
        HashMap<String, String> currentval = current.getContents();
        HashMap<String, String> ancestorval = ancestor.getContents();
        boolean conflict = helperfuncmerge(allfiles, givenval, ancestorval,
                currentval, bname, given);
        if (conflict) {
            System.out.println("Encountered a merge conflict.");
        }
        HashMap<String, String> staging = staged.getContents();
        HashMap<String, String> curr = new HashMap<String, String>();
        for (String name : allfiles) {
            if (staging.containsKey(name)) {
                tracked.add(name);
                curr.put(name, staging.get(name));
            } else if (!untracked.contains(name)
                    && currentval.containsKey(name)) {
                tracked.add(name);
                curr.put(name, currentval.get(name));
            }
        }
        Commit merged = new Commit(
                current.gethash(), given.gethash(), curr, currentbranch, bname);
        staged = new Stage();
        String name = merged.gethash();
        branchHeads.put(currentbranch, name);
        commits.add(name);
        head = name;
        Utils.writeObject(Utils.join(rootdir, name), merged);
        untracked.clear();
    }
    /**
     * Gets the head.
     * @return head
     */
    public String gethead() {
        return head;
    }

    /**
     * This is the commit.
     *
     * @param temp   the name of commit to put
     * @param branch the name of the branch
     * @param location the name of the location
     */
    public void addcommit(Commit temp, String branch, String location) {
        for (String blob : temp.getContents().keySet()) {
            Blob temporary = Utils.readObject(Utils.join(
                    location + File.separator,
                    temp.getContents().get(blob)), Blob.class);
            Utils.writeObject(Utils.join(rootdir,
                    temp.getContents().get(blob)), temporary);
        }
        String name = temp.gethash();
        branchHeads.put(branch, name);
        commits.add(name);
        File loc = new File(rootdir + name);
        Utils.writeObject(loc, temp);
    }
    /**
     * This is the commit.
     *
     * @param temp   the name of commit to put
     * @param branch the name of the branch
     * @param location the name of the location
     */
    public void addcommit2(Commit temp, String branch, String location) {
        for (String blob: temp.getContents().keySet()) {
            Blob temporary = Utils.readObject(
                    Utils.join(rootdir, temp.getContents().get(blob)),
                    Blob.class);
            Utils.writeObject(Utils.join(location + File.separator,
                    temp.getContents().get(blob)), temporary);
        }
        String name = temp.gethash();
        Gitlet remote = Utils.readObject(Utils.join(
                location + File.separator, "gitlet"), Gitlet.class);
        remote.branchHeads.put(branch, name);
        remote.commits.add(name);
        remote.head = name;
        File loc = new File(location + File.separator + name);
        Utils.writeObject(loc, temp);
        Utils.writeObject(Utils.join(
                location + File.separator, "gitlet"), remote);
    }

    /**
     * Adds a remote branch.
     *
     * @param name     the name of branch
     * @param location th name of location
     */
    public void addremote(String name, String location) {
        if (remoterepos.containsKey(name)) {
            throw Utils.error("A remote with that name already exists.");
        }
        location.replace("/", java.io.File.separator);
        remoterepos.put(name, location);
    }

    /**
     * Removes the remote.
     *
     * @param name removes remote with name
     */
    public void removeremote(String name) {
        if (remoterepos.containsKey(name)) {
            remoterepos.remove(name);
        } else {
            throw Utils.error("A remote with that name does not exist.");
        }
    }

    /**
     * pushes file to the remote location.
     *
     * @param name   the name of remote
     * @param branch the name of the remote branch
     */
    public void push(String name, String branch) {
        File location;
        if (remoterepos.containsKey(name)) {
            location = new File(remoterepos.get(name));
        } else {
            throw Utils.error("Remote directory not found.");
        }
        if (location.exists()) {
            Gitlet remoterepo = Utils.readObject(
                    Utils.join(remoterepos.get(name), "gitlet"),
                    Gitlet.class);
            String remotehead = remoterepo.gethead();
            boolean found = false;
            String tempo = head;
            ArrayList<Commit> tobecommited = new ArrayList<Commit>();
            while (tempo != null) {
                Commit header = Utils.readObject(
                        Utils.join(rootdir, tempo), Commit.class);
                if (tempo.equals(remotehead)) {
                    found = true;
                    break;
                }
                tobecommited.add(header);
                tempo = header.getParent();
            }
            if (found) {
                if (!remoterepo.branches.contains(branch)) {
                    remoterepo.branch(branch);
                }
                if (!remoterepo.currentbranch.equals(branch)) {
                    remoterepo.checkoutb(branch);
                }
                for (int i = tobecommited.size(); i > 0; i--) {
                    remoterepo.addcommit2(
                            tobecommited.get(i - 1), branch, remoterepos.get(
                                    name));
                }
                remoterepo.reset(remoterepo.branchHeads.get(branch));
            } else {
                throw Utils.error(""
                        + "Please pull down remote changes before pushing.");
            }
        } else {
            throw Utils.error("Remote directory not found.");
        }
    }

    /**
     * This is a way to deal with remote and branch.
     *
     * @param remote the remote name
     * @param branch the remote branch
     */
    public void fetch(String remote, String branch) {
        if (remoterepos.containsKey(remote)
                && new File(remoterepos.get(remote)).exists()) {
            Gitlet remoterepo = Utils.readObject(
                    Utils.join(remoterepos.get(remote), "gitlet"),
                    Gitlet.class);
            if (remoterepo.branches.contains(branch)) {
                String name = remote + File.separator + branch;
                if (!branches.contains(name)) {
                    branch(name);
                    branchHeads.put(name, null);
                }
                Stack<Commit> allcomits = new Stack<Commit>();
                String remotehead = remoterepo.branchHeads.get(branch);
                while (remotehead != null
                        && !remotehead.equals(branchHeads.get(name))) {
                    Commit remotecommit = Utils.readObject(
                            Utils.join(remoterepos.get(
                                    remote), remotehead), Commit.class
                    );
                    allcomits.push(remotecommit);
                    remotehead = remotecommit.getParent();
                }
                while (!allcomits.isEmpty()) {
                    addcommit(allcomits.pop(), name, remoterepos.get(remote));
                }
            } else {
                throw Utils.error(" That remote does not have that branch.");
            }
        } else {
            throw Utils.error("Remote directory not found.");
        }
    }

    /**
     * This is a way to deal with remote and branch.
     *
     * @param remote the remote name
     * @param branch the remote branch
     */
    public void pull(String remote, String branch) {
        fetch(remote, branch);
        merge(remote + File.separator + branch);
    }
}
