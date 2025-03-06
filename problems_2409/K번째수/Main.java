package problems_2409.K번째수;

import java.io.*;

public class Main {
	public static void main(String [] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		long n = Long.parseLong(br.readLine());
		long k = Long.parseLong(br.readLine());

		long max = n * n;

		long min = 1;
		long mid =0;
		long count = 0;
		while (min < max) {
			mid = (min+max)/2;
			count=0;
			for (int i = 1; i <= n; i++) {
				count+=Math.min(n,mid/i);
			}

//			System.out.println(min + "~"+max + "  -> mid : "+mid + "  count : "+count);
			if (count < k) {
				min = mid+1;
			} else {
				max = mid;
			}
		}

		long minCount = 0;
		long maxCount = 0;
		if (count<k) {
			mid++;
		}
		for (int i = 1; i <= n; i++) {
			minCount+=Math.min(n,(mid-1)/i);
			maxCount+=Math.min(n,mid/i);
		}
		if (minCount>=k) {
			bw.write(String.valueOf(mid-1));
		} else {
			bw.write(String.valueOf(mid));
		}

		bw.flush();
		bw.close();
		br.close();
	}
}
