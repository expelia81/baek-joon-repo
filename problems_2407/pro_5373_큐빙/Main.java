package problems_2407.pro_5373_큐빙;

import java.io.*;
import java.util.StringTokenizer;

public class Main {

	public class Center {

	}
	public class Edge {

	}
	public class Corner {

	}

	// 5*5 중, 가운데 3*3이 면을 의미한다.
	static int[][][] cube = new int[5][5][5];
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int n = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		/* 여러 정수 쓰는 경우 */
		int x = Integer.parseInt(st.nextToken());
		int y = Integer.parseInt(st.nextToken());

		/* 배열 필요한 경우 */
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i]=Integer.parseInt(st.nextToken());
		}

		bw.flush();
		br.close();
		bw.close();
	}
}