package three.math;

public class Box2 {
    Vector2 min;
    Vector2 max;

    public Box2() {
        this(null, null);
    }

    public Box2(Vector2 min, Vector2 max) {
        this.min = (min != null) ? min : new Vector2(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        this.max = (max != null) ? max : new Vector2(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public Box2 set(Vector2 min, Vector2 max) {
        this.min.copy(min);
        this.max.copy(max);
        return this;
    }

    public Box2 setFromPoints(Vector2[] points) {
        this.makeEmpty();
        for (int i = 0, il = points.length; i < il; i++) {
            this.expandByPoint(points[i]);
        }
        return this;
    }

    public Box2 setFromCenterAndSize(Vector2 center, Vector2 size) {
        Vector2 halfSize = new Vector2().copy(size).multiplyScalar(0.5);
        this.min.copy(center).sub(halfSize);
        this.max.copy(center).add(halfSize);
        return this;
    }

    public Box2 clone() {
        return new Box2().copy(this);
    }

    public Box2 copy(Box2 box) {
        this.min.copy(box.min);
        this.max.copy(box.max);
        return this;
    }

    public Box2 makeEmpty() {
        this.min.x = this.min.y = Double.POSITIVE_INFINITY;
        this.max.x = this.max.y = Double.NEGATIVE_INFINITY;
        return this;
    }

    public boolean isEmpty() {
        return (this.max.x < this.min.x) || (this.max.y < this.min.y);
    }

    public Vector2 getCenter(Vector2 target){
        if(target == null) {
            target = new Vector2();
        }
        return this.isEmpty()? target.set(0,0):target.addVectors(min,max).multiplyScalar(0.5);
    }

    public Vector2 getSize ( Vector2 target ) {

        if ( target == null ) {
            target = new Vector2();
        }

        return this.isEmpty() ? target.set( 0, 0 ) : target.subVectors( this.max, this.min );

    }

    public Box2 expandByPoint (Vector2 point ) {
        this.min.min( point );
        this.max.max( point );
        return this;
    }

    public Box2 expandByVector (Vector2 vector ) {

        this.min.sub( vector );
        this.max.add( vector );

        return this;

    }

    public Box2 expandByScalar ( double scalar ) {

        this.min.addScalar( - scalar );
        this.max.addScalar( scalar );

        return this;

    }

    public boolean containsPoint (Vector2 point ) {

        return point.x < this.min.x || point.x > this.max.x ||
                point.y < this.min.y || point.y > this.max.y ? false : true;

    }

    public boolean containsBox (Box2 box ) {

        return this.min.x <= box.min.x && box.max.x <= this.max.x &&
                this.min.y <= box.min.y && box.max.y <= this.max.y;

    }

    public Vector2 getParameter (Vector2 point,Vector2 target ) {

        // This can potentially have a divide by zero if the box
        // has a size dimension of 0.

        if ( target == null ) {
            target = new Vector2();
        }

        return target.set(
                ( point.x - this.min.x ) / ( this.max.x - this.min.x ),
                ( point.y - this.min.y ) / ( this.max.y - this.min.y )
        );
    }

    public boolean intersectsBox (Box2 box ) {

        // using 4 splitting planes to rule out intersections

        return box.max.x < this.min.x || box.min.x > this.max.x ||
                box.max.y < this.min.y || box.min.y > this.max.y ? false : true;

    }

    public Vector2 clampPoint (Vector2 point,Vector2 target ) {

        if ( target == null ) {
            target = new Vector2();

        }

        return target.copy( point ).clamp( this.min, this.max );

    }

    public double distanceToPoint (Vector2 point ) {

		Vector2 clampedPoint = new Vector2().copy( point ).clamp( this.min, this.max );
        return clampedPoint.sub( point ).length();

    }

    public Box2 intersect (Box2 box ) {

        this.min.max( box.min );
        this.max.min( box.max );

        return this;

    }

    public Box2 union (Box2 box ) {

        this.min.min( box.min );
        this.max.max( box.max );

        return this;

    }

    public Box2 translate (Vector2 offset ) {

        this.min.add( offset );
        this.max.add( offset );

        return this;

    }

    public boolean equals (Box2 box ) {

        return box.min.equals( this.min ) && box.max.equals( this.max );

    }

}
