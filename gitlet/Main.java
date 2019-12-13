package gitlet;

import java.io.File;
import java.io.IOException;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Avi Garg
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) throws IOException {
        File gitlet = new File(".gitlet", "gitlet");
        Gitlet repo;
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        if (gitlet.exists()) {
            repo = Utils.readObject(gitlet, Gitlet.class);
        } else {
            repo = new Gitlet();
            if (!args[0].equals("init")) {
                System.out.println("Not in an initialized Gitlet directory.");
                System.exit(0);
            }
        }
        try {
            repo.run(args);
        } catch (GitletException exp)  {
            System.out.println(exp.toString().substring(10 + 10 + 4));
            System.exit(0);
        }
        Utils.writeObject(gitlet, repo);
    }
}
