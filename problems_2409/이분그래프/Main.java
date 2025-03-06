package problems_2409.이분그래프;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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

			int[] group = new int[v+1];
			List<Integer>[] list = new List[v+1];
			for (int j = 0; j < list.length; j++) {
				list[j] = new ArrayList<>();
			}

			for (int j = 0; j < e; j++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
//				graph[a][b] = 1;
//				graph[b][a] = 1;
				list[a].add(b);
				list[b].add(a);
			}

			BooleanContainer result = new BooleanContainer();

			// 섬이 있을 수 있음.
			while (check(group) && result.value) {
				for (int j = 1; j < group.length; j++) {
					if (group[j] == 0) {
						dfs(j, group, 1, result, list);
						break;
					}
				}
			}

			for (int j = 1; j < group.length; j++) {

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

	private static void dfs(int v, int[] group, int preGroup, BooleanContainer result, List<Integer>[] graph) {
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

		for (int i = 0; i < graph[v].size(); i++) {
			if (group[graph[v].get(i)] == 0) {
				dfs(graph[v].get(i), group, group[v], result, graph);
			} else if (group[graph[v].get(i)] == group[v]) {
				result.value = false;
				return;
			}
		}

	}

}
