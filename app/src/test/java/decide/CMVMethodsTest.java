package decide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.geom.Point2D;

public class CMVMethodsTest {

    /**
     * The computed distance between the two points should be greater than 5.
     */
    @Test
    void testPositiveCmv0() {
        Point2D[] points = {new Point2D.Double(0, 0), new Point2D.Double(10, 10)};
        double len = 5.0;
        boolean b = CMVMethods.CMV_0(points, len);
        assertFalse(b);
    }

    /**
     * The computed distance between any two consecutive points should be less than 5.
     */
    @Test
    void testNegativeCmv0() {
        Point2D[] points = {new Point2D.Double(-2, -2), new Point2D.Double(0, 0), new Point2D.Double(2, 2)};
        double len = 5.0;
        boolean b = CMVMethods.CMV_0(points, len);
        assertFalse(b);
    }

    /**
     * There exists three consecutive points whose circumcircle has a radius greater than 1.
     */
    @Test
    void testPositiveCmv1() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(2, 0),
                new Point2D.Double(2, 2)
        };
        double radius = 1;
        boolean b = CMVMethods.CMV_1(points, radius);
        assertTrue(b);
    }

    /**
     * There're only 2 points. Not a valid input.
     */
    @Test
    void testNegativeCmv1() {
        Point2D[] points = {new Point2D.Double(0, 0), new Point2D.Double(2, 2)};
        double radius = 5;
        boolean b = CMVMethods.CMV_1(points, radius);
        assertFalse(b);
    }

    /**
     * There exists three consecutive points which form an angle such that angle < PI - 0.1.
     */
    @Test
    void testPositiveCmv2() {
        Point2D[] points = {
            new Point2D.Double(1.0, 0.0),
            new Point2D.Double(0.0, 0.0),
            new Point2D.Double(0.0, 1.0),
            new Point2D.Double(0.0, 0.0),
            new Point2D.Double(2.0, 0.0)
        };
        double epsilon = 0.1;
        boolean eval = CMVMethods.CMV_2(points, epsilon);
        assertTrue(eval);
    }

    /**
     * There're no three consecutive points which form an angle such that angle < PI - 0.1.
     */
    @Test
    void testNegativeCmv2() {
        Point2D[] points = {
            new Point2D.Double(1.0, 0.0), 
            new Point2D.Double(0.0, 0.0),     
            new Point2D.Double(-1.0, 0.0), 
            new Point2D.Double(-1.0, 0.0),      
            new Point2D.Double(1.0, 0.0)
        };
        double epsilon = 0.1;
        boolean eval = CMVMethods.CMV_2(points, epsilon);
        assertFalse(eval);
    }

    /**
     * There exists three consecutive points such that area of the triangle formed by which is greater than 8.
     */
    @Test 
    void testPositiveCmv3() {
        Point2D[] points = {
            new Point2D.Double(0.0, 0.0),
            new Point2D.Double(6.0, 0.0),
            new Point2D.Double(3.0, 3.0)
        };

        boolean eval = CMVMethods.CMV_3(points, 8.0);
        assertTrue(eval);
    }

    /**
     * There're no three consecutive points such that area of the triangle formed by which is greater than 5.5.
     */
    @Test 
    void testNegativeCmv3() {
        Point2D[] points = {
            new Point2D.Double(1.0, 0.0),
            new Point2D.Double(3.0, 2.0),
            new Point2D.Double(0.0, 3.0)
        };

        boolean eval = CMVMethods.CMV_3(points, 5.5);
        assertFalse(eval);
    }

    /**
     * There exists 3 consecutive points that lie in more than 2 quadrants.
     */
    @Test
    void testPositiveCmv4() {
        Point2D[] points = {
            new Point2D.Double(-1.0, 1.0),
            new Point2D.Double(-0.5, 2.0),
            new Point2D.Double(0.2, -4),
            new Point2D.Double(-2.0, 4.0),
            new Point2D.Double(1.5, 2.5),
            new Point2D.Double(0.5, 5.0)
        };
        boolean eval = CMVMethods.CMV_4(points, 3, 2);
        assertTrue(eval);
    }

    /**
     * There're no 3 consecutive points that lie in more than 2 quadrants.
     */
    @Test
    void testNegativeCmv4() {
        Point2D[] points = {
            new Point2D.Double(-1.0, 1.0),
            new Point2D.Double(-0.5, 2.0),
            new Point2D.Double(-0.5, 4.0),
            new Point2D.Double(-2.5, 4.0), 
            new Point2D.Double(1.0, 2.0),
            new Point2D.Double(0.5, 5.0)
        };
        boolean eval = CMVMethods.CMV_4(points, 3, 2);
        assertFalse(eval);
    }

    /**
     * There exists two consecutive points, such that the x coordinate of the latter one is smaller than the previous one. 
     */
    @Test
    void testPositiveCmv5() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2)
        };
        boolean b = CMVMethods.CMV_5(points);
        assertTrue(b);
    }

    /**
     * There's only 1 point. Not a valid input.
     */
    @Test
    void testNegativeCmv5() {
        Point2D[] points = {new Point2D.Double(0, 0)};
        boolean b = CMVMethods.CMV_5(points);
        assertFalse(b);
    }

    /**
     * There exists 4 consecutive points such that at least one point lies a distance greater than 1 from t he line joining the first and last of these 4 points.
     */
    @Test
    void testPositiveCmv6() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(1, 0),
                new Point2D.Double(2, 0),
                new Point2D.Double(3, 3),
                new Point2D.Double(4, 2)
        };
        int n_pts = 4;
        double dist = 1;
        boolean b = CMVMethods.CMV_6(points, n_pts, dist);
        assertTrue(b);
    }

    /**
     * N_PTS > NUMPOINTS. Not a valid input.
     */
    @Test
    void testNegativeCmv6() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2)
        };
        int n_pts = 5;
        double dist = 2;
        boolean b = CMVMethods.CMV_6(points, n_pts, dist);
        assertFalse(b);
    }

    /**
     * There exists two data points separated by exactly 3 consecutive intervening points that are a distance greater than 4.8 apart.
     */
    @Test
    void testPositiveCmv7() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 3),
                new Point2D.Double(-1, 0),
                new Point2D.Double(1, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(4, 0),
        };
        int k_pts = 3;
        double length1 = 4.8;
        assertTrue(CMVMethods.CMV_7(points, k_pts, length1));
    }

    /**
     * Test 1: There're no data points separated by exactly 3 consecutive intervening points that are a distance greater than 5 apart.
     * Test 2: K_PTS > NUMPOINTS - 2. Not a valid input.
     * Test 3: LENGTH < 0. Not a valid input.
     */
    @Test
    void testNegativeCmv7() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 3),
                new Point2D.Double(-1, 0),
                new Point2D.Double(1, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(4, 0),
        };
        int k_pts = 3;
        int k_pts_2 = 6;
        double length1 = 5;
        double length2 = -1;
        assertFalse(CMVMethods.CMV_7(points, k_pts, length1));
        assertFalse(CMVMethods.CMV_7(points, k_pts_2, length1));
        assertFalse(CMVMethods.CMV_7(points, k_pts, length2));
    }

    /**
     * There exists three points separated by exactly 1 and 1 consecutive intervening points, respectively, that radius of the circumcircle of which is larger than 2.
     */
    @Test
    void testPositiveCmv8() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(0, -2),
                new Point2D.Double(2, 0),
                new Point2D.Double(-2, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(4, 0),
        };
        int a_pts = 1, b_pts = 1;
        double radius2 = 2;
        assertTrue(CMVMethods.CMV_8(points, a_pts, b_pts, radius2));
    }

    /**
     * Test 1: There're no three points separated by exactly 1 and 1 consecutive intervening points, respectively, that radius of the circumcircle of which is larger than 5.
     * Test 2: A_PTS + B_PTS > NUMPOINTS - 3. Not a valid input.
     * Test 3: RADIUS1 < 0. Not a valid input.
     */
    @Test
    void testNegativeCmv8() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(0, -2),
                new Point2D.Double(2, 0),
                new Point2D.Double(-2, 0),
                new Point2D.Double(0, 1),
                new Point2D.Double(1, 0),
        };
        int a_pts = 1, b_pts = 1;
        int a_pts_f = 5;
        double radius2 = 2;
        double radius2_f = -2;
        assertFalse(CMVMethods.CMV_8(points, a_pts, b_pts, radius2));
        assertFalse(CMVMethods.CMV_8(points, a_pts_f, b_pts, radius2));
        assertFalse(CMVMethods.CMV_8(points, a_pts, b_pts, radius2_f));
    }

    /**
     * There exists three data poitns separated by exactly 1 and 1 consecutive intervening points, respectively, such that the angle formed by which is smaller than PI - 0.1 * PI. (The second points of the three points is the vertex of the angle.)
     */
    @Test
    void testPositiveCmv9() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(2, 0)
        };
        int c_pts = 1;
        int d_pts = 1;
        double epsilon = 0.1 * Math.PI;
        boolean b = CMVMethods.CMV_9(points, c_pts, d_pts, epsilon);
        assertTrue(b);
    }

    /**
     * C_PTS + D_PTS > NUMPOINTS - 3. Not a valid input.
     */
    @Test
    void testNegativeCmv9() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2)
        };
        int c_pts = 1;
        int d_pts = 4;
        double epsilon = 0.2;
        boolean b = CMVMethods.CMV_9(points, c_pts, d_pts, epsilon);
        assertFalse(b);
    }

    /**
     * There exists three data points separated by exactly 1 and 1 consecutive intervening points, respectively, that area of the triangle formed by which is greater than 1.
     */
    @Test
    void testPositiveCmv10() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(2, 0)
        };
        int e_pts = 1;
        int f_pts = 1;
        double area1 = 1;
        boolean b = CMVMethods.CMV_10(points, e_pts, f_pts, area1);
        assertTrue(b);
    }

    /**
     * AREA < 0. Not a valid input.
     */
    @Test
    void testNegativeCmv10() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2)
        };
        int e_pts = 1;
        int f_pts = 1;
        double area1 = -1;
        boolean b = CMVMethods.CMV_10(points, e_pts, f_pts, area1);
        assertFalse(b);
    }

    /**
     * There exists two points separated by exactly 2 consecutive intervening points, such that the x coordinate of the latter is less than the former.
     */
    @Test
    void testPositiveCmv11() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(-1, 0)
        };
        int g_pts = 2;
        assertTrue(CMVMethods.CMV_11(points, g_pts));
    }

    /**
     * Test 1: There're no two points separated by exactly 2 consecutive intervening points, such that the x coordinate of the latter is less than the former.
     * Test 2: G_PTS > NUMPOINTS - 2. Not a valid input.
     * Test 3: G_PTS > NUMPOINTS - 2. Not a valid input.
     */
    @Test
    void testNegativeCmv11() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(-1, 0)
        };
        Point2D[] points_2 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(2, 0)
        };
        Point2D[] points_3 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2)
        };
        int g_pts = 2;
        int g_pts_f = 5;
        assertFalse(CMVMethods.CMV_11(points, g_pts_f));
        assertFalse(CMVMethods.CMV_11(points_2, g_pts));
        assertFalse(CMVMethods.CMV_11(points_3, g_pts));
    }

    /**
     * There exists two points, seaprated by exactly 1 point, which are a distance greater than the length 3 apart. In addition, there exists two points, separated by exactly 1 point, that are a distance less than the length 2 apart.
     */
    @Test
    void testPositiveCmv12() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(5, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(4, 1)
        };
        int k_pts = 1;
        double length1 = 3, length2 = 2;
        assertTrue(CMVMethods.CMV_12(points, k_pts, length1, length2));
    }

    /**
     * Test 1: There'are no two points, seaprated by exactly 1 point, which are a distance greater than the length 6 apart. In addition, there exists two points, separated by exactly 1 point, that are a distance less than the length 2 apart.
     * Test 2: K_PTS > NUMPOINTS - 2. Not a valid input.
     * Test 3: K_PTS > NUMPOINTS - 2. Not a valid input.
     */
    @Test
    void testNegativeCmv12() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(5, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(4, 1)
        };
        Point2D[] points_2 = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2)
        };
        int k_pts = 1;
        int k_pts_f = 4;
        double length1 = 3, length2 = 2;
        double length1_f = 6;
        assertFalse(CMVMethods.CMV_12(points, k_pts, length1_f, length2));
        assertFalse(CMVMethods.CMV_12(points, k_pts_f, length1, length2));
        assertFalse(CMVMethods.CMV_12(points_2, k_pts, length1, length2));
    }

    /** 
     * There exists three points, separated by exactly 1 and 1 consecutive intervening points, respectively, that the radius of circumcircle of which has a radius larger than 1. In addition, there exists three points, separated by exactly 1 and 1 consecutive intervening points, respectively, that the radius of circumcircle of which has a radius smaller than 1.
     */
    @Test
    void testPositiveCmv13() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(2, 0),
                new Point2D.Double(3, 1)
        };
        int a_pts = 1;
        int b_pts = 1;
        double radius1 = 1;
        double radius2 = 2;
        boolean b = CMVMethods.CMV_13(points, a_pts, b_pts, radius1, radius2);
        assertTrue(b);
    }

    /**
     * A_PTS + B_PTS > NUMPOINTS - 3. Not a valid input.
     */
    @Test
    void testNegativeCmv13() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2)
        };
        int a_pts = 1;
        int b_pts = 1;
        double radius1 = 1;
        double radius2 = 1;
        boolean b = CMVMethods.CMV_13(points, a_pts, b_pts, radius1, radius2);
        assertFalse(b);
    }
    
    /**
     * There exists three data points, separated by exactly 1 and 1 consecutive intervening points, respectively, that area of the triangle formed by which is greater than 1. In addition, there exists three data points, separated by exactly 1 and 1 consecutive intervening points, respectively, that area of the triangle formed by which is less than 2.
     */
    @Test
    void testPositiveCmv14() {
        Point2D[] points = {
                new Point2D.Double(0, 2),
                new Point2D.Double(1, 1),
                new Point2D.Double(0, 0),
                new Point2D.Double(2, 2),
                new Point2D.Double(2, 0),
                new Point2D.Double(3, 1)
        };
        int e_pts = 1;
        int f_pts = 1;
        double area1 = 1;
        double area2 = 2;
        boolean b = CMVMethods.CMV_14(points, e_pts, f_pts, area1, area2);
        assertTrue(b);
    }

    /**
     * AREA1 < 0. Not a valid input. 
     */
    @Test
    void testNegativeCmv14() {
        Point2D[] points = {
                new Point2D.Double(0, 0),
                new Point2D.Double(0, 2),
                new Point2D.Double(-2, 0),
                new Point2D.Double(2, 2)
        };
        int e_pts = 1;
        int f_pts = 1;
        double area1 = -1;
        double area2 = 2;
        boolean b = CMVMethods.CMV_14(points, e_pts, f_pts, area1, area2);
        assertFalse(b);
    }
}