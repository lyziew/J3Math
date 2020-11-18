package three.math;

public class Vector2 {
    double x;
    double y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(Vector2 v) {
        this(v.x,v.y);
    }

    public Vector2(double s) {
        this(s,s);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 clone(){
        return new Vector2(this.x,this.y);
    }

    public Vector2 copy(Vector2 v){
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2 addScalar(double s) {
        this.x += s;
        this.y += s;
        return this;
    }

    public Vector2 addVectors(Vector2 a, Vector2 b) {
        this.x = a.x + b.x;
        this.y = a.y + b.y;
        return this;
    }

    public Vector2 addScaledVector(Vector2 v, double s) {
        this.x += v.x * s;
        this.y += v.y * s;
        return this;
    }

    public Vector2 sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2 subScalar(double s) {
        this.x -= s;
        this.y -= s;
        return this;
    }

    public Vector2 subVectors(Vector2 a, Vector2 b) {
        this.x = a.x - b.x;
        this.y = a.y - b.y;
        return this;
    }

    public Vector2 subScaledVector(Vector2 v, double s) {
        this.x -= v.x * s;
        this.y -= v.y * s;
        return this;
    }

    public Vector2 multiply(Vector2 v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2 multiplyScalar(double s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    public Vector2 divide(Vector2 v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    public Vector2 divideScalar(double s) {
        return this.multiplyScalar(1 / s);
    }

    public Vector2 applyMatrix3(Matrix3 m) {
        double x = this.x;
        double y = this.y;
        double[] e = m.elements;
        this.x = e[0] * x + e[3] * y + e[6];
        this.y = e[1] * x + e[4] * y + e[7];
        return this;
    }

    public Vector2 min(Vector2 v) {
        this.x = Math.min(this.x, v.x);
        this.y = Math.min(this.y, v.y);
        return this;
    }

    public Vector2 max(Vector2 v) {
        this.x = Math.max(this.x, v.x);
        this.y = Math.max(this.y, v.y);
        return this;
    }

    public Vector2 clamp(Vector2 min, Vector2 max) {
        // assumes min < max, componentwise
        this.x = Math.max(min.x, Math.min(max.x, this.x));
        this.y = Math.max(min.y, Math.min(max.y, this.y));
        return this;
    }

    public Vector2 clampLength(double min, double max) {
        double length = this.length();
        return this.divideScalar(length>0?length:1).multiplyScalar(Math.max(min, Math.min(max, length)));
    }

    public Vector2 floor() {
        this.x = Math.floor(this.x);
        this.y = Math.floor(this.y);
        return this;
    }

    public Vector2 ceil() {
        this.x = Math.ceil(this.x);
        this.y = Math.ceil(this.y);
        return this;
    }

    public Vector2 round() {
        this.x = Math.round(this.x);
        this.y = Math.round(this.y);
        return this;
    }

    public Vector2 roundToZero() {
        this.x = (this.x < 0) ? Math.ceil(this.x) : Math.floor(this.x);
        this.y = (this.y < 0) ? Math.ceil(this.y) : Math.floor(this.y);
        return this;
    }

    public Vector2 negate() {
        this.x = -this.x;
        this.y = -this.y;
        return this;
    }

    public double dot(Vector2 v) {
        return this.x * v.x + this.y * v.y;
    }

    public double cross(Vector2 v) {
        return this.x * v.y - this.y * v.x;
    }

    public double lengthSq() {
        return this.x * this.x + this.y * this.y;
    }

    public double length() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double manhattanLength(){
        return Math.abs(this.x)+ Math.abs(this.y);
    }

    public Vector2 normalize(){
        double length = this.length();
        return this.divideScalar(length>0?length:1);
    }

    public double angle(){
        return Math.atan2(-this.y,-this.x) + Math.PI;
    }

    public double distanceTo(Vector2 v) {
        return Math.sqrt(this.distanceToSquared(v));
    }

    public double distanceToSquared(Vector2 v) {
        double dx = this.x - v.x;
        double dy = this.y -v.y;
        return dx* dx + dy* dy;
    }

    public double manhattanDistanceTo(Vector2 v){
        return Math.abs(this.x-v.x) + Math.abs(this.y- v.y);
    }

    public Vector2 setLength(double length){
        return this.normalize().multiplyScalar(length);
    }

    public Vector2 lerp(Vector2 v, double alpha) {
        this.x+=(v.x-this.x) * alpha;
        this.y+=(v.y-this.y) * alpha;
        return this;
    }

    public boolean equals(Vector2 v){
        return (v.x== this.x)&&(v.y==this.y);
    }

    public Vector2 fromArray(double[] array) {
        return fromArray(array,0);
    }

    public Vector2 fromArray(double[] array, int offset) {
        if(array.length < offset + 2) {
            throw new IndexOutOfBoundsException();
        } else {
            this.x = array[offset];
            this.y = array[offset+1];
            return this;
        }
    }

    public void toArray(double[] array) {
        toArray(array,0);
    }

    public void toArray(double[] array, int offset) {
        if(array.length < offset + 2) {
            throw new IndexOutOfBoundsException();
        } else {
           array[offset] = this.x;
           array[offset+1] = this.y;
        }
    }

    public Vector2 rotateAround(Vector2 center, double angle){
        double c = Math.cos(angle);
        double s = Math.cos(angle);
        this.x = x* c - y *s + center.x;
        this.y = x*s+ y*c + center.y;
        return this;
    }

    public Vector2 random(){
        this.x = Math.random();
        this.y = Math.random();
        return this;
    }
}
