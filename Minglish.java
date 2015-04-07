import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.util.Stack;
import java.util.HashMap;
import java.util.List;

public class Minglish {
	public Minglish () {}
	
	public class Vertex {
		List<Vertex> edges;
		String element;
		boolean visited;

		public Vertex (String e) {
			element = e;
			edges = new ArrayList<Vertex>();
			visited = false;
		}
	}

	public void topologicalSortUtil (Vertex v, Stack<String> st, List<Vertex> g) {
		v.visited = true;
		Iterator it = v.edges.iterator();

		while (it.hasNext()) {
			Vertex n = (Vertex) it.next();
			if (!n.visited) topologicalSortUtil(n,st,g);
		}

		st.push(v.element);

	}
	public String topologicalSort (List<Vertex> g) {
		Stack<String> st = new Stack<String>();
		String sorted;

		for (int i = 0; i < g.size(); i++) {
			if (g.get(i).visited == false) topologicalSortUtil(g.get(i),st,g);
		}

		sorted = st.pop(); 
		while (!st.empty()) {
			sorted = sorted + st.pop(); 
		}
		return sorted;
	}
	public String answer (String words []) {
		List<Vertex> g = new ArrayList<Vertex>();
		HashMap<Character,Integer> map = new HashMap<Character,Integer>();
		String leftWord;
		String rightWord;
		int val;
		int position = 0;

		if (words.length == 1) return Character.toString(words[0].charAt(0));

		for (int i = 0; i < words.length - 1; i++) {
			leftWord = words[i];
			rightWord = words[i+1];

			for (int j = 0; j < Math.min(leftWord.length(),rightWord.length()); j++) {
				char left = leftWord.charAt(j);
				char right = rightWord.charAt(j);

				if (left != right) {
					Vertex v = new Vertex(Character.toString(left));
					Vertex v2 = new Vertex(Character.toString(right));

					if (!map.containsKey(left) && !map.containsKey(right)) {
						map.put(left,position);
						map.put(right,position+1);
						v.edges.add(v2);
						g.add(position,v);
						g.add(position+1,v2);
						position += 2;
					}
					else if (map.containsKey(right)) {
						val = map.get(right);
						Vertex l = g.get(val);
						if (!map.containsKey(left)) {
							map.put(left,position);
							g.add(position,v);
							position++;
						}
						g.get(map.get(left)).edges.add(l);

					}
					else if (map.containsKey(left)) {
						val = map.get(left);
						if (!map.containsKey(right)) {
							map.put(right,position);
							g.add(position,v2);
							position++;
						}
						g.get(val).edges.add(v2);
					}
					System.out.println("adding: " + v.element + " to " + v2.element);
					break;
				}
			}
		}
		return topologicalSort(g);

	}
	public static void main(String[] args) {
		Minglish min = new Minglish();
		String [] words = {"x","xx","xw","xwz","xwy","xwx"};
		String answer = min.answer(words);
		System.out.println(answer);
	}
}