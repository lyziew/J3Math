package three.math;

import javax.swing.*;

public class Vector3 {
    double x;
    double y;
    double z;


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(Vector3 v) {
        this(v.x, v.y, v.z);
    }

    public Vector3(double s) {
        this(s, s, s);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 clone() {
        return new Vector3(this.x, this.y, this.z);
    }

    public Vector3 copy(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    public Vector3 add(Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    public Vector3 addScalar(double s) {
        this.x += s;
        this.y += s;
        this.z += s;
        return this;
    }

    public Vector3 addVectors(Vector3 a, Vector3 b) {
        this.x = a.x + b.x;
        this.y = a.y + b.y;
        this.z = a.z + b.z;
        return this;
    }

    public Vector3 addScaledVector(Vector3 v, double s) {

        this.x += v.x * s;
        this.y += v.y * s;
        this.z += v.z * s;

        return this;

    }

    Vector3 sub(Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;

        return this;

    }

    Vector3 subScalar(double s) {

        this.x -= s;
        this.y -= s;
        this.z -= s;

        return this;

    }

    Vector3 subVectors(Vector3 a, Vector3 b) {

        this.x = a.x - b.x;
        this.y = a.y - b.y;
        this.z = a.z - b.z;

        return this;

    }

    Vector3 multiply(Vector3 v, Vector3 w) {

        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;

    }

    Vector3 multiplyScalar(double s) {

        this.x *= s;
        this.y *= s;
        this.z *= s;

        return this;

    }

    Vector3 multiplyVectors(Vector3 a, Vector3 b) {

        this.x = a.x * b.x;
        this.y = a.y * b.y;
        this.z = a.z * b.z;

        return this;

    }

    Vector3 applyEuler(Euler euler) {
        return this.applyQuaternion(new Quaternion().setFromEuler(euler));
    }

    Vector3 applyAxisAngle(Vector3 axis, double angle) {

        return this.applyQuaternion(new Quaternion().setFromAxisAngle(axis, angle));

    }

    Vector3 applyMatrix3(Matrix3 m) {

        double x = this.x, y = this.y, z = this.z;
        double[] e = m.elements;

        this.x = e[0] * x + e[3] * y + e[6] * z;
        this.y = e[1] * x + e[4] * y + e[7] * z;
        this.z = e[2] * x + e[5] * y + e[8] * z;

        return this;
    }

    Vector3 applyNormalMatrix(Matrix3 m) {

        return this.applyMatrix3(m).normalize();

    }

    Vector3 applyMatrix4(Matrix4 m) {

        double x = this.x, y = this.y, z = this.z;
        double[] e = m.elements;

        double w = 1 / (e[3] * x + e[7] * y + e[11] * z + e[15]);

        this.x = (e[0] * x + e[4] * y + e[8] * z + e[12]) * w;
        this.y = (e[1] * x + e[5] * y + e[9] * z + e[13]) * w;
        this.z = (e[2] * x + e[6] * y + e[10] * z + e[14]) * w;

        return this;

    }

    Vector3 applyQuaternion(Quaternion q) {

        double x = this.x, y = this.y, z = this.z;
        double qx = q.x, qy = q.y, qz = q.z, qw = q.w;

        // calculate quat * vector

        double ix = qw * x + qy * z - qz * y;
        double iy = qw * y + qz * x - qx * z;
        double iz = qw * z + qx * y - qy * x;
        double iw = -qx * x - qy * y - qz * z;

        // calculate result * inverse quat

        this.x = ix * qw + iw * -qx + iy * -qz - iz * -qy;
        this.y = iy * qw + iw * -qy + iz * -qx - ix * -qz;
        this.z = iz * qw + iw * -qz + ix * -qy - iy * -qx;

        return this;

    }

//    project( camera ) {
//
//        return this.applyMatrix4( camera.matrixWorldInverse ).applyMatrix4( camera.projectionMatrix );
//
//    }
//
//    unproject( camera ) {
//
//        return this.applyMatrix4( camera.projectionMatrixInverse ).applyMatrix4( camera.matrixWorld );
//
//    }

    Vector3 transformDirection(Matrix4 m) {

        // input: THREE.Matrix4 affine matrix
        // vector interpreted as a direction

        double x = this.x, y = this.y, z = this.z;
        double[] e = m.elements;

        this.x = e[0] * x + e[4] * y + e[8] * z;
        this.y = e[1] * x + e[5] * y + e[9] * z;
        this.z = e[2] * x + e[6] * y + e[10] * z;

        return this.normalize();

    }

    Vector3 divide(Vector3 v) {

        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;

        return this;

    }

    Vector3 divideScalar(double s) {

        return this.multiplyScalar(1 / s);

    }

    Vector3 min(Vector3 v) {

        this.x = Math.min(this.x, v.x);
        this.y = Math.min(this.y, v.y);
        this.z = Math.min(this.z, v.z);

        return this;

    }

    Vector3 max(Vector3 v) {

        this.x = Math.max(this.x, v.x);
        this.y = Math.max(this.y, v.y);
        this.z = Math.max(this.z, v.z);

        return this;

    }

    Vector3 clamp(Vector3 min, Vector3 max) {

        // assumes min < max, componentwise

        this.x = Math.max(min.x, Math.min(max.x, this.x));
        this.y = Math.max(min.y, Math.min(max.y, this.y));
        this.z = Math.max(min.z, Math.min(max.z, this.z));

        return this;

    }

    Vector3 clampScalar(double minVal, double maxVal) {

        this.x = Math.max(minVal, Math.min(maxVal, this.x));
        this.y = Math.max(minVal, Math.min(maxVal, this.y));
        this.z = Math.max(minVal, Math.min(maxVal, this.z));

        return this;

    }

    Vector3 clampLength(double min, double max) {

        double length = this.length();

        return this.divideScalar(length > 0 ? length : 1).multiplyScalar(Math.max(min, Math.min(max, length)));

    }

    Vector3 floor() {

        this.x = Math.floor(this.x);
        this.y = Math.floor(this.y);
        this.z = Math.floor(this.z);

        return this;

    }

    Vector3 ceil() {

        this.x = Math.ceil(this.x);
        this.y = Math.ceil(this.y);
        this.z = Math.ceil(this.z);

        return this;

    }

    Vector3 round() {

        this.x = Math.round(this.x);
        this.y = Math.round(this.y);
        this.z = Math.round(this.z);

        return this;

    }

    Vector3 roundToZero() {

        this.x = (this.x < 0) ? Math.ceil(this.x) : Math.floor(this.x);
        this.y = (this.y < 0) ? Math.ceil(this.y) : Math.floor(this.y);
        this.z = (this.z < 0) ? Math.ceil(this.z) : Math.floor(this.z);

        return this;

    }

    Vector3 negate() {

        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;

        return this;

    }

    double dot(Vector3 v) {

        return this.x * v.x + this.y * v.y + this.z * v.z;

    }

    // TODO lengthSquared?

    double lengthSq() {

        return this.x * this.x + this.y * this.y + this.z * this.z;

    }

    double length() {

        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

    }

    double manhattanLength() {

        return Math.abs(this.x) + Math.abs(this.y) + Math.abs(this.z);

    }

    Vector3 normalize() {
        double length = this.length();
        return this.divideScalar(length > 0 ? length : 1);

    }

    Vector3 setLength(double length) {

        return this.normalize().multiplyScalar(length);

    }

    Vector3 lerp(Vector3 v, double alpha) {

        this.x += (v.x - this.x) * alpha;
        this.y += (v.y - this.y) * alpha;
        this.z += (v.z - this.z) * alpha;

        return this;

    }

    Vector3 lerpVectors(Vector3 v1, Vector3 v2, double alpha) {

        this.x = v1.x + (v2.x - v1.x) * alpha;
        this.y = v1.y + (v2.y - v1.y) * alpha;
        this.z = v1.z + (v2.z - v1.z) * alpha;

        return this;

    }

    Vector3 cross(Vector3 v) {

        return this.crossVectors(this, v);

    }

    Vector3 crossVectors(Vector3 a, Vector3 b) {

        double ax = a.x, ay = a.y, az = a.z;
        double bx = b.x, by = b.y, bz = b.z;

        this.x = ay * bz - az * by;
        this.y = az * bx - ax * bz;
        this.z = ax * by - ay * bx;

        return this;

    }

    Vector3 projectOnVector(Vector3 v) {

        double denominator = v.lengthSq();

        if (denominator == 0) {
            this.x = 0;
            this.y = 0;
            this.z = 0;
            return this;
        }
        ;

        double scalar = v.dot(this) / denominator;

        return this.copy(v).multiplyScalar(scalar);

    }

    Vector3 projectOnPlane(Vector3 planeNormal) {

        Vector3 vector3 = this.clone().projectOnVector(planeNormal);

        return this.sub(vector3);

    }

    Vector3 reflect(Vector3 normal) {

        // reflect incident vector off plane orthogonal to normal
        // normal is assumed to have unit length
        return this.sub(normal.clone().multiplyScalar(2 * this.dot(normal)));

    }

    double angleTo(Vector3 v) {

        double denominator = Math.sqrt(this.lengthSq() * v.lengthSq());

        if (denominator == 0) return Math.PI / 2;

        double theta = this.dot(v) / denominator;

        // clamp, to handle numerical problems

        return Math.acos(MathUtils.clamp(theta, -1, 1));

    }

    double distanceTo(Vector3 v) {

        return Math.sqrt(this.distanceToSquared(v));

    }

    double distanceToSquared(Vector3 v) {

        double dx = this.x - v.x, dy = this.y - v.y, dz = this.z - v.z;

        return dx * dx + dy * dy + dz * dz;

    }

    double manhattanDistanceTo(Vector3 v) {

        return Math.abs(this.x - v.x) + Math.abs(this.y - v.y) + Math.abs(this.z - v.z);

    }

    Vector3 setFromSpherical(Spherical s) {
        return this.setFromSphericalCoords(s.radius, s.phi, s.theta);
    }

    Vector3 setFromSphericalCoords(double radius, double phi, double theta) {
        double sinPhiRadius = Math.sin(phi) * radius;
        this.x = sinPhiRadius * Math.sin(theta);
        this.y = Math.cos(phi) * radius;
        this.z = sinPhiRadius * Math.cos(theta);

        return this;

    }

    Vector3 setFromCylindrical(Cylindrical c) {

        return this.setFromCylindricalCoords(c.radius, c.theta, c.y);

    }

    Vector3 setFromCylindricalCoords(double radius, double theta, double y) {

        this.x = radius * Math.sin(theta);
        this.y = y;
        this.z = radius * Math.cos(theta);

        return this;

    }

    Vector3 setFromMatrixPosition(Matrix4 m) {

        double[] e = m.elements;

        this.x = e[12];
        this.y = e[13];
        this.z = e[14];

        return this;

    }

    Vector3 setFromMatrixScale(Matrix4 m) {

        double sx = this.setFromMatrixColumn(m, 0).length();
        double sy = this.setFromMatrixColumn(m, 1).length();
        double sz = this.setFromMatrixColumn(m, 2).length();

        this.x = sx;
        this.y = sy;
        this.z = sz;

        return this;

    }

    Vector3 setFromMatrixColumn(Matrix4 m, int index) {

        return this.fromArray(m.elements, index * 4);

    }

    Vector3 setFromMatrix3Column(Matrix3 m, int index) {

        return this.fromArray(m.elements, index * 3);

    }

    boolean equals(Vector3 v) {

        return ((v.x == this.x) && (v.y == this.y) && (v.z == this.z));

    }

    public Vector3 fromArray(double[] array) {
        return fromArray(array, 0);
    }

    public Vector3 fromArray(double[] array, int offset) {
        if (array.length < offset + 3) {
            throw new IndexOutOfBoundsException();
        } else {
            this.x = array[offset];
            this.y = array[offset + 1];
            this.z = array[offset + 2];
            return this;
        }
    }

    public void toArray(double[] array) {
        toArray(array, 0);
    }

    public void toArray(double[] array, int offset) {
        if (array.length < offset + 3) {
            throw new IndexOutOfBoundsException();
        } else {
            array[offset] = this.x;
            array[offset + 1] = this.y;
            array[offset + 2] = this.z;
        }
    }


    Vector3 random() {
        this.x = Math.random();
        this.y = Math.random();
        this.z = Math.random();
        return this;
    }
}
