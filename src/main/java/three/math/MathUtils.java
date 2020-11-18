package three.math;

import java.security.InvalidParameterException;

public class MathUtils {
    public static final double EPSILON = 2.2204460492503130808472633361816e-16;
    public static  String[] _lut = new String[256];
    static {
        for (int  i = 0; i < 256; i ++ ) {

            _lut[ i ] = ( i < 16 ? "0" : " " ) + Integer.toHexString(i);
        }
    }

    public static final  double DEG2RAD = Math.PI / 180;
    public static final  double RAD2DEG = 180 / Math.PI;
    public static double clamp(double value, double min, double max) {
        return Math.max(min,Math.min(max,value));
    }

    public static String generateUUID(){
// http://stackoverflow.com/questions/105034/how-to-create-a-guid-uuid-in-javascript/21963136#21963136
		int d0 = (int)(Math.random() * 0xffffffff) | 0;
		int d1 = (int)(Math.random() * 0xffffffff) | 0;
		int d2 = (int)(Math.random() * 0xffffffff)| 0;
		int d3 = (int)(Math.random() * 0xffffffff) | 0;
		String uuid =
                _lut[ d0 & 0xff ] + _lut[ d0 >> 8 & 0xff ] + _lut[ d0 >> 16 & 0xff ] + _lut[ d0 >> 24 & 0xff ] + '-' +
                _lut[ d1 & 0xff ] + _lut[ d1 >> 8 & 0xff ] + '-' + _lut[ d1 >> 16 & 0x0f | 0x40 ] + _lut[ d1 >> 24 & 0xff ] + '-' +
                _lut[ d2 & 0x3f | 0x80 ] + _lut[ d2 >> 8 & 0xff ] + '-' + _lut[ d2 >> 16 & 0xff ] + _lut[ d2 >> 24 & 0xff ] +
                _lut[ d3 & 0xff ] + _lut[ d3 >> 8 & 0xff ] + _lut[ d3 >> 16 & 0xff ] + _lut[ d3 >> 24 & 0xff ];

        // .toUpperCase() here flattens concatenated strings to save heap memory space.
        return uuid.toUpperCase();
    }

    public static int euclideanModulo(int n, int m) {
        return ((n % m) + m) % m;
    }

    public static double mapLinear(double x, double a1, double a2 ,double b1, double b2) {
        return b1 + (x-a1) * (b2-b1) /(a2-a1);
    }

    public static double lerp(double x, double y, double t) {
        return (1 -t) * x + t* y;
    }

    public static double smoothstep(double x,double min,double max) {
        if(x<=min) return 0;
        if(x>=max) return 1;
        x = (x-min) / (max -min);
        return x*x * (3- 2*x);
    }

    public static double smootherstep(double x,double min,double max) {
        if(x<=min) return 0;
        if(x>=max) return 1;
        x = (x-min) / (max -min);
        return x*x*x * (x*( x*6- 15)+10);
    }

    public static int randInt(int low,int high) {
        return (int) (low + Math.floor( Math.random() * (high-low + 1)));
    }

    public static double randFlot(double low, double high) {
        return low + Math.random() * (high-low);
    }

    public static double randFloatSpread(double range) {
        return range * (0.5 - Math.random());
    }

    public static double seededRandom(double s) {
        double _seed = s % 2147483647;
        // Park-Miller algorithm
        _seed = _seed * 16807 % 2147483647;

        return ( _seed - 1 ) / 2147483646;
    }

    public static double degToRad(double degrees) {
        return degrees * MathUtils.DEG2RAD;
    }

    public static double radToDeg(double radians) {
        return radians * MathUtils.RAD2DEG;
    }

    public static boolean isPowerOfTwo(int value) {
        return (value & (value -1)) == 0 && value != 0;
    }

    public static int ceilPowerofTwo(int value) {
        return (int)Math.pow(2,(int)Math.ceil(Math.log(value)/ Math.log(2)));
    }

    public static int floorPowerofTwo(int value) {
        return (int)Math.pow( 2, (int)Math.floor( Math.log( value ) / Math.log(2) ) );
    }

    public static void setQuaternionFromProperEuler(Quaternion q, double a,double b,double c, EulerOrder order){
        // Intrinsic Proper Euler Angles - see https://en.wikipedia.org/wiki/Euler_angles

        // rotations are applied to the axes in the order specified by 'order'
        // rotation by angle 'a' is applied first, then by angle 'b', then by angle 'c'
        // angles are in radians
		double c2 = Math.cos( b / 2 );
        double s2 = Math.sin( b / 2 );

        double c13 = Math.cos( ( a + c ) / 2 );
        double s13 = Math.sin( ( a + c ) / 2 );

        double c1_3 = Math.cos( ( a - c ) / 2 );
        double s1_3 = Math.sin( ( a - c ) / 2 );

        double c3_1 = Math.cos( ( c - a ) / 2 );
        double s3_1 = Math.sin( ( c - a ) / 2 );

        switch ( order ) {

            case XYX:
                q.set( c2 * s13, s2 * c1_3, s2 * s1_3, c2 * c13 );
                break;

            case YZY:
                q.set( s2 * s1_3, c2 * s13, s2 * c1_3, c2 * c13 );
                break;

            case ZXZ:
                q.set( s2 * c1_3, s2 * s1_3, c2 * s13, c2 * c13 );
                break;

            case XZX:
                q.set( c2 * s13, s2 * s3_1, s2 * c3_1, c2 * c13 );
                break;

            case YXY:
                q.set( s2 * c3_1, c2 * s13, s2 * s3_1, c2 * c13 );
                break;

            case ZYZ:
                q.set( s2 * s3_1, s2 * c3_1, c2 * s13, c2 * c13 );
                break;

            default:
                throw new InvalidParameterException( "THREE.MathUtils: .setQuaternionFromProperEuler() encountered an" +
                        " unknown order: " + order );
        }
    }

}
