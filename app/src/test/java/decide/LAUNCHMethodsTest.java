package decide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class LAUNCHMethodsTest {

    /**
     * Given FUV, LAUNCH method can compute the correct result of LAUNCH, based on the rule in the project description.
     */
    @Test
    void testPostiveLAUNCH() {
        boolean[] fuv = new boolean[15];
        Arrays.fill(fuv, true);
        assertTrue(LAUNCHMethods.LAUNCH(fuv));
    }

    /** 
     * A negative test of LAUNCH method.
     */
    @Test
    void testNegativeLAUNCH() {
        boolean[] fuv = new boolean[15];
        Arrays.fill(fuv, true);
        fuv[0] = false;
        assertFalse(LAUNCHMethods.LAUNCH(fuv));
    }
}
