class Vertex:
	visited = False
	def __init__(self,element):
		self.element = element
		self.visited = False
		self.edges = []

def topologicalSortUtil(v,st,g):
	v.visited = True
	for j in v.edges:
		if (j.visited != True):
			topologicalSortUtil(j,st,g)
	st.append(v.element)

def topologicalSort(g):
	st = []
	sort = ""
	for i in range(len(g)):
		if g[i].visited == False:
			topologicalSortUtil(g[i],st,g)
	sort = st.pop()
	while len(st) != 0:
		sort = sort + st.pop()
	return sort

def answer(words):
	graph = []
	hashmap = {}
	leftWord = ""
	rightWord = ""
	val = 0
	position = 0

	if (len(words) == 1):
		return words[0][0]

	for i in range(len(words)-1):
		leftWord = words[i]
		rightWord = words[i+1]
		for j in range(min(len(leftWord),len(rightWord))):
			left = leftWord[j]
			right = rightWord[j]
			if left != right:
				v = Vertex(left)
				v2 = Vertex(right)
				if left not in hashmap and right not in hashmap:
					hashmap[left] = position
					hashmap[right] = position+1
					v.edges.append(v2)
					graph.insert(position,v)
					graph.insert(position+1,v2)
					position += 2
				elif right in hashmap:
					val = hashmap[right]
					l = graph[val]
					if left not in hashmap:
						hashmap[left] = position
						graph.insert(position,v)
						position += 1
					graph[hashmap[left]].edges.append(l)
				elif left in hashmap:
					val = hashmap[left]
					if right not in hashmap:
						hashmap[right] = position
						graph.insert(position,v2)
						position += 1
					graph[val].edges.append(v2)
				print("Adding",v.element,"to",v2.element)
				break

	return topologicalSort(graph)

words = ["bb","ab","ac","ad","aa"]
print(answer(words))