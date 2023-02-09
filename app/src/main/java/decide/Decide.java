package decide;

import java.awt.geom.Point2D;
import java.io.*;

public class Decide {

    private double LENGTH1;
    private double RADIUS1;
    private double EPSILON;
    private double AREA1;
    private int Q_PTS;
    private int QUADS;
    private double DIST;
    private int N_PTS;
    private int K_PTS;
    private int A_PTS;
    private int B_PTS;
    private int C_PTS;
    private int D_PTS;
    private int E_PTS;
    private int F_PTS;
    private int G_PTS;
    private double LENGTH2;
    private double RADIUS2;
    private double AREA2;
    private Point2D[] points;
    private Connector[][] LCM;
    private boolean[] PUV;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Read scalar params
        double  LENGTH1 = 0;
        double  RADIUS1 = 0;
        double  EPSILON = 0;
        double  AREA1   = 0;
        int     Q_PTS   = 0;
        int     QUADS   = 0;
        double  DIST    = 0;
        int     N_PTS   = 0;
        int     K_PTS   = 0;
        int     A_PTS   = 0;
        int     B_PTS   = 0;
        int     C_PTS   = 0;
        int     D_PTS   = 0;
        int     E_PTS   = 0;
        int     F_PTS   = 0;
        int     G_PTS   = 0;
        double  LENGTH2 = 0;
        double  RADIUS2 = 0;
        double  AREA2   = 0;

        try {
            LENGTH1 = Double.parseDouble(br.readLine());
            RADIUS1 = Double.parseDouble(br.readLine());
            EPSILON = Double.parseDouble(br.readLine());
            AREA1   = Double.parseDouble(br.readLine());
            Q_PTS   = Integer.parseInt(br.readLine());
            QUADS   = Integer.parseInt(br.readLine());
            DIST    = Double.parseDouble(br.readLine());
            N_PTS   = Integer.parseInt(br.readLine());
            K_PTS   = Integer.parseInt(br.readLine());
            A_PTS   = Integer.parseInt(br.readLine());
            B_PTS   = Integer.parseInt(br.readLine());
            C_PTS   = Integer.parseInt(br.readLine());
            D_PTS   = Integer.parseInt(br.readLine());
            E_PTS   = Integer.parseInt(br.readLine());
            F_PTS   = Integer.parseInt(br.readLine());
            G_PTS   = Integer.parseInt(br.readLine());
            LENGTH2 = Double.parseDouble(br.readLine());
            RADIUS2 = Double.parseDouble(br.readLine());
            AREA2   = Double.parseDouble(br.readLine());
            
        } catch (IOException ioe){
            ioe.printStackTrace(System.out);
            System.exit(1);
        }

        // Read LCM
        Connector[][] LCM = new Connector[15][15];
        String lc_line = ""; 

        try {
            for (int i = 0; i < 15; i++) {
                lc_line = br.readLine();
                String[] lc_line_values = lc_line.split(" ");
                for (int j = 0; j < 15; j++) {
                    if (lc_line_values[j].equals("0")) {
                        LCM[i][j] = Connector.NOTUSED;
                    } else if (lc_line_values[j].equals("1")) {
                        LCM[i][j] = Connector.ORR;
                    } else if (lc_line_values[j].equals("2")) {
                        LCM[i][j] = Connector.ANDD;
                    } else {
                        throw new IOException();
                    }
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace(System.out);
            System.exit(1);
        }

        // Read PUV
        boolean[] PUV = new boolean[15];
        try {
            String puv_line = br.readLine();
            String[] puv_line_values = puv_line.split(" ");
            for (int i = 0; i < 15; i++) {
                if (puv_line_values[i].equals("true")) {
                    PUV[i] = true;
                } else if (puv_line_values[i].equals("false")) {
                    PUV[i] = false;
                } else {
                    throw new IOException();
                }
            }
        } catch (IOException ioe){
            ioe.printStackTrace(System.out);
            System.exit(1);
        }

        // Read number of points
        int number_of_points = 0;
        try {
            number_of_points = Integer.parseInt(br.readLine());
        } catch (IOException ioe){
            ioe.printStackTrace(System.out);
            System.exit(1);
        }

        // Read points
        Point2D[] points = new Point2D[number_of_points];;
        String point_line = "";
        String[] coordinates;
        double read_x_coordinate = 0.0;
        double read_y_coordinate = 0.0;

        try {
            for (int i = 0; i < number_of_points; i++) {
                point_line = br.readLine();
                coordinates = point_line.split(" ");
                read_x_coordinate = Double.parseDouble(coordinates[0]);
                read_y_coordinate = Double.parseDouble(coordinates[1]);
                points[i] = new Point2D.Double(read_x_coordinate, read_y_coordinate);
            }
        } catch (IOException ioe){
            System.out.println("ERROR READING POINTS");
            ioe.printStackTrace(System.out);
            System.exit(1);
        }

        Decide decide = new Decide(LENGTH1, RADIUS1, EPSILON, AREA1, Q_PTS, QUADS, DIST, N_PTS, K_PTS, 
            A_PTS, B_PTS, C_PTS, D_PTS, E_PTS, F_PTS, G_PTS, LENGTH2, RADIUS2, AREA2, points, LCM, PUV);

        boolean launch = decide.evaluateLaunch();

        if (launch) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
         
        System.exit(0);
    }

    Decide(double LENGTH1,
            double RADIUS1,
            double EPSILON,
            double AREA1,
            int Q_PTS,
            int QUADS,
            double DIST,
            int N_PTS,
            int K_PTS,
            int A_PTS,
            int B_PTS,
            int C_PTS,
            int D_PTS,
            int E_PTS,
            int F_PTS,
            int G_PTS,
            double LENGTH2,
            double RADIUS2,
            double AREA2,
            Point2D[] points,
            Connector[][] LCM,
            boolean[] PUV) {
        this.LENGTH1 = LENGTH1;
        this.RADIUS1 = RADIUS1;
        this.EPSILON = EPSILON;
        this.AREA1 = AREA1;
        this.Q_PTS = Q_PTS;
        this.QUADS = QUADS;
        this.DIST = DIST;
        this.N_PTS = N_PTS;
        this.K_PTS = K_PTS;
        this.A_PTS = A_PTS;
        this.B_PTS = B_PTS;
        this.C_PTS = C_PTS;
        this.D_PTS = D_PTS;
        this.E_PTS = E_PTS;
        this.F_PTS = F_PTS;
        this.G_PTS = G_PTS;
        this.LENGTH2 = LENGTH2;
        this.RADIUS2 = RADIUS2;
        this.AREA2 = AREA2;
        this.points = points;
        this.LCM = LCM;
        this.PUV = PUV;
    }

    /**
     * Evaluates all CMV functions, PUM and FUV and returns whether or not to launch.
     * @return {@code true} if there should be a launch, otherwise {@code false}.
     */
    public boolean evaluateLaunch() {
        boolean[] CMV = new boolean[] {
            CMVMethods.CMV_0(points, LENGTH1),
            CMVMethods.CMV_1(points, RADIUS1),
            CMVMethods.CMV_2(points, EPSILON),
            CMVMethods.CMV_3(points, AREA1),
            CMVMethods.CMV_4(points, Q_PTS, QUADS),
            CMVMethods.CMV_5(points),
            CMVMethods.CMV_6(points, N_PTS, DIST),
            CMVMethods.CMV_7(points, K_PTS, LENGTH1),
            CMVMethods.CMV_8(points, A_PTS, B_PTS, RADIUS1),
            CMVMethods.CMV_9(points, C_PTS, D_PTS, EPSILON),
            CMVMethods.CMV_10(points, E_PTS, F_PTS, AREA1),
            CMVMethods.CMV_11(points, G_PTS),
            CMVMethods.CMV_12(points, K_PTS, LENGTH1, LENGTH2),
            CMVMethods.CMV_13(points, A_PTS, B_PTS, RADIUS1, RADIUS2),
            CMVMethods.CMV_14(points, E_PTS, F_PTS, AREA1, AREA2)
        };

        boolean[][] PUM = PUMMethods.PUM(CMV, LCM);
        boolean[] FUV = FUVMethods.FUV(PUV, PUM);

        boolean launch = LAUNCHMethods.LAUNCH(FUV);

        return launch;
    }
}
