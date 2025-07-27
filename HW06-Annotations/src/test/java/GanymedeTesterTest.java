import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sinara.ganymede.engine.GanymedeTester;
import ru.sinara.ganymede.engine.Summary;

public class GanymedeTesterTest {

    GanymedeTester ganymedeTester;

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

        Summary summary = ganymedeTester.runTests("ru.sinara.ganymede.demo.IdealAppTest");
        assertThat(summary.getTotal()).isEqualTo(2);
        assertThat(summary.getPassed()).isEqualTo(2);
        assertThat(summary.getFailed()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test failed on BeforeEach stage")
    void failedOnSetUp() throws ClassNotFoundException {

        Summary summary = ganymedeTester.runTests("ru.sinara.ganymede.demo.FailSetupTest");
        assertThat(summary.getTotal()).isEqualTo(2);
        assertThat(summary.getPassed()).isEqualTo(0);
        assertThat(summary.getFailed()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test failed on AfterEach stage")
    void failedOnTearDown() throws ClassNotFoundException {

        Summary summary = ganymedeTester.runTests("ru.sinara.ganymede.demo.FailTearDownTest");
        assertThat(summary.getTotal()).isEqualTo(2);
        assertThat(summary.getPassed()).isEqualTo(0);
        assertThat(summary.getFailed()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test failed on TestMethod stage")
    void failedOnTestMethod() throws ClassNotFoundException {

        Summary summary = ganymedeTester.runTests("ru.sinara.ganymede.demo.FailTestMethodTest");
        assertThat(summary.getTotal()).isEqualTo(3);
        assertThat(summary.getPassed()).isEqualTo(2);
        assertThat(summary.getFailed()).isEqualTo(1);
    }
}
