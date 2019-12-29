package webapplication.bet.model;

public class Course {
    float c1;
    float cX;
    float c2;
    float c1X;
    float c2X;
    float c12;

    public Course() {
    }

    public Course(float c1, float cX, float c2, float c1X, float c2X, float c12) {
        this.c1 = c1;
        this.cX = cX;
        this.c2 = c2;
        this.c1X = c1X;
        this.c2X = c2X;
        this.c12 = c12;
    }

    public float getC1() {
        return c1;
    }

    public void setC1(float c1) {
        this.c1 = c1;
    }

    public float getCX() {
        return cX;
    }

    public void setCX(float cX) {
        this.cX = cX;
    }

    public float getC2() {
        return c2;
    }

    public void setC2(float c2) {
        this.c2 = c2;
    }

    public float getC1X() {
        return c1X;
    }

    public void setC1X(float c1X) {
        this.c1X = c1X;
    }

    public float getC2X() {
        return c2X;
    }

    public void setC2X(float c2X) {
        this.c2X = c2X;
    }

    public float getC12() {
        return c12;
    }

    public void setC12(float c12) {
        this.c12 = c12;
    }
}
