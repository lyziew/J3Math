package three.math;

import java.security.InvalidParameterException;

import static three.math.MathUtils.EPSILON;

public class Quaternion {

    double x =0,y=0,z=0,w=0;
    public Quaternion(){

    }

    public Quaternion(double x,double y, double z,double w) {
       set(x,y,z,w);
    }

    public void set(double x,double y,double z,double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


     public Quaternion clone() {

        return new Quaternion( this.x, this.y, this.z, this.w );

    }

    public Quaternion copy(Quaternion quaternion ) {

        this.x = quaternion.x;
        this.y = quaternion.y;
        this.z = quaternion.z;
        this.w = quaternion.w;
        return this;

    }

    public Quaternion setFromEuler(Euler euler) {

        if ( euler == null ) {

            throw new NullPointerException( "THREE.Quaternion: .setFromEuler() now expects an Euler rotation rather " +
                    "than a Vector3 and order.");

        }

		double x = euler.x, y = euler.y, z = euler.z;
        EulerOrder order = euler.order;

        // http://www.mathworks.com/matlabcentral/fileexchange/
        // 	20696-function-to-convert-between-dcm-euler-angles-quaternions-and-euler-vectors/
        //	content/SpinCalc.m

		double c1 = Math.cos( x / 2 );
		double c2 = Math.cos( y / 2 );
		double c3 = Math.cos( z / 2 );

		double s1 = Math.sin( x / 2 );
		double s2 = Math.sin( y / 2 );
		double s3 = Math.sin( z / 2 );

        switch ( order ) {

            case XYZ:
                this.x = s1 * c2 * c3 + c1 * s2 * s3;
                this.y = c1 * s2 * c3 - s1 * c2 * s3;
                this.z = c1 * c2 * s3 + s1 * s2 * c3;
                this.w = c1 * c2 * c3 - s1 * s2 * s3;
                break;

            case YXZ:
                this.x = s1 * c2 * c3 + c1 * s2 * s3;
                this.y = c1 * s2 * c3 - s1 * c2 * s3;
                this.z = c1 * c2 * s3 - s1 * s2 * c3;
                this.w = c1 * c2 * c3 + s1 * s2 * s3;
                break;

            case ZXY:
                this.x = s1 * c2 * c3 - c1 * s2 * s3;
                this.y = c1 * s2 * c3 + s1 * c2 * s3;
                this.z = c1 * c2 * s3 + s1 * s2 * c3;
                this.w = c1 * c2 * c3 - s1 * s2 * s3;
                break;

            case ZYX:
                this.x = s1 * c2 * c3 - c1 * s2 * s3;
                this.y = c1 * s2 * c3 + s1 * c2 * s3;
                this.z = c1 * c2 * s3 - s1 * s2 * c3;
                this.w = c1 * c2 * c3 + s1 * s2 * s3;
                break;

            case YZX:
                this.x = s1 * c2 * c3 + c1 * s2 * s3;
                this.y = c1 * s2 * c3 + s1 * c2 * s3;
                this.z = c1 * c2 * s3 - s1 * s2 * c3;
                this.w = c1 * c2 * c3 - s1 * s2 * s3;
                break;

            case XZY:
                this.x = s1 * c2 * c3 - c1 * s2 * s3;
                this.y = c1 * s2 * c3 - s1 * c2 * s3;
                this.z = c1 * c2 * s3 + s1 * s2 * c3;
                this.w = c1 * c2 * c3 + s1 * s2 * s3;
                break;

            default:
                throw  new InvalidParameterException(
                        "THREE.Quaternion: .setFromEuler() encountered an unknown order: " + order );

        }
        return this;

    }

    public Quaternion setFromAxisAngle(Vector3 axis,double angle ) {

        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/angleToQuaternion/index.htm

        // assumes axis is normalized

		double halfAngle = angle / 2, s = Math.sin( halfAngle );

        this.x = axis.x * s;
        this.y = axis.y * s;
        this.z = axis.z * s;
        this.w = Math.cos( halfAngle );
        return this;

    }

    public Quaternion setFromRotationMatrix(Matrix4 m ) {

        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/index.htm

        // assumes the upper 3x3 of m is a pure rotation matrix (i.e, unscaled)

		double te[] = m.elements,

                m11 = te[ 0 ], m12 = te[ 4 ], m13 = te[ 8 ],
                m21 = te[ 1 ], m22 = te[ 5 ], m23 = te[ 9 ],
                m31 = te[ 2 ], m32 = te[ 6 ], m33 = te[ 10 ],

                trace = m11 + m22 + m33;

        if ( trace > 0 ) {

			double s = 0.5 / Math.sqrt( trace + 1.0 );

            this.w = 0.25 / s;
            this.x = ( m32 - m23 ) * s;
            this.y = ( m13 - m31 ) * s;
            this.z = ( m21 - m12 ) * s;

        } else if ( m11 > m22 && m11 > m33 ) {

			double s = 2.0 * Math.sqrt( 1.0 + m11 - m22 - m33 );

            this.w = ( m32 - m23 ) / s;
            this.x = 0.25 * s;
            this.y = ( m12 + m21 ) / s;
            this.z = ( m13 + m31 ) / s;

        } else if ( m22 > m33 ) {

			double s = 2.0 * Math.sqrt( 1.0 + m22 - m11 - m33 );

            this.w = ( m13 - m31 ) / s;
            this.x = ( m12 + m21 ) / s;
            this.y = 0.25 * s;
            this.z = ( m23 + m32 ) / s;

        } else {

			double s = 2.0 * Math.sqrt( 1.0 + m33 - m11 - m22 );

            this.w = ( m21 - m12 ) / s;
            this.x = ( m13 + m31 ) / s;
            this.y = ( m23 + m32 ) / s;
            this.z = 0.25 * s;

        }

        return this;

    }

    public Quaternion setFromUnitVectors(Vector3 vFrom,Vector3 vTo ) {

        // assumes direction vectors vFrom and vTo are normalized

		double EPS = 0.000001;

        double r = vFrom.dot( vTo ) + 1;

        if ( r < EPS ) {

            r = 0;

            if ( Math.abs( vFrom.x ) > Math.abs( vFrom.z ) ) {

                this.x = - vFrom.y;
                this.y = vFrom.x;
                this.z = 0;
                this.w = r;

            } else {

                this.x = 0;
                this.y = - vFrom.z;
                this.z = vFrom.y;
                this.w = r;

            }

        } else {

            // crossVectors( vFrom, vTo ); // inlined to avoid cyclic dependency on Vector3

            this.x = vFrom.y * vTo.z - vFrom.z * vTo.y;
            this.y = vFrom.z * vTo.x - vFrom.x * vTo.z;
            this.z = vFrom.x * vTo.y - vFrom.y * vTo.x;
            this.w = r;

        }

        return this.normalize();

    }

    public double angleTo(Quaternion q ) {

        return 2 * Math.acos( Math.abs( MathUtils.clamp( this.dot( q ), - 1, 1 ) ) );

    }

    public Quaternion rotateTowards(Quaternion q,double step ) {

		double angle = this.angleTo( q );

        if ( angle == 0 ) return this;

		int t = (int) Math.min( 1, step / angle );

        this.slerp( q, t );

        return this;

    }

    public Quaternion identity() {
        this.set( 0, 0, 0, 1 );
        return this;

    }

    public Quaternion inverse() {

        // quaternion is assumed to have unit length

        return this.conjugate();

    }

    public Quaternion conjugate() {

        this.x *= - 1;
        this.y *= - 1;
        this.z *= - 1;
        return this;

    }

    public double dot(Quaternion v ) {

        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;

    }

    public double lengthSq() {

        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;

    }

    public double length() {

        return Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w );

    }

    public Quaternion normalize() {

        double l = this.length();

        if ( l == 0 ) {

            this.x = 0;
            this.y = 0;
            this.z = 0;
            this.w = 1;

        } else {

            l = 1 / l;

            this.x = this.x * l;
            this.y = this.y * l;
            this.z = this.z * l;
            this.w = this.w * l;

        }

        return this;

    }

    public  Quaternion multiply(Quaternion q) {
        return this.multiplyQuaternions( this, q );

    }

    public  Quaternion premultiply(Quaternion q ) {

        return this.multiplyQuaternions( q, this );

    }

    public Quaternion multiplyQuaternions(Quaternion a,Quaternion b ) {

        // from http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions/code/index.htm

		double qax = a.x, qay = a.y, qaz = a.z, qaw = a.w;
		double qbx = b.x, qby = b.y, qbz = b.z, qbw = b.w;

        this.x = qax * qbw + qaw * qbx + qay * qbz - qaz * qby;
        this.y = qay * qbw + qaw * qby + qaz * qbx - qax * qbz;
        this.z = qaz * qbw + qaw * qbz + qax * qby - qay * qbx;
        this.w = qaw * qbw - qax * qbx - qay * qby - qaz * qbz;

        return this;

    }

    public Quaternion slerp(Quaternion qb,int t ) {

        if ( t == 0 ) return this;
        if ( t == 1 ) return this.copy( qb );

		double x = this.x, y = this.y, z = this.z, w = this.w;

        // http://www.euclideanspace.com/maths/algebra/realNormedAlgebra/quaternions/slerp/

        double cosHalfTheta = w * qb.w + x * qb.x + y * qb.y + z * qb.z;

        if ( cosHalfTheta < 0 ) {

            this.w = - qb.w;
            this.x = - qb.x;
            this.y = - qb.y;
            this.z = - qb.z;

            cosHalfTheta = - cosHalfTheta;

        } else {

            this.copy( qb );

        }

        if ( cosHalfTheta >= 1.0 ) {

            this.w = w;
            this.x = x;
            this.y = y;
            this.z = z;

            return this;

        }

		double sqrSinHalfTheta = 1.0 - cosHalfTheta * cosHalfTheta;

        if ( sqrSinHalfTheta <= EPSILON ) {

			double s = 1 - t;
            this.w = s * w + t * this.w;
            this.x = s * x + t * this.x;
            this.y = s * y + t * this.y;
            this.z = s * z + t * this.z;

            this.normalize();


            return this;

        }

		double sinHalfTheta = Math.sqrt( sqrSinHalfTheta );
		double halfTheta = Math.atan2( sinHalfTheta, cosHalfTheta );
		double ratioA = Math.sin( ( 1 - t ) * halfTheta ) / sinHalfTheta,
                ratioB = Math.sin( t * halfTheta ) / sinHalfTheta;

        this.w = ( w * ratioA + this.w * ratioB );
        this.x = ( x * ratioA + this.x * ratioB );
        this.y = ( y * ratioA + this.y * ratioB );
        this.z = ( z * ratioA + this.z * ratioB );

        return this;

    }

    public boolean equals(Quaternion quaternion ) {

        return ( quaternion.x == this.x ) && ( quaternion.y == this.y ) && ( quaternion.z == this.z ) && ( quaternion.w == this.w );

    }

    public Quaternion fromArray(double[] array) {
        return fromArray(array,0);
    }

    public Quaternion fromArray(double[] array,int offset ) {
        if(array == null) {
            throw new NullPointerException("");
        }
        if(array.length < offset+4){
            throw new IndexOutOfBoundsException("");
        }
        this.x = array[ offset ];
        this.y = array[ offset + 1 ];
        this.z = array[ offset + 2 ];
        this.w = array[ offset + 3 ];

        return this;

    }

    double[] toArray(double[] array){
        return toArray(array,0);
    }

    double[] toArray(double[] array,int offset ) {

        if ( array == null ){
            array = new double[4];
            offset=0;
        }
        if(array.length < offset + 4){
            throw new IndexOutOfBoundsException("");
        }
        array[ offset ] = this.x;
        array[ offset + 1 ] = this.y;
        array[ offset + 2 ] = this.z;
        array[ offset + 3 ] = this.w;

        return array;

    }
}
