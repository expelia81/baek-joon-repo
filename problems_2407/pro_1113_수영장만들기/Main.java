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
	}
	// 0이면 미방문, -1이면 벽으로 확정, 1이상이면
	static int[][] isVisited;
	static Node[][] nodes;
	static Queue<Node> wall =new ArrayDeque<>();
	static int minOuterWallSize =1000;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};

	static int[][][] sectors;
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

		sectors = new int[10][y][x];

		// 3차원으로 보자. 2차원으로 쪼개놓으면, 높이를 고려할 필요가 없다.
		for (int i = 1; i <= 9; i++) {
			for (int j = 0; j < y; j++) {
				for (int k = 0; k < x; k++) {
					int size = nodes[j][k].size;
					if (size > i) {
						sectors[i][j][k] = 1;
					}
				}
			}
		}


//		logWall();

		for (int i = 1; i <= 9; i++) {
			int[][] sector = sectors[i];
			isVisited = new int[y][x];
			for (int j = 0; j < sector.length; j++) {
				for (int k = 0; k < sector[0].length; k++) {
					if (sector[j][k] == 0 && isVisited[j][k]==0 && nodes[j][k].sector ==0) {
						queue.add(nodes[j][k]);
						result+=bfs(sector);
					}
				}
			}
		}


		bw.write(result+"");

		bw.flush();
		br.close();
		bw.close();
	}

	private static int bfs(int[][] sector) {
		int size = 0;
		boolean isWall = false;
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			if (node.sector == Integer.MIN_VALUE) {
				isWall = true;
			}
			if (isVisited[node.y][node.x] == 0) {
				isVisited[node.y][node.x] = 1;
				if (sector[node.y][node.x] == 0) {
					size++;
					for (int j = 0; j < 4; j++) {
						int ny = node.y+dy[j];
						int nx = node.x+dx[j];
						if (ny>=0 && nx>=0 && ny<sector.length && nx<sector[0].length) {
							if (sector[ny][nx] == 0) {
								queue.add(nodes[ny][nx]);
							}
						}
					}
				} else { //벽을 만나면 종료한다.
					continue;
				}
			}
		}
//		System.out.println("sector : "+i + " result : "+size + " isWall : "+isWall);
		if (isWall) {
			return 0;
		}

		return size;
	}

	static int result = 0;

	static Queue<Node> queue = new ArrayDeque<>();


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