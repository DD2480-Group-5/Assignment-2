package decide;

import static decide.CMVMethods.NUMLIC;

public class FUVMethods {
    // Implement the methods for section 2.3 in this class

    /**
     * Method used to generate Final Unlocking Vector (FUV) given PUV and PUM, based on section 2.3.
     * 
     * @param PUV : Preliminary Unlocking Vector
     * @param PUM : Preliminary Unlocking Matrix
     * @return FUV
     */
    public static boolean[] FUV(boolean[] PUV, boolean[][] PUM) {
        assert (PUV.length == NUMLIC && PUM.length == NUMLIC && PUM[0].length == NUMLIC);

        boolean[] FUV = new boolean[NUMLIC];
        
        for (int i = 0; i < NUMLIC; i++) {
            if (PUV[i]) {
                FUV[i] = true;
                for (int j = 0; j < NUMLIC; j++) {
                    if (i == j) continue;
                    if (!PUM[i][j]) {
                        FUV[i] = false;
                        break;
                    }
                }
            }
            else {
                FUV[i] = true;
            }
        }
        System.out.println(FUV);
        return FUV;
    }
}
