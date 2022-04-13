import java.io.File;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        File i = new File(args[0]);
        File o = new File("output.hack");
        Parser p = new Parser();
        p.parse(i, o);
    }
}
