import java.io.*;
import java.util.*;
public class PowerAVLApp {

    private AVLTree tree = new AVLTree();
    private String fname;
    private String originalfname;
    private boolean notTest = false;

    public PowerAVLApp(){
        this("cleaned_data.csv");
    }

    public PowerAVLApp(String filename){
        try{
            if (filename.contains("cleaned_data.csv")){
                notTest = true;
            }
            Scanner sc = new Scanner(new FileReader(filename));
            originalfname =filename.substring(filename.indexOf("/")+1,filename.lastIndexOf("."));

            this.fname= "AVLInsertTestResults/InsertionTestFor"+originalfname;

            if (notTest){                           //if it isnt a test, delete first line from file
                String deleteFirstLine = sc.nextLine();
                deleteFirstLine = null;
            }

            while(sc.hasNext())
            {
                if (!notTest){
                    setCounter();
                }
                String line = sc.nextLine();
                tree.root = tree.insert(tree.root, new Power(line));
                if (!notTest){
                    printCounterToFile();
                }
            }

        }
        catch(Exception e){
            System.out.println("Error:\t"+e.getMessage());
            e.printStackTrace();
        }
    }

    public void setCounter(){
        tree.opCount=0;
    }
    public void printCounterToFile(){
        try{
            PrintWriter out = new PrintWriter(new FileWriter((fname+".txt"), true));
            out.println(tree.opCount);
            out.close();
            tree.opCount=0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void printDateTime(String dateTime)
    {
        tree.findNode(dateTime);
    }
    public void printDateTime(String dateTime, Boolean isNotTest) {
        notTest=isNotTest;
        tree.findNode(dateTime);
    }
    public void printAllDateTimes()
    {
        tree.printNodes();
    }
    public void printAllDateTimes(String keyfile)
    {
        String arr[] = new String[500];
        int i = -1;
        try{

            Scanner sc = new Scanner(new FileReader(keyfile));
            while(sc.hasNext())
            {
                i++;
                String line = sc.nextLine();
                arr[i] = line.substring(0, line.indexOf(","));
            }
        }
        catch(Exception e) {

        }
        tree.printNodes(arr, i);
    }

    private class AVLTree {

        /**
         * This inner class provides the Node objects comprising the AVL Tree
         */
        private class Node {
                private Power key;
                private int height;
                private Node left, right;

                Node(Power d) {
                    key = d;
                    height = 1;
                }

            @Override
            public String toString() {
                return key.toString();
            }
        }

        private Node root;
        private int opCount=0;

        /**
         * returns height of the node
         * @param N
         */
       /**  utility function to get the height of the tree*/
        int height(Node N) {
            if (N == null)
                return 0;

            return N.height;
        }

        /**A utility function to get maximum of two integers*/
        int max(int a, int b) {
            return (a > b) ? a : b;
        }

        /** utility function to right rotate subtree rooted with y
        */
        Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;

            // Perform rotation
            x.right = y;
            y.left = T2;

            // Update heights
            y.height = max(height(y.left), height(y.right)) + 1;
            x.height = max(height(x.left), height(x.right)) + 1;

            // Return new root
            return x;
        }

        /**A utility function to left rotate subtree rooted with x*/
        Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;

            // Perform rotation
            y.left = x;
            x.right = T2;

            //  Update heights
            x.height = max(height(x.left), height(x.right)) + 1;
            y.height = max(height(y.left), height(y.right)) + 1;

            // Return new root
            return y;
        }

        /**Get Balance factor of node N*/
        int getBalance(Node N) {
            if (N == null)
                return 0;

            return height(N.left) - height(N.right);
        }
        /** Method to insert elements into the AVL tree
        */
        Node insert(Node node, Power key) {

            /* 1.  Perform the normal BST insertion */
            opCount++;//one comparison, the null one which is just to avoid error, but including compareTo used in check val
            if (node == null){

                return (new Node(key));
            }
            int check = key.compareTo(node.key);
            if (check<0){
                node.left = insert(node.left, key);
            }
            else if (check>0){
                node.right = insert(node.right, key);
            }
            else{ // Duplicate keys not allowed
                return node;
            }

            /* 2. Update height of this ancestor node */
            node.height = 1 + max(height(node.left),
                    height(node.right));

        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
            int balance = getBalance(node);
            if (Math.abs(balance)>1) {
                opCount++; //will either compare to left.key or right.key once, so just incrementing here

                if (balance>1){

                    if (key.compareTo(node.left.key) < 0) { //single right rotation
                        return rightRotate(node);
                    }
                    else{
                        node.left = leftRotate(node.left); //left rotation then right
                        return rightRotate(node);
                    }
                }
                else
                {
                    if (key.compareTo(node.right.key) > 0) { //single left rotation
                        return leftRotate(node);
                    }
                    else
                    {
                        node.right = rightRotate(node.right); //right rotation then left
                        return leftRotate(node);
                    }
                }
            }
            return node;
        }
        /**
        * Prints the nodes given an array of keys
        *
        */
        public void printNodes(String[] array, int k){
            fname = "AVLSearchTestResults/SearchTestFor"+ originalfname;
            for (int i = 0; i<=k; i++)
            {
                findNode(array[i]);
            }
        }
        public void printNodes(){
            inOrder(root);
        }
        /**In order traversal of tree*/
        private void inOrder(Node node) {
            if (node != null) {
                inOrder(node.left);
                System.out.println(node.key + " ");
                inOrder(node.right);
            }
        }
        /** finds a specific node given a dateTime*/
        public void findNode(String date)
        {
            setCounter();
            Power temp = new Power(date, "", "");
            if (notTest){
                System.out.println(findNode(temp, root)+"\n"+opCount);
            }
            else{
                System.out.println(findNode(temp, root));
                printCounterToFile();
            }
        }
        /** finds a specific node given a dateTime*/
        private String findNode(Power key, Node node)
        {
            opCount++;
            if (node==null)
            {
                return "Date/time not found";
            }

            if (key.compareTo(node.key)==0)
            {
                return node.toString();
            }
            else if (key.compareTo(node.key)<0)
            {
                return findNode(key, node.left);
            }
            else if (key.compareTo(node.key)>0)
            {
                return findNode(key, node.right);
            }
            return null;
        }

    }


        /**Main method to control how application is run from command line*/
    public static void main(String[] args)
    {

        PowerAVLApp PAA;
        if (args.length<1)
        {
            PAA = new PowerAVLApp();
            PAA.printAllDateTimes();
        }
        else
        {
            if (args[0].contains("txt"))
            {
                PAA = new PowerAVLApp(args[0]);
                PAA.printAllDateTimes(args[0]);
            }
            else
            {
                PAA = new PowerAVLApp();
                PAA.printDateTime(args[0], true);
            }
        }

    }
}
