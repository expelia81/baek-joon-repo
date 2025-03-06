package problems_2409.빙산;
import java.io.*;
import java.util.*;

public class Main {
	static int n, m;
	static int[][] map;
	static boolean[][] visited;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		map = new int[n][m];

		// 지도 입력
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int years = 0;
		while (true) {
			// 빙산 개수 확인
			int icebergCount = countIcebergs();

			// 빙산이 없으면 0 출력
			if (icebergCount == 0) {
				bw.write("0");
				break;
			}

			// 빙산이 2개 이상으로 분리되면 년수 출력
			if (icebergCount >= 2) {
				bw.write(String.valueOf(years));
				break;
			}

			// 빙산 녹이기
			meltIcebergs();
			years++;
		}

		bw.flush();
		bw.close();
		br.close();
	}

	// 빙산 개수 세기 (DFS 이용)
	static int countIcebergs() {
		visited = new boolean[n][m];
		int count = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] > 0 && !visited[i][j]) {
					dfs(i, j);
					count++;
				}
			}
		}
		return count;
	}

	// 연결된 빙산 탐색
	static void dfs(int x, int y) {
		visited[x][y] = true;

		for (int k = 0; k < 4; k++) {
			int nx = x + dx[k];
			int ny = y + dy[k];

			if (nx >= 0 && nx < n && ny >= 0 && ny < m
							&& map[nx][ny] > 0 && !visited[nx][ny]) {
				dfs(nx, ny);
			}
		}
	}

	// 빙산 녹이기
	static void meltIcebergs() {
		int[][] meltAmount = new int[n][m];

		// 각 위치별 녹는 양 계산
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (map[i][j] > 0) {
					int seaCount = countSea(i, j);
					meltAmount[i][j] = Math.min(map[i][j], seaCount);
				}
			}
		}

		// 동시에 녹이기
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				map[i][j] = Math.max(0, map[i][j] - meltAmount[i][j]);
			}
		}
	}

	// 특정 위치 주변 바다 개수 세기
	static int countSea(int x, int y) {
		int seaCount = 0;
		for (int k = 0; k < 4; k++) {
			int nx = x + dx[k];
			int ny = y + dy[k];

			if (nx >= 0 && nx < n && ny >= 0 && ny < m && map[nx][ny] == 0) {
				seaCount++;
			}
		}
		return seaCount;
	}
}
