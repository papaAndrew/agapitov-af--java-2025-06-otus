import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sinara.ganymede.engine.GanymedeTester;
import ru.sinara.ganymede.engine.TestResults;

public class GanymedeTesterTest {

    private GanymedeTester ganymedeTester;

    @BeforeEach
    void beforeEach() {
        ganymedeTester = GanymedeTester.getInstance();
    }
    //    @AfterEach
    void afterEach() {
        throw new RuntimeException("Crushed");
    }

    @Test
    @DisplayName("Ideal App test")
    void idealTest() throws ClassNotFoundException {
        GanymedeTester ganymedeTester = GanymedeTester.getInstance();

        TestResults testResults = ganymedeTester.runTests("ru.sinara.ganymede.demo.IdealAppTest");
        assertThat(testResults.getTotal()).isEqualTo(2);
        assertThat(testResults.getPassed()).isEqualTo(2);
        assertThat(testResults.getFailed()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test failed on BeforeEach stage")
    void failedOnSetUp() throws ClassNotFoundException {

        TestResults testResults = ganymedeTester.runTests("ru.sinara.ganymede.demo.FailSetupTest");
        assertThat(testResults.getTotal()).isEqualTo(2);
        assertThat(testResults.getPassed()).isEqualTo(0);
        assertThat(testResults.getFailed()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test failed on AfterEach stage")
    void failedOnTearDown() throws ClassNotFoundException {

        TestResults testResults = ganymedeTester.runTests("ru.sinara.ganymede.demo.FailTearDownTest");
        assertThat(testResults.getTotal()).isEqualTo(2);
        assertThat(testResults.getPassed()).isEqualTo(0);
        assertThat(testResults.getFailed()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test failed on TestMethod stage")
    void failedOnTestMethod() throws ClassNotFoundException {

        TestResults testResults = ganymedeTester.runTests("ru.sinara.ganymede.demo.FailTestMethodTest");
        assertThat(testResults.getTotal()).isEqualTo(3);
        assertThat(testResults.getPassed()).isEqualTo(2);
        assertThat(testResults.getFailed()).isEqualTo(1);
    }
}
