public class RideHailingGraph {

    private int[][] adjList;
    private int[] edgeCount;
    private int numVertices;

    // Konstruktor
    public RideHailingGraph(int vertices) {
        if (vertices <= 0) {
            throw new IllegalArgumentException(
                "Jumlah simpul harus lebih dari 0."
            );
        }

        numVertices = vertices;
        adjList = new int[vertices][];
        edgeCount = new int[vertices];

        for (int i = 0; i < vertices; i++) {
            adjList[i] = new int[2];
        }
    }

    // Memeriksa nomor simpul
    private void checkVertex(int vertex) {
        if (vertex < 0 || vertex >= numVertices) {
            throw new IllegalArgumentException(
                "Nomor simpul tidak valid: " + vertex
            );
        }
    }

    // Menambahkan edge dua arah
    public void addEdge(int u, int v) {
        checkVertex(u);
        checkVertex(v);

        addDirectedEdge(u, v);
        addDirectedEdge(v, u);
    }

    // Menambahkan edge dan memperbesar array jika penuh
    private void addDirectedEdge(int u, int v) {
        if (edgeCount[u] == adjList[u].length) {
            int newCapacity = adjList[u].length * 2;
            int[] newArr = new int[newCapacity];

            for (int i = 0; i < edgeCount[u]; i++) {
                newArr[i] = adjList[u][i];
            }

            adjList[u] = newArr;
        }

        adjList[u][edgeCount[u]] = v;
        edgeCount[u]++;
    }

    // BFS untuk mencari driver terdekat berdasarkan jumlah hop
    public int bfsCariTerdekat(int asal, boolean[] adalahDriver) {
        checkVertex(asal);

        if (adalahDriver == null ||
            adalahDriver.length != numVertices) {
            throw new IllegalArgumentException(
                "Data driver tidak sesuai dengan jumlah simpul."
            );
        }

        boolean[] visited = new boolean[numVertices];
        int[] queue = new int[numVertices];
        int[] jarak = new int[numVertices];

        int head = 0;
        int tail = 0;

        visited[asal] = true;
        jarak[asal] = 0;
        queue[tail++] = asal;

        while (head < tail) {
            int curr = queue[head++];

            // Memeriksa apakah simpul merupakan lokasi driver
            if (adalahDriver[curr]) {
                System.out.println(
                    "Driver terdekat ditemukan di Simpul " + curr
                );
                System.out.println(
                    "Jarak: " + jarak[curr] + " hop."
                );
                return curr;
            }

            // Mengunjungi seluruh tetangga
            for (int i = 0; i < edgeCount[curr]; i++) {
                int neighbor = adjList[curr][i];

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    jarak[neighbor] = jarak[curr] + 1;
                    queue[tail++] = neighbor;
                }
            }
        }

        System.out.println("Tidak ada driver yang tersedia.");
        return -1;
    }

    // Program utama untuk simulasi
    public static void main(String[] args) {

        // Membuat graph dengan 8 simpul
        RideHailingGraph peta = new RideHailingGraph(8);

        // Menghubungkan simpul pada graph
        peta.addEdge(0, 1);
        peta.addEdge(0, 2);
        peta.addEdge(1, 3);
        peta.addEdge(2, 3);
        peta.addEdge(3, 4);
        peta.addEdge(4, 5);
        peta.addEdge(4, 6);
        peta.addEdge(5, 7);
        peta.addEdge(6, 7);

        // Menentukan lokasi driver aktif
        boolean[] adalahDriver = new boolean[8];
        adalahDriver[5] = true;
        adalahDriver[6] = true;

        System.out.println("=== SIMULASI PENCARIAN DRIVER GOCEPAT ===");
        System.out.println(
            "Driver tersedia di Simpul 5 dan Simpul 6.\n"
        );

        // Kasus 1
        System.out.println("Pengujian 1: Penumpang berada di Simpul 0");
        peta.bfsCariTerdekat(0, adalahDriver);

        // Kasus 2
        System.out.println("\nPengujian 2: Penumpang berada di Simpul 7");
        peta.bfsCariTerdekat(7, adalahDriver);

        // Kasus 3
        System.out.println("\nPengujian 3: Penumpang berada di Simpul 4");
        peta.bfsCariTerdekat(4, adalahDriver);
    }
}

