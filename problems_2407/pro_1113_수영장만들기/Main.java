package problems_2407.pro_1113_수영장만들기;

import java.io.*;
import java.util.*;

public class Main {

	static class Node {
		@Override
		public String toString() {
			return "Node{" +
							"size=" + size +
							", y=" + y +
							", x=" + x +
							", sector=" + sector +
							'}';
		}

		int size;
		int y;
		int x;

		// -1 : 벽, 1 이상 : 지역일 수 있는 후보지.
		int sector = 0;

		public Node(int size, int y, int x) {
			this.size = size;
			this.y = y;
			this.x = x;
		}
		public void finallyWater() {
			water.add(this);
		}

		public void maybeWall() {
			sector=-1;
		}
	}
	// 0이면 미방문, -1이면 벽으로 확정, 1이상이면
	static int[][] isVisited;
	static Node[][] nodes;
	static Queue<Node> wall =new ArrayDeque<>();
	static Queue<Node> water=new ArrayDeque<>();
	static int minOuterWallSize =1000;
	static List<Integer> sectorsHeight = new ArrayList<>();
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		/* 여러 정수 쓰는 경우 */
		int y = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());

		/* 배열 필요한 경우 */
		isVisited = new int[y][x];
		nodes = new Node[y][x];
		for (int i = 0; i < y; i++) {
			String s = br.readLine();
			for (int j = 0; j < x; j++) {
				nodes[i][j] = new Node(Integer.parseInt(s.charAt(j)+""),i,j);
			}
		}


		// 1. 1차 외벽 찾기.
		findOuterWall();

		// 2. 물이 들어올 수 있는 후보 중에서, 최소 외벽보다 더 높은 칸을 찾는다.
		findZeroSector();

		logWall();


		int result = 0;

		bw.write(result+"");

		bw.flush();
		br.close();
		bw.close();
	}


	static Queue<Node> queue = new ArrayDeque<>();

	private static void findZeroSector() {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				if (nodes[i][j].sector == Integer.MIN_VALUE) { //-2는 외벽이다.
					ifNotWallAddWater(nodes[i][j]);
				}
			}
		}
	}

	private static void ifNotWallAddWater(Node node) {
		for (int i = 0; i < 4; i++) {
			int ny = node.y+dy[i];
			int nx = node.x+dx[i];
			if (ny>=0 && nx>=0 && ny<nodes.length && nx<nodes[0].length) {
				addWaterIfNotWall(node, nodes[ny][nx]);
			}
		}
	}

	private static void addWaterIfNotWall(Node node, Node nextNode) {
		if (nextNode.sector==0) {
			if (node.size > nextNode.size) {
				nextNode.sector=sectorsHeight.size();
				sectorsHeight.add(node.size);
				queue.add(nextNode);
				bfs(nextNode.sector);
			}
		}
	}

	private static void bfs(int sector) {
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			System.out.println("물이 들어올 수 있는 후보 : "+node);
			for (int i = 0; i < 4; i++) {
				int ny = node.y+dy[i];
				int nx = node.x+dx[i];
				if (ny>=0 && nx>=0 && ny<nodes.length && nx<nodes[0].length) {
					ifWaterAddAndCheck(nodes[ny][nx], sector);
				}
			}
		}
	}

	// 혹시 시간 문제를 개선해야한다면 sector에 크기만 저장할 게 아니라, 갯수까지 같이 저장한다.
	private static void ifWaterAddAndCheck(Node node, int sector) {
		System.out.println("물이 들어올 수 있나...? "+node);
	}

	private static void findOuterWall() {
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
				if (i==0 || j==0 || i== nodes.length-1 || j==nodes[0].length-1) {
					wall.add(nodes[i][j]);
					findOuterWallBfs();
				}
			}
		}
	}
	private static void findOuterWallBfs() {
	/*
			1차 외벽 찾기는 네 면에서 탐색한다. 네 면중 탐색되지 않은 포인트가 존재할 경우 BFS를 계속 수행한다.
			같거나, 더 높은 벽이 나오면 외벽이라는 의미이다.
			더 낮은 벽이 나오면 그것은 큐에 추가하지 않음.  (탐색 순서에 따른 높이 차이여도, 마킹하지 않기 때문에 다음 면 탐색시 외벽임이 증명된다.)
			단, 이 때 외벽의 최소높이를 기록해둔다.
			자, 이제 1차 수영장의 물은 최소높이보다 높을 수 없다.
	 */
		while (!wall.isEmpty()){
			Node node = wall.poll();
			if (isVisited[node.y][node.x]==0) {
				isVisited[node.y][node.x]=-1;
				node.sector=Integer.MIN_VALUE;
			} else {
				continue;
			}
			if (node.x > 0) {
				ifWallAddAndCheck(nodes[node.y][node.x-1],node);
			}
			if (node.y > 0) {
				ifWallAddAndCheck(nodes[node.y-1][node.x],node);
			}
			if (node.x < nodes[0].length-1) {
				ifWallAddAndCheck(nodes[node.y][node.x+1],node);
			}
			if (node.y < nodes.length-1) {
				ifWallAddAndCheck(nodes[node.y+1][node.x],node);
			}
		}
	}
	private static void ifWallAddAndCheck(Node next, Node now) {
		if (next.size >= now.size) { // 외벽보다 낮지만 않으면 외벽으로 취급될 수 있다.
			wall.add(next);
		} else {
			minOuterWallSize =Math.min(now.size, minOuterWallSize);
		}
	}
	private static void logWall() {
//		System.out.println("물에 닿은 외벽 최소높이 : " + minOuterWallSize);
//		System.out.println("남은 Wall queue size : " +wall.size() );
		System.out.println();
		for (int i = 0; i < nodes.length; i++) {
			for (int j = 0; j < nodes[0].length; j++) {
//				System.out.print((isVisited[i][j]>=0 ? isVisited[i][j] : "-")+" ");
				Node node = nodes[i][j];
				if (node.sector<0) {
					System.out.print(node.sector == Integer.MIN_VALUE ? " -" : ""+node.sector);
				} else {
//					System.out.print(" 0");
					System.out.print(" "+node.sector);
				}
//				System.out.print(nodes[i][j].size+" ");
			}
			System.out.println();
		}
	}
}