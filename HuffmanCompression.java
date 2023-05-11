import java.util.*;

public class HuffmanCompression{
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);
        String str = input.nextLine();
        ArrayList<Integer> codeList = new ArrayList<>();
        char[] arr = str.toCharArray();
        for(char c:arr){
            int i = (int)c;
            Integer code = Integer.valueOf(i);
            codeList.add(code);
        }

        //generate a map of the code and its frequency
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int j = 0; j < codeList.size(); j++){
            Integer n = codeList.get(j);
            if(map.containsKey(n)){
                Integer value = map.get(n);
                map.remove(n);
                map.put(n, value + 1);
            }
            else{
                map.put(n, 1);
            }
        }

        // generate a node list. Store each code and its frequency
        ArrayList<Node> nodeList = new ArrayList<Node>();
        Set<Integer> keySet = map.keySet();
        for (Integer key : keySet){
            nodeList.add(new Node(key, map.get(key), null, null));
        }

        Node r = generateTree(nodeList);
        // map_CC is the map of ASCII code and compression code
        Map<Integer, String> map_CC = new HashMap<Integer, String>();
        String s = "";
        preOrder(r, s, map_CC);
        
        for (int j = 0; j < codeList.size(); j++){
            Integer n = codeList.get(j);
            String str1 = map_CC.get(n);
            System.out.print(str1);
        }

        System.out.println("");

        Set<Integer> keySetCC = map_CC.keySet();
        for (Integer key : keySetCC){
            System.out.print(key.intValue());
            System.out.print(":"+map_CC.get(key));
            System.out.println("");
        }
    }

    public static Node generateTree(ArrayList<Node> nodeList){
        while(nodeList.size() > 1){
            Collections.sort(nodeList);

            Node l = nodeList.get(0);
            Node r = nodeList.get(1);
            Node p = new Node(0, l.freq + r.freq, l, r);

            // remove previous nodes
            nodeList.remove(0);
            nodeList.remove(0);

            // add the new one
            nodeList.add(p);
        }
        return nodeList.get(0);
    }

    public static void preOrder(Node root, String s, Map<Integer, String> m){
        if (root.ln == null){
            m.put(root.code, s);
            return;
        }
        // Left is 0 and right is 1.
        else{
            String s1, s2;
            s1 = s+"0";
            s2 = s+"1";
            preOrder(root.ln, s1, m);
            preOrder(root.rn, s2, m);
        }
    }
}

class Node implements Comparable<Node>{
    int code;
    int freq;
    Node ln;
    Node rn;

    public Node(int code, int freq, Node l, Node r){
        this.code = code;
        this.freq = freq;
        this.ln = l;
        this.rn = r;
    }

    public Integer getCode(){
        return code;
    }

    public Integer getFreq(){
        return freq;
    }

    public void setCode(int code){
        this.code = code;
    }

    public void setFreq(int freq){
        this.freq = freq;
    }

    public int compareTo(Node node){
        return this.freq - node.freq;
    }
}