package three.math;

public class Vector4 {
    double x;
    double y;
    double z;
    double w;
    public Vector4(){
        this(0,0,0,0);
    }

    public Vector4(Vector4 v){
        this(v.x,v.y,v.z,v.w);
    }

    public Vector4(double s) {
        this(s,s,s,s);
    }

    public Vector4(double x,double y,double z,double w){
        this.x= x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4 clone() {
        return new Vector4( this.x, this.y, this.z, this.w );

    }

    Vector4 copy ( Vector4 v ) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }

    Vector4 add (Vector4 v ) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        this.w += v.w;

        return this;

    }

    Vector4 addScalar (double s ) {

        this.x += s;
        this.y += s;
        this.z += s;
        this.w += s;

        return this;
    }

    Vector4 addVectors ( Vector4 a,Vector4 b ) {

        this.x = a.x + b.x;
        this.y = a.y + b.y;
        this.z = a.z + b.z;
        this.w = a.w + b.w;

        return this;

    }

     Vector4 addScaledVector (Vector4 v,double s ) {

        this.x += v.x * s;
        this.y += v.y * s;
        this.z += v.z * s;
        this.w += v.w * s;

        return this;

    }

    Vector4 sub (Vector4 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        this.w -= v.w;

        return this;

    }

    Vector4 subScalar (double s ) {

        this.x -= s;
        this.y -= s;
        this.z -= s;
        this.w -= s;

        return this;

    }

    Vector4 subVectors (Vector4 a,Vector4 b ) {

        this.x = a.x - b.x;
        this.y = a.y - b.y;
        this.z = a.z - b.z;
        this.w = a.w - b.w;

        return this;

    }

    Vector4 multiplyScalar (double s ) {

        this.x *= s;
        this.y *= s;
        this.z *= s;
        this.w *= s;

        return this;

    }

    Vector4 applyMatrix4 (Matrix4 m ) {

		double x = this.x, y = this.y, z = this.z, w = this.w;
		double[] e = m.elements;

        this.x = e[ 0 ] * x + e[ 4 ] * y + e[ 8 ] * z + e[ 12 ] * w;
        this.y = e[ 1 ] * x + e[ 5 ] * y + e[ 9 ] * z + e[ 13 ] * w;
        this.z = e[ 2 ] * x + e[ 6 ] * y + e[ 10 ] * z + e[ 14 ] * w;
        this.w = e[ 3 ] * x + e[ 7 ] * y + e[ 11 ] * z + e[ 15 ] * w;

        return this;

    }

    Vector4 divideScalar ( double s ) {

        return this.multiplyScalar( 1 / s );

    }

   Vector4 setAxisAngleFromQuaternion (Quaternion q ) {

        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToAngle/index.htm

        // q is assumed to be normalized

        this.w = 2 * Math.acos( q.w );

		double s = Math.sqrt( 1 - q.w * q.w );

        if ( s < 0.0001 ) {

            this.x = 1;
            this.y = 0;
            this.z = 0;

        } else {

            this.x = q.x / s;
            this.y = q.y / s;
            this.z = q.z / s;

        }

        return this;

    }

   Vector4 setAxisAngleFromRotationMatrix (Matrix4 m ) {

        // http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToAngle/index.htm

        // assumes the upper 3x3 of m is a pure rotation matrix (i.e, unscaled)

        double angle, x, y, z; // variables for result
        double epsilon = 0.01,		// margin to allow for rounding errors
                epsilon2 = 0.1;	// margin to distinguish between 0 and 180 degrees
                double[] te = m.elements;

            double m11 = te[ 0 ], m12 = te[ 4 ], m13 = te[ 8 ],
                m21 = te[ 1 ], m22 = te[ 5 ], m23 = te[ 9 ],
                m31 = te[ 2 ], m32 = te[ 6 ], m33 = te[ 10 ];

        if ( ( Math.abs( m12 - m21 ) < epsilon ) &&
                ( Math.abs( m13 - m31 ) < epsilon ) &&
                ( Math.abs( m23 - m32 ) < epsilon ) ) {

            // singularity found
            // first check for identity matrix which must have +1 for all terms
            // in leading diagonal and zero in other terms

            if ( ( Math.abs( m12 + m21 ) < epsilon2 ) &&
                    ( Math.abs( m13 + m31 ) < epsilon2 ) &&
                    ( Math.abs( m23 + m32 ) < epsilon2 ) &&
                    ( Math.abs( m11 + m22 + m33 - 3 ) < epsilon2 ) ) {

                // this singularity is identity matrix so angle = 0

                this.x =1;
                this.y=0;
                this.z=0;
                this.w = 0;
                return this; // zero angle, arbitrary axis

            }

            // otherwise this singularity is angle = 180

            angle = Math.PI;

			double xx = ( m11 + 1 ) / 2;
            double yy = ( m22 + 1 ) / 2;
            double zz = ( m33 + 1 ) / 2;
            double xy = ( m12 + m21 ) / 4;
            double xz = ( m13 + m31 ) / 4;
            double yz = ( m23 + m32 ) / 4;

            if ( ( xx > yy ) && ( xx > zz ) ) {

                // m11 is the largest diagonal term

                if ( xx < epsilon ) {

                    x = 0;
                    y = 0.707106781;
                    z = 0.707106781;

                } else {

                    x = Math.sqrt( xx );
                    y = xy / x;
                    z = xz / x;

                }

            } else if ( yy > zz ) {

                // m22 is the largest diagonal term

                if ( yy < epsilon ) {

                    x = 0.707106781;
                    y = 0;
                    z = 0.707106781;

                } else {

                    y = Math.sqrt( yy );
                    x = xy / y;
                    z = yz / y;

                }

            } else {

                // m33 is the largest diagonal term so base result on this

                if ( zz < epsilon ) {

                    x = 0.707106781;
                    y = 0.707106781;
                    z = 0;

                } else {

                    z = Math.sqrt( zz );
                    x = xz / z;
                    y = yz / z;

                }

            }

            this.x =x;
            this.y = y;
            this.z = z;
            this.w= angle;
            return this; // return 180 deg rotation

        }

        // as we have reached here there are no singularities so we can handle normally

        double s = Math.sqrt( ( m32 - m23 ) * ( m32 - m23 ) +
                ( m13 - m31 ) * ( m13 - m31 ) +
                ( m21 - m12 ) * ( m21 - m12 ) ); // used to normalize

        if ( Math.abs( s ) < 0.001 ) s = 1;

        // prevent divide by zero, should not happen if matrix is orthogonal and should be
        // caught by singularity test above, but I've left it in just in case

        this.x = ( m32 - m23 ) / s;
        this.y = ( m13 - m31 ) / s;
        this.z = ( m21 - m12 ) / s;
        this.w = Math.acos( ( m11 + m22 + m33 - 1 ) / 2 );

        return this;

    }

    Vector4 min (Vector4 v ) {

        this.x = Math.min( this.x, v.x );
        this.y = Math.min( this.y, v.y );
        this.z = Math.min( this.z, v.z );
        this.w = Math.min( this.w, v.w );

        return this;

    }

    Vector4 max (Vector4 v ) {

        this.x = Math.max( this.x, v.x );
        this.y = Math.max( this.y, v.y );
        this.z = Math.max( this.z, v.z );
        this.w = Math.max( this.w, v.w );

        return this;

    }

    Vector4 clamp ( Vector4 min,Vector4 max ) {

        // assumes min < max, componentwise

        this.x = Math.max( min.x, Math.min( max.x, this.x ) );
        this.y = Math.max( min.y, Math.min( max.y, this.y ) );
        this.z = Math.max( min.z, Math.min( max.z, this.z ) );
        this.w = Math.max( min.w, Math.min( max.w, this.w ) );

        return this;

    }

    Vector4 clampScalar (double minVal,double maxVal ) {

        this.x = Math.max( minVal, Math.min( maxVal, this.x ) );
        this.y = Math.max( minVal, Math.min( maxVal, this.y ) );
        this.z = Math.max( minVal, Math.min( maxVal, this.z ) );
        this.w = Math.max( minVal, Math.min( maxVal, this.w ) );

        return this;

    }

   Vector4 clampLength (double min,double max ) {

		double length = this.length();

        return this.divideScalar( length>0?length:1 ).multiplyScalar( Math.max( min, Math.min( max, length ) ) );

    }

    Vector4 floor () {

        this.x = Math.floor( this.x );
        this.y = Math.floor( this.y );
        this.z = Math.floor( this.z );
        this.w = Math.floor( this.w );

        return this;

    }

    Vector4 ceil () {

        this.x = Math.ceil( this.x );
        this.y = Math.ceil( this.y );
        this.z = Math.ceil( this.z );
        this.w = Math.ceil( this.w );

        return this;

    }

    Vector4 round () {

        this.x = Math.round( this.x );
        this.y = Math.round( this.y );
        this.z = Math.round( this.z );
        this.w = Math.round( this.w );

        return this;

    }

    Vector4 roundToZero () {
        this.x = ( this.x < 0 ) ? Math.ceil( this.x ) : Math.floor( this.x );
        this.y = ( this.y < 0 ) ? Math.ceil( this.y ) : Math.floor( this.y );
        this.z = ( this.z < 0 ) ? Math.ceil( this.z ) : Math.floor( this.z );
        this.w = ( this.w < 0 ) ? Math.ceil( this.w ) : Math.floor( this.w );
        return this;

    }

   Vector4 negate () {

        this.x = - this.x;
        this.y = - this.y;
        this.z = - this.z;
        this.w = - this.w;

        return this;

    }

    double dot (Vector4 v ) {

        return this.x * v.x + this.y * v.y + this.z * v.z + this.w * v.w;

    }

    double lengthSq () {

        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;

    }

    double length () {

        return Math.sqrt( this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w );

    }

    double manhattanLength () {

        return Math.abs( this.x ) + Math.abs( this.y ) + Math.abs( this.z ) + Math.abs( this.w );

    }

    Vector4 normalize () {
        double length = this.length();
        return this.divideScalar( length>0?length:1 );

    }

    Vector4 setLength (double length ) {

        return this.normalize().multiplyScalar( length );

    }

    Vector4 lerp (Vector4 v,double alpha ) {

        this.x += ( v.x - this.x ) * alpha;
        this.y += ( v.y - this.y ) * alpha;
        this.z += ( v.z - this.z ) * alpha;
        this.w += ( v.w - this.w ) * alpha;

        return this;

    }

    Vector4 lerpVectors (Vector4 v1,Vector4 v2,double alpha ) {

        this.x = v1.x + ( v2.x - v1.x ) * alpha;
        this.y = v1.y + ( v2.y - v1.y ) * alpha;
        this.z = v1.z + ( v2.z - v1.z ) * alpha;
        this.w = v1.w + ( v2.w - v1.w ) * alpha;

        return this;

    }

    boolean equals (Vector4 v ) {

        return ( ( v.x == this.x ) && ( v.y == this.y ) && ( v.z == this.z ) && ( v.w == this.w ) );

    }

    public Vector4 fromArray(double[] array) {
        return fromArray(array, 0);
    }

    public Vector4 fromArray(double[] array, int offset) {
        if (array.length < offset + 4) {
            throw new IndexOutOfBoundsException();
        } else {
            this.x = array[offset];
            this.y = array[offset + 1];
            this.z = array[offset + 2];
            this.z = array[offset + 3];
            return this;
        }
    }

    public void toArray(double[] array) {
        toArray(array, 0);
    }

    public void toArray(double[] array, int offset) {
        if (array.length < offset + 4) {
            throw new IndexOutOfBoundsException();
        } else {
            array[offset] = this.x;
            array[offset + 1] = this.y;
            array[offset + 2] = this.z;
            array[offset + 3] = this.w;
        }
    }

    Vector4 random () {

        this.x = Math.random();
        this.y = Math.random();
        this.z = Math.random();
        this.w = Math.random();

        return this;

    }
}
