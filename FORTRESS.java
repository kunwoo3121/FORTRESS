import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;
public class FORTRESS {
	
	static int[] height;
	
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		
		int C = sc.nextInt();
		
		for(int i = 0; i < C; i++)
		{
			int N = sc.nextInt();
			
			int[][] rampart = new int[N][3];
			
			ArrayList<Integer> graph[] = new ArrayList[N];
			
			height = new int[N];
			
			for(int j = 0; j < N; j++)
			{
				graph[j] = new ArrayList<Integer>();
			}
			
			for(int j = 0; j < N; j++)
			{
				rampart[j][0] = sc.nextInt();
				rampart[j][1] = sc.nextInt();
				rampart[j][2] = sc.nextInt();
			}
			
			Arrays.sort(rampart, (t1, t2) -> {
					return Integer.compare(t1[2], t2[2]);
			});
			
			graph = makeTree(rampart, N);
			
			System.out.println(max_len(graph, N));
		}
	}
	
	public static ArrayList<Integer>[] makeTree(int[][] rampart, int N)
	{
		ArrayList<Integer> graph[] = new ArrayList[N];
		
		for(int j = 0; j < N; j++)
		{
			graph[j] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < N; i++)
		{
			for(int j = i + 1; j < N; j++)
			{
				if(isChild(rampart[i], rampart[j]))
				{
					graph[j].add(i);
					height[j] = Math.max(height[i] + 1, height[j]);
					break;
				}
			}
		}
		
		return graph;
	}
	
	public static boolean isChild(int[] a, int[] b)
	{
		int distanceX = (a[0] - b[0]) * (a[0] - b[0]);
		int distanceY = (a[1] - b[1]) * (a[1] - b[1]);
		int distanceR = (a[2] - b[2]) * (a[2] - b[2]);
		
		if(distanceX + distanceY <= distanceR) return true;
		else return false;
	}
	
	public static int max_len(ArrayList<Integer>[] graph, int N)
	{
		int[] maxLen = new int[N];
		int result = 0;
		
		for(int i = 0; i < N; i++)
		{
			ArrayList<Integer> h= new ArrayList<Integer>();
			
			int n = graph[i].size();
			
			for(int j = 0; j < n; j++) 
			{
				int k = graph[i].get(j);
				
				h.add(height[k]);
			}
			
			Collections.sort(h);
			
			if(n <= 0) continue;
			else if(n == 1) maxLen[i] += h.get(0) + 1;
			else maxLen[i] += h.get(n-1) + h.get(n-2) + 2;
		}
		
		for(int i = 0; i < N; i++)
		{
			result = Math.max(result, maxLen[i]);			
		}
		
		return result;
	}
}