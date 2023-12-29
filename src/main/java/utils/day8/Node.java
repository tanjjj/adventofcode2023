package utils.day8;

public class Node {
    private final String left;
    private final String right;


    public Node(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getNext(Character ch) {
        if (ch.equals('L')) {
            return left;
        } else {
            return right;
        }
    }

}
