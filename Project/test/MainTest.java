
import edu.uiowa.cs.similarity.TopjCommand;
import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {

    public MainTest() {
    }

    @Test
    public void testEuc() {
        TopjCommand Top = new TopjCommand("euc");
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
        double result = Top.euc(temp1, temp2);
        assertEquals(-5.1961524227, result, 0.00001);
    }

    @Test
    public void testEucnorm() {
        TopjCommand Top = new TopjCommand("euc");
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
        double result = Top.eucnorm(temp1, temp2);
        assertEquals(-1.27861316602, result, 0.00001);
    }
//
//    @Test
//    public void reduceAtDepthTest() {
//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.insertNode(50);
//        bt.insertNode(2);
//        bt.insertNode(34);
//        bt.insertNode(19);
//        bt.insertNode(6);
//        bt.insertNode(22);
//        int sum = bt.reduceAtDepth(2, new IntegerSum());
//        assertEquals(47, sum);
//    }
//
//    @Test
//    public void reduceAtDepthTest2() {
//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.insertNode(50);
//        bt.insertNode(2);
//        bt.insertNode(34);
//        bt.insertNode(19);
//        bt.insertNode(6);
//        bt.insertNode(22);
//        int sum = bt.reduceAtDepthRecursive(2, new IntegerSum());
//        assertEquals(47, sum);
//    }
//
//    @Test
//    public void selectIterativeTest() {
//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.insertNode(50);
//        bt.insertNode(2);
//        bt.insertNode(34);
//        bt.insertNode(19);
//        bt.insertNode(6);
//        bt.insertNode(22);
//        Integer[] expected = {50, 2, 6, 34, 22};
//        List<Integer> answers = bt.selectIterative(new IsEven());
//        assertArrayEquals(expected, answers.toArray());
//    }
//
//    // Example implementation of ReduceFunction used by the given test
//    private static class IntegerSum implements ReduceFunction<Integer, Integer> {
//
//        @Override
//        public Integer combine(Integer soFar, Integer x) {
//            return soFar + x;
//        }
//
//        @Override
//        public Integer initialValue() {
//            return 0;
//        }
//    }
//
//    // Example implementation of IsEven used by the given test
//    private static class IsEven implements Predicate<Integer> {
//
//        @Override
//        public boolean check(Integer data) {
//            return data % 2 == 0;
//        }
//    }
//
//    /* The staff will run your code on several additional JUnit tests of our own.
//		   You must write additional tests below to provide more evidence that your
//		   method implementations are correct. 
//		
//		   This test code is part of the assignment, just like the other code.
//
//		   If you write a new test and it fails, GREAT! That means you are making
//		   progress. Either the test is wrong and you just need to fix it, or it means you found
//		   a bug in your BinaryTree code and now you can go fix it. Don't remove good tests just
//		   because they fail.
//     */
//    // write your new tests below here, using the @Test methods above as an example.
//    // PART 2: testing
//    @Test
//    public void ReduceTest1() {
//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.insertNode(0);
//        bt.insertNode(11);
//        bt.insertNode(12);
//        bt.insertNode(21);
//        bt.insertNode(22);
//        bt.insertNode(23);
//        bt.insertNode(24);
//        bt.insertNode(31);
//        bt.insertNode(32);
//        bt.insertNode(30);
//        int sum = bt.reduceAtDepth(3, new ReduceTest());
//        assertEquals(32, sum);
//    }    
//    
//    @Test
//    public void ReduceTest2() {
//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.insertNode(0);
//        bt.insertNode(11);
//        bt.insertNode(12);
//        bt.insertNode(21);
//        bt.insertNode(22);
//        bt.insertNode(23);
//        bt.insertNode(24);
//        bt.insertNode(31);
//        bt.insertNode(32);
//        bt.insertNode(30);
//        int sum = bt.reduceAtDepthRecursive(3, new ReduceTest());
//        assertEquals(32, sum);
//    }
//   
//    private static class ReduceTest implements ReduceFunction<Integer, Integer> {
//
//        @Override
//        public Integer combine(Integer soFar, Integer x) {
//            if (soFar < x) {
//                return x;
//            } else {
//                return soFar;
//            }
//        }
//
//        @Override
//        public Integer initialValue() {
//            return Integer.MIN_VALUE;
//        }
//    }
//    // PART 4: testing
//    @Test
//    public void SelectTest1() {
//        BinaryTree<Integer> bt = new BinaryTree<>();
//        bt.insertNode(0);
//        bt.insertNode(11);
//        bt.insertNode(12);
//        bt.insertNode(21);
//        bt.insertNode(22);
//        bt.insertNode(23);
//        bt.insertNode(24);
//        bt.insertNode(31);
//        bt.insertNode(32);
//        bt.insertNode(30);
//        Integer[] expected = {0, 21, 30, 12, 24};
//        List<Integer> answers = bt.selectIterative(new SelectTest());
//        assertArrayEquals(expected, answers.toArray());
//    }
//
//    private static class SelectTest implements Predicate<Integer> {
//
//        @Override
//        public boolean check(Integer data) {
//            return data % 3 == 0;
//        }
//    }
}
