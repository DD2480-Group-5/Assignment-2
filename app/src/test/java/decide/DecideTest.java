package decide;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;
import java.util.Arrays;

import static decide.Connector.*;
import static decide.CMVMethods.*;
import static decide.PUMMethods.*;
import static decide.FUVMethods.*;
import static decide.LAUNCHMethods.*;

public class DecideTest {

    /**
     * Given all the required input to the DECIDE program, the DECIDE program is functionally correct, according to outputs it produces.
     */
    @Test
    void testPositiveDecide() {
        double  LENGTH1 = 1.0;
        double  RADIUS1 = 1.0;
        double  EPSILON = 1.0;
        double  AREA1   = 1.0;
        int     Q_PTS   = 1;
        int     QUADS   = 1;
        double  DIST    = 1.0;
        int     N_PTS   = 1;
        int     K_PTS   = 1;
        int     A_PTS   = 1;
        int     B_PTS   = 1;
        int     C_PTS   = 1;
        int     D_PTS   = 1;
        int     E_PTS   = 1;
        int     F_PTS   = 1;
        int     G_PTS   = 1;
        double  LENGTH2 = 1.0;
        double  RADIUS2 = 1.0;
        double  AREA2   = 1.0;

        Connector[][] LCM = {
            {ANDD, ANDD, ANDD, ANDD, ANDD, ANDD, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, ANDD, NOTUSED, NOTUSED, ANDD, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, ANDD, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, ANDD, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, ANDD, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED},
            {NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, NOTUSED, ORR}
        };
        boolean[] PUV = {false, true, true, true, false, true, true, true, true, true, true, true, true, true, true};
        Point2D[] points = {
            new Point2D.Double(0, 0), 
            new Point2D.Double(1, 0), 
            new Point2D.Double(0, 1), 
            new Point2D.Double(10, 10), 
            new Point2D.Double(-99.9, -99.9)
        };

        boolean[] CMV = new boolean[] {
            CMV_0(points, LENGTH1),
            CMV_1(points, RADIUS1),
            CMV_2(points, EPSILON),
            CMV_3(points, AREA1),
            CMV_4(points, Q_PTS, QUADS),
            CMV_5(points),
            CMV_6(points, N_PTS, DIST),
            CMV_7(points, K_PTS, LENGTH1),
            CMV_8(points, A_PTS, B_PTS, RADIUS1),
            CMV_9(points, C_PTS, D_PTS, EPSILON),
            CMV_10(points, E_PTS, F_PTS, AREA1),
            CMV_11(points, G_PTS),
            CMV_12(points, K_PTS, LENGTH1, LENGTH2),
            CMV_13(points, A_PTS, B_PTS, RADIUS1, RADIUS2),
            CMV_14(points, E_PTS, F_PTS, AREA1, AREA2)
        };

        boolean[][] PUM = PUM(CMV, LCM);
        boolean[] FUV = FUV(PUV, PUM);
        boolean launch = LAUNCH(FUV);

        System.out.println(CMV);
        boolean[] trueCMV = {true, true, true, true, false, true, false, true, true, true, true, true, false, false, false};
        boolean[][] truePUM = {
            {true, true, true, true, false, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, false, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, false, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
            {true, true, true, true, true, true, true, true, true, true, true, true, true, true, false}
        };
        boolean[] trueFUV = {true, true, true, true, true, true, true, true, true, true, true, true, true, true, true};

        assertTrue(Arrays.equals(CMV, trueCMV));
        assertTrue(Arrays.deepEquals(PUM, truePUM));
        assertTrue(Arrays.equals(FUV, trueFUV));
        assertTrue(launch);

        // Test Decide class
        Decide decide = new Decide(LENGTH1, RADIUS1, EPSILON, AREA1, Q_PTS, QUADS, DIST, N_PTS, K_PTS, A_PTS, B_PTS, C_PTS, D_PTS, E_PTS, F_PTS, G_PTS, LENGTH2, RADIUS2, AREA2, points, LCM, PUV);
        launch = decide.evaluateLaunch();
        assertTrue(launch);
    }

    /**
     * A negative test of DECIDE.
     */
    @Test
    void testNegativeDecide() {
        Point2D[] points = new Point2D[] {
            new Point2D.Double(2.0, 2.0),
            new Point2D.Double(1.0, 1.0),
            new Point2D.Double(-1.0, 1.0),
            new Point2D.Double(-1.0, -1.0),
            new Point2D.Double(1.0, -1.0),
        };

        double EPSILON = 0.785;
        double LENGTH1 = 1.0;
        double LENGTH2 = 2.0;
        double RADIUS1 = 2.0;
        double RADIUS2 = 1.0;
        double AREA1 = 1.0;
        double AREA2 = 2.0;
        double DIST = 2.0;

        int Q_PTS = 2;
        int QUADS = 3;
        int N_PTS = 3;
        int K_PTS = 1;
        int A_PTS = 1;
        int B_PTS = 1;
        int C_PTS = 1;
        int D_PTS = 1;
        int E_PTS = 1;
        int F_PTS = 1;
        int G_PTS = 2;

        Connector[][] LCM = new Connector[15][15];

        for (int i = 0; i < LCM.length; i++) {
            for (int j = 0; j < LCM.length; j++) {
                LCM[i][j] = ANDD;
            }
        }

        boolean[] PUV = new boolean[15];
        for (int i = 0; i < PUV.length; i++) {
            PUV[i] = true;
        }

        Decide decide = new Decide(LENGTH1, RADIUS1, EPSILON, AREA1, Q_PTS, QUADS, DIST, N_PTS, K_PTS,
            A_PTS, B_PTS, C_PTS, D_PTS, E_PTS, F_PTS, G_PTS, LENGTH2, RADIUS2, AREA2, points, LCM, PUV);

        boolean launch = decide.evaluateLaunch();
        assertFalse(launch);
    }
}
