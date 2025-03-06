package problems_2409.나무재테크;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	private static Ground[][] graph;

	private static int[] dx = {0, 0, 1, -1, 1, -1, 1, -1};
	private static int[] dy = {1, -1, 0, 0, 1, -1, -1, 1};

	private static int treeCount = 0;
	private static int n = 0;
	private static int k = 0;
	public static void main(String [] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		n = Integer.parseInt(st.nextToken());
		treeCount = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		graph = new Ground[n+2][n+2];
		for (int i = 0; i < n+2; i++) {
			for (int j = 0; j < n + 2; j++) {
				graph[i][j]=new Ground();
				if (i==0 || j==0 || i==n+1 || j==n+1) {
					graph[i][j].enable=false;
				}
			}
		}

		for (int i = 1; i < n+1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 1; j < n+1; j++) {
				graph[i][j].growth=Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < treeCount; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int ag = Integer.parseInt(st.nextToken());
			graph[x][y].trees.add(ag);
		}

		for (int i = 0; i < k; i++) {
			spring();
			fall();
//			logNut();
//			logGrowth();
//			logTrees();
		}

		bw.write(String.valueOf(treeCount));



		bw.flush();
		bw.close();
		br.close();
	}

	private static void spring() {
		for (int i = 1; i < n+1; i++) {
			for (int j = 1; j < n+1; j++) {
				List<Integer> tree = graph[i][j].trees;
				tree.sort((a,b)->a-b);
				Ground ground = graph[i][j];
				for (int l = 0; l < tree.size(); l++) {
//					System.out.println("now tree : " + tree.get(l) +  " nutrient : "+graph[i][j].nutrient);
					if (tree.get(l) > graph[i][j].nutrient) {
						int count = tree.size() - l;
						for (int m = 0; m < count; m++) {
							// 여름이었다...
							ground.nutrient+=tree.get(tree.size()-1)/2;
							// 여기까지 여름
							tree.remove(tree.size()-1);
							treeCount--;
						}
						break;
					}
					graph[i][j].nutrient-=tree.get(l);
					tree.set(l,tree.get(l)+1);
				}
			}
		}
	}
	private static void fall() {
		for (int i = 1; i < n+1; i++) {
			for (int j = 1; j < n+1; j++) {
				Ground ground = graph[i][j];
				ground.nutrient+= ground.growth;
				for (int age : ground.trees) {
					if (age%5==0) {
						for (int l = 0; l < 8; l++) {
							int x = i+dx[l];
							int y = j+dy[l];
							if (graph[x][y].enable) {
								graph[x][y].trees.add(1);
								treeCount++;
							}
						}
					}
				}
			}
		}
	}

	private static void logNut() {
		System.out.println("--------현재 영양소-------");
		for (int i = 1; i < graph.length-1; i++) {
			for (int j = 1; j < graph.length-1; j++) {
				System.out.print(graph[i][j].nutrient + " ");
			}
			System.out.println();
		}
	}

	private static void logGrowth() {
		System.out.println("--------성장률-------");
		for (int i = 1; i < graph.length-1; i++) {
			for (int j = 1; j < graph.length-1; j++) {
				System.out.print(graph[i][j].growth + " ");
			}
			System.out.println();
		}
	}
	private static void logTrees() {
		System.out.println("--------나무갯수-------");
		for (int i = 1; i < graph.length-1; i++) {
			for (int j = 1; j < graph.length-1; j++) {
				System.out.print(graph[i][j].trees.size() + " ");
			}
			System.out.println();
		}
	}

	private static class Ground {
		 int nutrient=5;
		 int growth=0;
		 boolean enable = true;
		 List<Integer> trees = new ArrayList<>();

	}

	/*
5 2 6
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 3 2 3 2
2 1 3
3 2 3
	 */
}
