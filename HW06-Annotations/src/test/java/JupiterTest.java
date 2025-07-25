import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JupiterTest {

    @BeforeAll
    static void setUp() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Jupiter test");
        }
    }

    @Test
    void test() {}

    @AfterAll
    static void tearDown() {

        System.out.println("Jupiter test");
    }
}
