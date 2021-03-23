package week9;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Stack;

public class GraphInList<T> {
	
	private class GraphNode {
		T data;
		GraphNode link;
		
		public GraphNode(T input) {
			data = input;
			link = null;
		}
	} 
	
	ArrayList<GraphNode> adjacentList; // �� ���� ����� ��
	int[] visited; // BFS, DFS���� ��� 
	
	public void createGraph() {
		adjacentList = new ArrayList<GraphNode>(); // ��Ĺ���̶�� ���⼭ �ʱ�ȭ ������� �� 
	}

	public boolean insertVertex(T v) {
		int index = indexOf(v);
		if (index>=0) // index�� 0���� ũ�ٴ� �� �̹� �����Ѵٴ� �� 
			return false; // Vertex already exist
		adjacentList.add(new GraphNode(v));
		return true;
	}

	public boolean insertEdge(T u, T v) { //  edge from u to v
		if (indexOf(u)<0 || indexOf(v)<0) // ������ �߰��Ҷ� vertax�� �ݵ�� �־�� �ϹǷ�, �������� ������ false�� ���� 
			return false; 
		int index = indexOf(u);
		GraphNode newNode = new GraphNode(v);  
		newNode.link = adjacentList.get(index).link; // insert at the first position ù��°�� �ٷ� �ڿ� ���̴� ���̴�. ����->�λ��̶�� �����̰���Ű�� �ִ� ���� �λ��� ����Ű������
		adjacentList.get(index).link = newNode; // ù��° ���� �� 
		// if undirected case, insertEdge(v, u) should be done!
		return true;
	}
	
	private int indexOf(T u) { // �̹� ���� �ִ� �������� Ȯ���Ѵ�. 
		for (int i =0;i<adjacentList.size(); i++ ) {
			if ( adjacentList.get(i).data.equals(u))
				return i;
		}
		return -1;
	}

	public HashSet<T> adjacent(T v){
		HashSet<T> result= new HashSet<T>();
		System.out.println(">>> Adj. test :  "+ v) ;

		int index = indexOf(v);
		if (index==-1) {
			System.out.println(v +"  not found");
			return result; // Vertex not found;
		}
		GraphNode  p = adjacentList.get(index);
		System.out.println(">>> Adj. test : p "+ p.data) ;

		while (p.link!=null) {
			result.add(p.link.data);
			p=p.link;
		}
		return result;
	}

	public boolean deleteVertex(T v) {
		int index = indexOf(v);
		if (index<0)
			return false;  // Vertex is not found
		GraphNode p = adjacentList.get(index);
		
		if (p.link!=null) {
			GraphNode q = p.link;
			while (q!=null) {
				deleteEdge(q.data, v);
				q=q.link;
			}	
		}
		adjacentList.remove(index);
		return true;
	}

	public boolean deleteEdge(T u, T v) { //  edge from u to v
		int index = indexOf(u);
		if (index<0)
			return false;  // Tail-Vertex is not found
		GraphNode p = adjacentList.get(index);
		GraphNode q = p.link;
		while (!(q.data.equals(v)) && q.link!=null) { // ��ũ���󰡼� ����� �� 
			p= p.link; q=q.link;
		}
		if (q.data.equals(v)) {
			p.link=q.link;
			// if undirected case, deleteEdge(v, u) should be done!
			return true;
		}
		return false;
	}
	
	public void showGraph() {
		System.out.println("[ Graph ]");
		
		for (int i=0; i<adjacentList.size(); i++) {
		    System.out.print(adjacentList.get(i).data + " ");
			GraphNode p = adjacentList.get(i).link;
			while(p!=null) {
			    System.out.print(" => "+p.data);
			    p=p.link;
			}
			System.out.println();
		}
	}

	public void BFS(T v){	
		int index = indexOf(v);

		if (index>=0) {
			Deque<Integer> que = new ArrayDeque<Integer>();
			visited = new int [adjacentList.size()];
			Arrays.fill(visited, 0); // �湮���� ���� ���� 0���� ǥ�� 
			que.add(index);
			BFSRecursion(que);
		}
		else
			System.out.println("Starting vertex not found");

	}

	private void BFSRecursion(Deque<Integer> que) {
	
		while(que.peek()!=null) { // peek�޼���� ���� ���� �� �Ʒ����� poll�� ������ �޼��� 
			int index = que.poll();
			System.out.println(">>> Polled out from Queue :"+index +"  "+adjacentList.get(index).data+" visited :"+visited[index]);

			if (visited[index]==1) { // �̹̹湮�ߴ����� ���� 
				return;
			}
			else {
				System.out.println(adjacentList.get(index).data);
				visited[index]=1;
				GraphNode temp= adjacentList.get(index).link;
		
				while(temp!=null) {
					que.add(indexOf(temp.data)); // que�� �־���
					System.out.println(">>> Added into Queue :"+indexOf(temp.data)+"  "+temp.data);
					temp=temp.link; // �ϳ��� ��忡 ������ ����� ���� ã�Ƴ����� ���ؼ� 
				}
				
				BFSRecursion(que);
			}
		}
		return;
	}
	
	public void DFS(T v){		
		int index = indexOf(v);
		if (index>=0) {
			Stack<Integer> st = new Stack<Integer>(); 
			visited = new int [adjacentList.size()];
			Arrays.fill(visited, 0);
			st.push(index);
			DFSRecursion(st);
		}
		else
			System.out.println("Starting vertex not found");

	}

	private void DFSRecursion(Stack<Integer> st) {

		if (st.size()>0) {
			int index=st.pop();
			System.out.println(">>> Popped out from Stack :"+index +"  "+adjacentList.get(index).data+" visited :"+visited[index]);

			if (visited[index]>0) {
				return;
			}
			visited[index]= 1;  // �湮 ����� ���� 
			
			GraphNode temp= adjacentList.get(index).link;
			while(temp!=null) {
					st.push(indexOf(temp.data));
					System.out.println(">>> Pushed into Stack :"+indexOf(temp.data)+"  "+temp.data);
					DFSRecursion(st); // BFS�� DFS�� �ٸ��� ���� ���ʿ� �ִ�. 
					temp=temp.link;
			}
			System.out.println(adjacentList.get(index).data); // Recursion�� ������ �������� ������ 
		}
	}


	public static void main(String[] args) {
		String [] vertex = {"seoul", "daejeon", "daegu", "busan",
							"kwangju", "incheon", "ulsan", "jeju"};
		GraphInList<String> myG = new GraphInList<>(); // Linked List ���
		
		myG.createGraph();
		for (int i=0; i<vertex.length; i++)
			myG.insertVertex(vertex[i]); // seoul daejeon�� ���ʷ� ����. 
		
		myG.insertEdge(vertex[0], vertex[3]);
		myG.insertEdge(vertex[0], vertex[7]);  // �� ���α׷������� seoul����� ��ũ�� �λ��� �����ϰ� �� �� �߰��� ������ ������
		                                       // seoul�� �λ���̿� jeju�� �����Ѵ�. 
		myG.insertEdge(vertex[3], vertex[1]);
		myG.insertEdge(vertex[3], vertex[7]);
		myG.insertEdge(vertex[1], vertex[4]);
		myG.insertEdge(vertex[1], vertex[5]);
		myG.insertEdge(vertex[5], vertex[2]);
		myG.insertEdge(vertex[5], vertex[6]);
		myG.insertEdge(vertex[5], vertex[3]);
		myG.insertEdge(vertex[5], vertex[7]);
		myG.insertEdge(vertex[6], vertex[2]);
		myG.insertEdge(vertex[6], vertex[0]);
		
		
		
//		myG.insertEdge(vertex[7], vertex[0]);
		
//		myG.insertEdge(vertex[0], vertex[1]);
//		myG.insertEdge(vertex[0], vertex[6]);
//		myG.insertEdge(vertex[0], vertex[7]);
//		myG.insertEdge(vertex[1], vertex[2]);
//		myG.insertEdge(vertex[1], vertex[5]);
//		myG.insertEdge(vertex[2], vertex[3]);
//		myG.insertEdge(vertex[2], vertex[4]);

		System.out.println("*** Graph created ***");
		myG.showGraph();
		
		System.out.println("--- Adjacent Vertices to : "+vertex[5]);
		HashSet<String> adj = myG.adjacent(vertex[5]);
		System.out.println(adj);
		System.out.println("--- Adjacent Vertices to : "+vertex[0]);
		adj = myG.adjacent(vertex[0]);
		System.out.println(adj);

		
		int i=0;
		System.out.println("--- BFS & DFS Test --- Start from : "+ vertex[i]);

		System.out.println("\n*** BFS *** \n");
		myG.BFS(vertex[i]);

		System.out.println("\n*** DFS *** \n");
		myG.DFS(vertex[i]);

		}

}