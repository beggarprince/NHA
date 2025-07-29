import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SimpleTest {

    @Test
    public void testBasicMath() {
        int result = 2 + 2;
        Assertions.assertEquals(4, result, "2 + 2 should equal 4");
    }

    @Test
    public void testStringOperations() {
        String greeting = "Hello" + " " + "World";
        Assertions.assertEquals("Hello World", greeting);
        Assertions.assertTrue(greeting.contains("World"));
        Assertions.assertFalse(greeting.isEmpty());
    }

    @Test
    public void testBooleanLogic() {
        boolean isTrue = true;
        boolean isFalse = false;

        Assertions.assertTrue(isTrue);
        Assertions.assertFalse(isFalse);
        Assertions.assertNotEquals(isTrue, isFalse);
    }

    @Test
    public void testArrayOperations() {
        int[] numbers = {1, 2, 3, 4, 5};

        Assertions.assertEquals(5, numbers.length);
        Assertions.assertEquals(1, numbers[0]);
        Assertions.assertEquals(5, numbers[4]);
    }
}