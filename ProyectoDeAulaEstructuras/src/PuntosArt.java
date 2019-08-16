import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import java.util.StringTokenizer;
/*
 * 
 *@Autores 
 *Camilo Andres Piza Hernandez 
 *Julio Alberto Parra
 *Jaime Steven Leon
 * 
 */
public class PuntosArt {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("CamiloAndresPiza.txt"), "UTF8"));
//				BufferedReader br = new BufferedReader(
//						   new InputStreamReader(
//				                      new FileInputStream("JaimeLeon.txt"), "UTF8"));
//				BufferedReader br = new BufferedReader(
//				   new InputStreamReader(
//		                   new FileInputStream("JulioParra.txt"), "UTF8"));

		int n = Integer.parseInt(br.readLine());

		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[n];
		HashMap<Integer, Long> map = new HashMap<>();
		HashMap<Long, String> data = new HashMap<>();
		HashMap<Long, Integer> post = new HashMap<>();

		for (int i = 0; i < adj.length; i++) {
			adj[i] = new ArrayList<>();
		}

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			BigInteger id = new BigInteger(st.nextToken());

			String name = "";
			while (st.hasMoreTokens()) {
				name += st.nextToken() + " ";
			}
			map.put(i, id.longValue());
			data.put(id.longValue(), name);
			post.put(id.longValue(), i);
		}

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String s = st.nextToken();
			BigInteger bi = new BigInteger(s);
			Integer pos = post.get(bi.longValue());
			while (st.hasMoreTokens()) {
				String value = st.nextToken();
				BigInteger bi1 = new BigInteger(value);
				adj[pos.intValue()].add(post.get(bi1.longValue()));
			}
		}
		for (int i = 0; i < adj.length; i++) {
			if (adj[i].isEmpty()) 
				adj[i].add(0);
		}
		Graph g1 = new Graph(n, adj);
		System.out.print("Articulation Points: \n");
		ArrayList<Integer> arp = g1.APinit();
		int i=0;
		for (Integer integer : arp) {
			i++;
			System.err.println(data.get(map.get(integer.intValue())));
		}
		System.out.println(i);
		br.close();
	}
}
class Graph {

	public int V;
	public ArrayList<Integer>[] adj;
	public int time = 0;

	public Graph(int v, ArrayList<Integer>[] adj) {
		V = v;
		//Adjacent List
		this.adj = adj;
	}

	ArrayList<Integer> APinit() {
		boolean visited[] = new boolean[V];
		int disc[] = new int[V];
		int low[] = new int[V];
		int parent[] = new int[V];
		boolean ap[] = new boolean[V];
		Arrays.fill(parent, -1); 	
		Arrays.fill(ap, false);

		ArrayList<Integer> pos = new ArrayList<>();
		for (int i = 0; i < V; i++) 
			if (!visited[i]) Articulation_Point(i, visited, disc, low, parent, ap);
		for (int i = 0; i < V; i++) if (ap[i]) pos.add(i);
		return pos;
	}

	void Articulation_Point(int s, boolean vis[], int disc[], int low[], int parent[], boolean ap[]) {
		int children = 0;
		vis[s] = true;
		disc[s] = ++time;
		low[s] = disc[s];
		for (int j = 0; j < adj[s].size(); j++) {
			int v=adj[s].get(j);
			if (!vis[v]) {
				children++;
				parent[v] = s;
				Articulation_Point(v, vis, disc, low, parent, ap);
				low[s] = Math.min(low[s], low[v]);
				if (parent[s] == -1 && children > 1) ap[s] = true;
				if (parent[s] != -1 && low[v] >= disc[s]) ap[s] = true;
			}   else if (v != parent[s]) low[s] = Math.min(low[s], disc[v]);
		}
	}
}