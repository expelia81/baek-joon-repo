package problems_2406_2.pro_2141_우체국_미해결;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
	private static class Node {
		int humans;
		int point;

		public Node(int humans, int point) {
			this.humans = humans;
			this.point = point;
		}

		@Override
		public String toString() {
			return "Node{" +
							"humans=" + humans +
							", point=" + point +
							'}';
		}
	}
	private static long rightHuman=0;
	private static long leftHuman=0;
	private static long distanceHumans =0;
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int n = Integer.parseInt(br.readLine());

		StringTokenizer st;


		/* 배열 필요한 경우 */
		Node[] arr = new Node[n];
//		arr[0]=new Node(0,0);
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine(), " ");

			/* 여러 정수 쓰는 경우 */
			int point = Integer.parseInt(st.nextToken());
			int hum = Integer.parseInt(st.nextToken());
			arr[i]=new Node(hum,point);
			rightHuman+=hum;
			distanceHumans += (long) point * hum;
		}
		Arrays.sort(arr, Comparator.comparingInt(o -> o.point));

		/*
		  최초 단계에서, sum을 한 번은 계산하도록 한다.
		  0번째에서부터 시작, 1칸씩 이동할 때마다
		  left, right의 합이 오히려 높아지는 순간, 바로 그 직전점이 최고점이다.
		 */

		// 최초 거리합을 0번 인덱스 기준으로 재정립
		distanceHumans -= (long) arr[0].point * rightHuman;
		rightHuman-=arr[0].humans;
		leftHuman+=arr[0].humans;

		// 최저 거리가 되는 값의 크기로 할 것이다.
		long resultDistance = distanceHumans;
		long resultIndex=arr[0].point;

		// 1번 인덱스부터 시작한다. i-1번째 포인트와 i번째 포인트의 거리를 잰다.
		for (int i = 1; i < n; i++) {
			/*
			  i-1번째 포인트와 i번째 포인트에서의 거리를 잰다.
			 */
			Node node= arr[i];
			int distanceDifference = node.point - arr[i-1].point;
			// 빼기 전에 distanceHuman을 계산한다.
			// 오른 쪽으로 이동하므로, 우측 사람들이 줄어드는 만큼 왼쪽 사람들 거리가 커진다.
			distanceHumans -= (long) distanceDifference * rightHuman;
			distanceHumans += (long) distanceDifference * leftHuman;
			// 오른쪽으로 이동하므로, 우측 사람들 수를 줄이고, 좌측 사람들 수를 늘린다.
			leftHuman+=node.humans;
			rightHuman-=node.humans;
//			System.out.println("distanceHumans: " + distanceHumans + " / resultDistance: " + resultDistance);
			if (resultDistance>distanceHumans) {
				resultDistance=distanceHumans;
				resultIndex=node.point;
			}
		}
		bw.write(String.valueOf(resultIndex));

		bw.flush();
		br.close();
		bw.close();
	}
}