
import edu.uiowa.cs.similarity.SimM;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    public MainTest() {
    }

    @Test
    public void testEuc() {
        SimM simm = new SimM("euc");
        HashMap<String, Double> temp1 = new HashMap<>();
        HashMap<String, Double> temp2 = new HashMap<>();
        temp1.put("1", 1.0);
        temp1.put("2", 4.0);
        temp1.put("3", 1.0);
        temp1.put("4", 0.0);
        temp1.put("5", 0.0);
        temp1.put("6", 0.0);
        temp2.put("1", 3.0);
        temp2.put("2", 0.0);
        temp2.put("3", 0.0);
        temp2.put("4", 1.0);
        temp2.put("5", 1.0);
        temp2.put("6", 2.0);
        double result = simm.similarity(temp1, temp2);
        assertEquals(-5.1961524227, result, 0.00001);
    }

    @Test
    public void testEucnorm() {
        SimM simm = new SimM("eucnorm");
        HashMap<String, Double> temp1 = new HashMap<>();
        HashMap<String, Double> temp2 = new HashMap<>();
        temp1.put("1", 1.0);
        temp1.put("2", 4.0);
        temp1.put("3", 1.0);
        temp1.put("4", 0.0);
        temp1.put("5", 0.0);
        temp1.put("6", 0.0);
        temp2.put("1", 3.0);
        temp2.put("2", 0.0);
        temp2.put("3", 0.0);
        temp2.put("4", 1.0);
        temp2.put("5", 1.0);
        temp2.put("6", 2.0);
        double result = simm.similarity(temp1, temp2);
        assertEquals(-1.27861316602, result, 0.00001);
    }
}
