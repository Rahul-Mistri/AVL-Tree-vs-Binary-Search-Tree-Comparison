import java.util.*;
import java.io.*;
public class PowerBSTApp {

    private BST bst = new BST();
    private String fname;
    private String originalfname;
    private boolean notTest = false;
    public PowerBSTApp()
    {
        this("cleaned_data.csv");
    }

    public PowerBSTApp(String filename)
    {
        try{
            if (filename.contains("cleaned_data.csv"))
            {
                notTest = true;
            }
            Scanner sc = new Scanner(new FileReader(filename));
            originalfname =filename.substring(filename.indexOf("/")+1,filename.lastIndexOf("."));

            this.fname= "BSTInsertTestResults/InsertionTestFor"+originalfname;

            if (notTest){                           //if it isnt a test, delete first line from file
                String deleteFirstLine = sc.nextLine();
                deleteFirstLine = null;
            }
            while(sc.hasNext())
            {
                if (!notTest){
                setCounter();}

                String line = sc.nextLine();
                bst.insert(new Power(line));
                if(!notTest){
                printCounterToFile();}
            }
        }
        catch(Exception e)
        {
            System.out.println("Error: "+e.getMessage());
            e.printStackTrace();

        }
    }
    public void setCounter(){
        bst.opCount=0;
    }
    public void printCounterToFile(){
        try{
            PrintWriter out = new PrintWriter(new FileWriter((fname+".txt"), true));
            out.println(bst.opCount);
            out.close();
            bst.opCount=0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void printDateTime(String dT)
    {
        bst.findNode(dT);
    }
    public void printDateTime(String dateTime, Boolean isNotTest) {
        notTest=isNotTest;
        bst.findNode(dateTime);
    }
    public void printAllDateTimes()
    {
        bst.printTree();
    }
    public void printAllDateTimes(String keyfile)
    {
        String[] arr = new String[500];
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
        bst.printNodes(arr, i);
    }

    // Java program to demonstrate insert operation in binary search tree
    private class BST {

        /* Class containing left and right child of current node and key value*/
        private class Node {
            Power key;
            Node left, right;

            public Node(Power item) {
                key = item;
                left = right = null;
            }
            public String toString() {
                return key.toString();
            }
        }

        // Root of BST
        private Node root;
        private int opCount=0;

        // Constructor
        public BST() {
            root = null;
        }

        // This method mainly calls insertRec()
        public void insert(Power key) {
            root = insertRec(root, key);
        }

        /* A recursive function to insert a new key in BST */
        private Node insertRec(Node root, Power key) {

            /* If the tree is empty, return a new node */
            opCount++;
            if (root == null) {
                root = new Node(key);
                return root;
            }

            /* Otherwise, recur down the tree */
            if (key.compareTo(root.key)<0){
                root.left = insertRec(root.left, key);
            }
            else if (key.compareTo(root.key)>0){
                root.right = insertRec(root.right, key);
            }

            /* return the (unchanged) node pointer */
            return root;
        }

        // This method mainly calls InorderRec()
        public void printTree()  {
            inOrder(root);
        }

        public void printNodes(String[] arr, int k)
        {
            fname = "BSTSearchTestResults/SearchTestFor"+ originalfname;
            for (int i = 0; i<= k; i++) {
                findNode(arr[i]);
            }
        }

        // A utility function to do inorder traversal of BST
        private void inOrder(Node root) {
            if (root != null) {
                inOrder(root.left);
                System.out.println(root.key);
                inOrder(root.right);
            }
        }

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

        private String findNode(Power key, Node node){
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
    public static void main(String[] args)
    {

        PowerBSTApp PBA;
        if (args.length<1)
        {
            PBA = new PowerBSTApp();
            PBA.printAllDateTimes();
        }
        else
        {
            if (args[0].contains("txt"))
            {
                PBA = new PowerBSTApp(args[0]);
                PBA.printAllDateTimes(args[0]);
            }
            else
            {
                PBA = new PowerBSTApp();
                PBA.printDateTime(args[0], true);
            }
        }

    }

}
