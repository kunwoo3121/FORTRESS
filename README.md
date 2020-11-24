# FORTRESS

https://algospot.com/judge/problem/read/FORTRESS

# 구현 방법
```
입력되는 데이터를 트리의 형태로 변환한다. 

1) 반지름이 작을 수록 트리의 아래쪽에 위치할 것이기 때문에 반지름을 기준으로 오름차순 정렬을 한다.

2) 자식 노드가 되는 기준은 중심 점간의 길이보다 반지름 길이의 차이가 더 길 때이다.

ex) 21 15 20  가 입력으로 들어오면 
    15 15 10 
    13 12 5 
    12 12 3 
    19 19 2 
    30 24 5 
    32 10 7 
    32 9 4 

* 반지름을 기준으로 오름차순 정렬을 한다. 

=> 19 19 2  (1)        
   12 12 3  (2)         
   32 9 4   (3)        
   13 12 5  (4)
   30 24 5  (5)
   32 10 7  (6)
   15 15 10 (7)
   21 15 20 (8)
   
   부모노드     자식노드
     (8)    -   (5), (6), (7)
     (7)    -   (4), (1)
     (6)    -   (3)
     (4)    -   (2)
     
  와 같이 트리가 구성이 된다.
  
  트리는 ArrayList를 이용해 부모노드와 자식노트를 표현하여 구현하였다.

3) 트리를 완성하고 나면 한 노드에서 다른 노드로 갈 때 가장 긴 경로를 구하면 된다.
   가장 긴 경로는 잎 노드에서 잎 노드로 움직이는 경우이다.
   
4) 각 정점별로 그 정점을 root 노드로 하였을 때 최대 경로를 구하여 경로의 최대값을 구한다.
```

# 구현 코드
```java
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
```

  
