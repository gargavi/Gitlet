package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import java.io.IOException;


/** The suite of all JUnit tests for the gitlet package.
 *  @author avigarg
 */
public class UnitTest {

    /** Run the JUnit tests in the loa package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    /** A dummy test to avoid complaint. */
    @Test
    public void addTest() throws IOException {
        try {
            Main.main("init");
        } catch (GitletException expr) {
            System.out.println(expr);
        }
        Main.main("add", "somet.txt");
        Main.main("commit", "this be the way");
        Main.main("add", "hello.txt");
        Main.main("branch", "nayan");
        Main.main("log");
        Main.main("status");
        Main.main("checkout", "nayan");
        Main.main("status");

    }
    @Test
    public void tryThis() {
        Commit he = new Commit();
        System.out.println(he.getSParent());
    }
}


