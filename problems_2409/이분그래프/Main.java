package problems_2409.이분그래프;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
	public static void main(String [] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int n = Integer.parseInt(br.readLine());

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int v = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());

			int[][] graph = new int[v+1][v+1];
			int[] group = new int[v+1];

			for (int j = 0; j < e; j++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				graph[a][b] = 1;
				graph[b][a] = 1;
			}

			/*
			 0 : 아직 미탐색
			 1 : 그룹 1
			 2 : 그룹 2

			 탐색 중, 직전 그룹값과 같은 그룹을 만날 경우 이분그래프가 아님.
			 */
			BooleanContainer result = new BooleanContainer();

			// 섬이 있을 수 있음.
			while (check(group)) {
				for (int j = 1; j < group.length; j++) {
					if (group[j] == 0) {
						dfs(graph, j, group, 1, result);
						break;
					}
				}
			}

			if (result.value) {
				bw.write("YES\n");
			} else {
				bw.write("NO\n");
			}
		}



		bw.flush();
		bw.close();
		br.close();
	}

	private static boolean check(int[] group) {
		for (int i = 1; i < group.length; i++) {
			if (group[i] == 0) {
				return true;
			}
		}
		return false;
	}

	private static class BooleanContainer {
		public Boolean value = true;
	}

	private static void dfs(int[][] graph, int v, int[] group, int preGroup, BooleanContainer result) {
		if (!result.value) {
			return;
		}
		if (group[v]==0) {
			group[v] = preGroup==1 ? 2 : 1;
		} else {
			if (group[v] == preGroup) {
				result.value = false;
				return;
			}
			return;
		}
//		System.out.println("v = " + v + ", preGroup = " + preGroup + " group = " + group[v] + " result = " + result);
		for (int i = 1; i < graph.length; i++) {
			if (graph[v][i] == 1) {
				dfs(graph, i, group, preGroup == 1 ? 2 : 1, result);
			}
		}
	}

}
