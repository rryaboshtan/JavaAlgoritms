import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PrefixTree {

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(".\\numbers.txt"));

        TreeNode root = new TreeNode(' ');
        for (String line : lines) {
            root.insert(line);
        }

        // System.out.println(root.containString("18А0605"));

        // List<String> extractedFromTree = new ArrayList<>();
        // root.getAllNumbers("", extractedFromTree);

        writeTreeToFile(".\\tree.dat", root);

        TreeNode fromFile = readFromFile(".\\tree.dat");

        List<String> extractedFromTree = new ArrayList<>();
        fromFile.getAllNumbers("", extractedFromTree);
        
    }

    private static void writeTreeToFile(String path, TreeNode root) {
        try {
            PrintWriter out = new PrintWriter(path);
            root.writeToFile(out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static TreeNode readFromFile(String path) {
        TreeNode root = new TreeNode(' ');
        try {
            FileReader reader = new FileReader(path);
            reader.read();
            root.readFromFile(reader, root);
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    static class TreeNode {
        char value;

        List<TreeNode> children;

        public TreeNode(char value) {
            this.value = value;
        }

        public void insert(String data) {
            if (data.length() == 0) {
                return;
            }
            if (children == null) {
                children = new ArrayList<>();
            }
            char c = data.charAt(0);
            TreeNode child = findNodeByChar(c);

            if (child == null) {
                child = new TreeNode(c);
                children.add(child);
            }
            child.insert(data.substring(1));

        }

        private TreeNode findNodeByChar(char c) {
            if (children != null) {
                for (TreeNode node : children) {
                    if (node.value == c) {
                        return node;
                    }
                }
            }
            return null;
        }

        private boolean containString(String str) {
            TreeNode current = this;
            for (int i = 0; i < str.length(); i++) {
                current = current.findNodeByChar(str.charAt(i));
                if (current == null) {
                    return false;
                }
            }
            return true;
        }

        public void getAllNumbers(String path, List<String> result) {
            if (value != ' ') {
                path = path + value;
            }
            if (children != null) {
                for (TreeNode node : children) {
                    node.getAllNumbers(path, result);
                }
            } else {
                result.add(path);
            }
        }

        public void writeToFile(PrintWriter writer) {
            writer.write(value);

            if (children != null) {
                for (TreeNode node : children) {
                    node.writeToFile(writer);
                }
            }
            writer.write(']');
        }

        public void readFromFile(FileReader reader, TreeNode root) throws IOException {
            char ch;
            while ((ch = (char) reader.read()) != ']') {
                TreeNode treeNode = new TreeNode(ch);
                treeNode.readFromFile(reader, root);
                if (children == null) {
                    children = new ArrayList<>();
                }
                children.add(treeNode);
            }
        }
    }
}