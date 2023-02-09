package decide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import static decide.FUVMethods.*;

public class FUVMethodsTest {
    
    /**
     * Given PUM and PUV, FUV method can compute the correct result of FUV, based on the rule in the project description.
     */
    @Test
    void testPositiveFUV() {
        boolean PUM[][] ={
            {true, false, true, false, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true}
        };
        boolean PUV[] = {true, false, true, true, true, true, true, true, true, true, true, true, true, true, true};
        boolean FUV[] = {false, true, true, false, true, true, true, true, true, true, true, true, true, true, true};
        assertTrue(Arrays.equals(FUV, FUV(PUV, PUM)));
    }

    /** 
     * A negative test of FUV method. 
     */
    @Test
    void testNegativeFUV() {
        boolean PUM[][] ={
            {true, true, true, false, true, true, true, true, true, true, true, false, true, true, true},
            {false, true, true, true, true, true, true, false, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {false, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, false, true, true, false, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, false, true, true, true, true, true, false, true, true, true, true, true, true},
            {true, true, true, true, true, true, false, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, false, true, true, true, true, true, true, true, false, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true}
        };
        boolean PUV[] = {true, false, true, true, true, true, true, false, true, true, true, true, false, true, true};
        boolean FUV[] = {false, true, true, true, true, false, true, true, true, true, true, true, true, true, true};
        assertFalse(Arrays.equals(FUV, FUV(PUV, PUM)));
    }
}
