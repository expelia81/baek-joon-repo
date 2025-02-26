package problems_2409.파티;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public class Main {

	public static class Edge implements Comparable<Edge>{
		Node node;
		int length;

		public Edge(Node node, int length) {
			this.node = node;
			this.length = length;
		}

		@Override
		public int compareTo(@NotNull Edge o) {
			return this.length - o.length;
		}
	}

	public static class Node {
		int index;
		int length = Integer.MAX_VALUE;
		List<Edge> list = new ArrayList<>();
		public Node(int index) {
			this.index = index;
		}
	}

	static Node[] nodes;
	static PriorityQueue<Edge> queue = new PriorityQueue<>();

	public static void main(String [] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		nodes = new Node[n+1];

		for (int i = 0; i <= n; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int length = Integer.parseInt(st.nextToken());
//			nodes[start].list.add(new Edge(nodes[end], length));
			nodes[end].list.add(new Edge(nodes[start], length));
		}

		nodes[k].length = 0;
		queue.add(new Edge(nodes[k],0));


		while (!queue.isEmpty()) {
			Edge now = queue.poll();

			now.node.list.forEach(edge -> {
				int length = edge.length + now.length;
				if (length < edge.node.length) {
					edge.node.length = length;
					queue.add(new Edge(edge.node, length));
				}
			});
		}

		for (Node node : nodes) {
			System.out.println(node.length);

		}






	}


}
