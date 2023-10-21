import java.util.Scanner;

public class Knights {
    // Adjecency matrix of graph
    private int[][] graph;
    private int n, nEdge;
    private boolean[] visited;
    private String[] labels;

    public Knights(int n, String[] labels) {
        this.n = n;
        this.nEdge = 0;
        graph = new int[n][n];
        this.visited = new boolean[n];
        resetVisited();
        this.labels = labels;

        System.out.println("Graph ready.");
    }

    public int getN() {
        return this.n;
    }

    public void updateWeight(int row, int col, int newValue) {
        this.graph[row][col] = newValue;
        if (newValue > 0) {
            this.nEdge += 1;
        }
    }

    private boolean validateNode(int v) {
        return 0 <= v && v < this.n;
    }

    public void addVisited(int v) {
        this.visited[v] = true;
    }

    public boolean isVisited(int v) {
        return this.visited[v];
    }

    public String getLabel(int v) {
        String label;
        try {
            label = this.labels[v];
        } 
        catch(Exception ex) {
            label = String.valueOf(v);
        }
        
        return label;
    }

    private int bestSearch(int v, int w) {
        // returns the function result f(n) = h(n)
        int min = 999, node = v;
        addVisited(v);
        for(int i = 0; i < this.n; i++) {
            int weight = this.graph[v][i];
            if(i == w && weight > 0) {
                return w;
            }
            if (weight < min && weight > 0 && !isVisited(i)) {
                min = weight;
                node = i;
            }
        }
        return node;
    }

    private void resetVisited() {
        for (int i = 0; i < this.n; i++) {
            this.visited[i] = false;
        }
    }

    public int bestFirstSearch(int v, int w) {
        // return the "greedy" best-first search path 
        if(!validateNode(v) || !validateNode(w)) {
            System.out.printf("Invalid vertex. %d or %d\n", v, w);
            return -1;
        } 
        int best = bestSearch(v, w);
        int prevbest = v;
        int edgeTraversed = 1; // To avoid infinite iterations
        int cost = 0;
        do {
            cost += this.graph[prevbest][best];
            prevbest = best;
            best = bestSearch(best, w);
            edgeTraversed += 1;
            if(edgeTraversed > this.nEdge) {
                return -1;
            }
        } while(best != w);
        cost += this.graph[prevbest][w];
        resetVisited();  // reset visited nodes list

        return cost;
    }

    public int getNEdge() {
        return this.nEdge;
    }

    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        int n = stdin.nextInt();
        String[] labels = {"oradea", "zerind", "arad", "timisoara", "lugoj", "mehadia", "drobeta", 
        "craiova", "sibiu", "rv", "fagaras", "pitesti", "bucharest", "giurgiu", "urziceni", "hirsova", 
        "eforie", "vaslui", "iasi", "neamt"};
        Knights chessboard = new Knights(n, labels);

        // Read adjencency matrix
        int weight;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weight = stdin.nextInt();
                chessboard.updateWeight(i, j, weight);
            } 
        }

        int samples = stdin.nextInt();

        // Read v and w and print costs
        int v, w, cost;
        for(int i = 0; i < samples; i++) {
            v = stdin.nextInt();
            w = stdin.nextInt();
            cost = chessboard.bestFirstSearch(v, w);
            String labelV = chessboard.getLabel(v);
            String labelW = chessboard.getLabel(w);
            System.out.printf("cost f(%s, %s): %d \n", labelV, labelW, cost);
        }

        stdin.close();
        System.out.printf("Graph Configuration (V, E): (%d, %d)\n", 
        chessboard.getN(), chessboard.getNEdge());

        
    }
}
