
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Graph {

    private final int V;
    private final List<List<Integer>> adj;
    public static List cycle = new ArrayList();

    public Graph(int V)
    {
        this.V = V;
        adj = new ArrayList<>(V);

        for (int i = 0; i < V; i++) {
            adj.add(new LinkedList<>());
        }
    }

    private boolean isCyclicUtil(int i, boolean[] visited, boolean[] recStack)
    {

        //Mark the current node as visited and
        //part of recursion stack
       if (recStack[i])
           return true;

       if (visited[i])
           return false;

       visited[i] = true;

       recStack[i] = true;

       List<Integer> children = adj.get(i);

       if (children.size() != 0) {
           cycle.add(children.get(0));
       }


       for (Integer c: children)
           if (isCyclicUtil(c, visited, recStack))
           {
               return true;
           }

        recStack[i] = false;

        return false;
    }

    private void addEdge(int source, int dest) {
        adj.get(source).add(dest);
    }

    private boolean isCyclic() {

        //Mark all the verticles as not visited and
        //not part of recursion stack
        boolean[] visited = new boolean[V];
        boolean[] recStack = new boolean[V];

        for (int i = 0; i < V; i++)
            if (isCyclicUtil(i, visited, recStack))
                return true;

        return false;
    }

    public static void main(String ...args) throws IOException
    {

       Scanner scanner = new Scanner(new File("C:\\Users\\Hudanov\\Desktop\\dfs\\resources\\test.txt"));

       Graph graph = new Graph(10000);


        while (scanner.hasNextLine()) {
           String line = scanner.nextLine();

           String[] temp;
           String delimiter = " ";
           temp = line.split(delimiter);

         if (temp.length == 2) {
             System.out.println(temp[0] + " " + temp[1]);
             graph.addEdge(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
         } else {
              graph = new Graph(Integer.parseInt(temp[0]));
         }
           System.out.println();
       }


       if(graph.isCyclic())
       {
           for (Object element: cycle) {
               System.out.print(element + " <-- ");
           }
           System.out.println("Graph contains cycle");
       }
       else
           System.out.println("Graph doesn't contain cycle");
    }
}
