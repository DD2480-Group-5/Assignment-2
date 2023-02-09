package decide;

public class LAUNCHMethods {
    public static boolean LAUNCH(boolean[] FUV) {
        assert FUV.length == CMVMethods.NUMLIC;

        for (boolean b : FUV) {
            if (!b) return false;
        }
        return true;
    }
}
