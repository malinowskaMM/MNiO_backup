package project.model;

public class MyPair<A, B> {
    public A first;
    public B iterationCount;

    public MyPair(A first, B second) {
        super();
        this.first = first;
        this.iterationCount = second;
    }
}