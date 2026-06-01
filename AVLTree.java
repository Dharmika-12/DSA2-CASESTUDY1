import java.util.*;

class AVLNode {
    long ts;
    int height;
    AVLNode left, right;

    AVLNode(long ts) {
        this.ts = ts;
        this.height = 1;
    }
}
public class AVLTree {

    static int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    static int getBalance(AVLNode node) {
        return (node == null) ? 0 :
                height(node.left) - height(node.right);
    }

    static AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;

        x.height = Math.max(height(x.left),height(x.right)) + 1;

        return x;
    }

    static AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left),height(x.right)) + 1;

        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    static AVLNode insert(AVLNode root, long key) {

        if (root == null)
            return new AVLNode(key);

        // Larger timestamp goes LEFT
        if (key > root.ts)
            root.left = insert(root.left, key);
        else
            root.right = insert(root.right, key);

        root.height = 1 + Math.max(height(root.left),height(root.right));

        int balance = getBalance(root);

        // LL Rotation
        if (balance > 1 && key > root.left.ts)
            return rightRotate(root);

        // RR Rotation
        if (balance < -1 && key < root.right.ts)
            return leftRotate(root);

        // LR Rotation
        if (balance > 1 && key < root.left.ts) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // RL Rotation
        if (balance < -1 && key > root.right.ts) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    static void topK(AVLNode root, int k, List<Long> result) {

        if (root == null || result.size() == k)
            return;

        topK(root.left, k, result);

        if (result.size() < k)
            result.add(root.ts);

        topK(root.right, k, result);
    }

    static void inorder(AVLNode root) {
        if (root == null)
            return;

        inorder(root.left);
        System.out.print(root.ts + " ");
        inorder(root.right);
    }

    public static void main(String[] args) {

        long[] timestamps = {
            31000, 27000, 35000,
            23000, 39000, 19000,
            43000, 15000, 47000,
            11000, 51000
        };

        AVLNode root = null;

        for (long ts : timestamps) {
            root = insert(root, ts);
        }

        System.out.println("All Timestamps (Descending Order):");
        inorder(root);

        List<Long> top5 = new ArrayList<>();
        topK(root, 5, top5);

        System.out.println("\n\nTop 5 Recent Timestamps:");

        for (long ts : top5) {
            System.out.print(ts + " ");
        }
    }
}