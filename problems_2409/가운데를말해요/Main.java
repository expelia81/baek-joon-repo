package problems_2409.가운데를말해요;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String [] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int n = Integer.parseInt(br.readLine());


		PriorityQueue<Integer> left = new PriorityQueue<>(((o1, o2) -> o2-o1));
		PriorityQueue<Integer> right = new PriorityQueue<>();



//
//		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for(int i=1; i<=n; i++) {
//		for(int i=1; i<=n; i++) {
//			int temp = Integer.parseInt(st.nextToken());
			int temp = Integer.parseInt(br.readLine());

			add(left, right, temp);

			if (left.size() >= right.size()) {
				bw.write(left.peek() + "\n");
			} else {
				bw.write(right.peek() + "\n");
			}
//			PriorityQueue<Integer> finalLeft = new PriorityQueue<>(left);
//			PriorityQueue<Integer> finalRight = new PriorityQueue<>(right);
//			String leftstr = "";
//			String rightstr = "";
//			while (!finalLeft.isEmpty()) {
//				leftstr = finalLeft.poll() + " " + leftstr;
//			}
//			while (!finalRight.isEmpty()) {
//				rightstr += finalRight.poll() + " ";
//			}
//			System.out.println("left : " + leftstr);
//			System.out.println("right : " + rightstr);
		}

		bw.flush();
		bw.close();
		br.close();
	}

	private static void add(PriorityQueue<Integer> left, PriorityQueue<Integer> right, int temp) {
//		if (left.isEmpty() && right.isEmpty()) {
//			left.add(temp);
//			return;
//		}
//
//		if (left.size() > 0 && right.size() > 0) {
//			if (left.peek() <= temp && temp <= right.peek()) {
//				left.add(temp);
//			} else if (temp < left.peek()) {
//				left.add(temp);
//				if (left.size() > right.size()) {
//					right.add(left.poll());
//				}
//			} else {
//				right.add(temp);
//				if (right.size() > left.size()) {
//					left.add(right.poll());
//				}
//			}
//			return;
//		}
//
//		if (left.size()==0) {
//			if (right.peek() >= temp) {
//				left.add(temp);
//			} else {
//				left.add(right.poll());
//				right.add(temp);
//			}
//			return;
//		}
//		if (right.size()==0) {
//			if (left.peek() <= temp) {
//				right.add(temp);
//			} else {
//				right.add(left.poll());
//				left.add(temp);
//			}
//		}

		// left가 비어있거나 num이 left의 최대값보다 작거나 같으면 left에 추가
		if (left.isEmpty() || temp <= left.peek()) {
			left.add(temp);
		} else {
			right.add(temp);
		}

		// 밸런싱: 두 큐의 크기 차이가 최대 1이어야 함
		// left가 right보다 2개 이상 많으면
		if (left.size() > right.size() + 1) {
			right.add(left.poll());
		}
		// right가 left보다 많으면
		else if (right.size() > left.size()) {
			left.add(right.poll());
		}
	}
}

/*
12
1
9
8
5
3
2
2
7
8
100
200
300
 */