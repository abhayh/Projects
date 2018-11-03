import re
import sys
import heapq

#Hash function for Uniform cost, Greedy and Astar
def hash_func(item):
	return str(hash(tuple(item)))


def dfs(cur_node):
	fringe_list = [(cur_node.init_state,[cur_node.init_state])]
	num_of_nodes = 1
	max_fringe_len = 1
	while fringe_list:
        #LIFO push-pop stack for handling the dfs fringe
		if max_fringe_len < len(fringe_list):
			max_fringe_len = len(fringe_list)
		(node,path) = fringe_list.pop()
		if cur_node.goal_test(node):
			return path
		nodelist = cur_node.get_states(node)
		num_of_nodes+=len(nodelist)
        #iterate over the left most solution
		for (nextnode,cost) in nodelist:
			if cur_node.goal_test(nextnode):
				return path
			print [x[0] for x in fringe_list]
			print nextnode
			if nextnode not in [x[0] for x in fringe_list] and nextnode != cur_node.init_state:
				fringe_list.append((nextnode, path + [nextnode]))


def greedySearch(cur_node):
	fringe_list = [(cur_node.get_heuristic(cur_node.init_state),cur_node.init_state,[cur_node.init_state])]
	visited_list = {}
	visited_list[hash_func(cur_node.init_state)] = 1
	num_of_nodes = 1
	max_fringe_len = 1
	while fringe_list:
		if max_fringe_len < len(fringe_list):
			max_fringe_len = len(fringe_list)
		(cost,node,path) = heapq.heappop(fringe_list)
		if cur_node.goal_test(node):
			return path
		nodelist = cur_node.get_states(node)
		num_of_nodes+=len(nodelist)
    #Greedy search checks only for heuristic cost(h(n))
		for (nextnode,cost) in nodelist:
			hash_val = hash_func(nextnode)
			if hash_val not in visited_list:
				heapq.heappush(fringe_list,(cur_node.get_heuristic(nextnode),nextnode, path + [nextnode]))
				visited_list[hash_val] = 1


def uniformcost(cur_node):
    fringe_list = [(0,cur_node.init_state,[cur_node.init_state])]
    visited_list = {}
    visited_list[hash_func(cur_node.init_state)] = 1
    num_of_nodes = 1
    max_fringe_len = 1
    while fringe_list:
        if max_fringe_len < len(fringe_list):
            max_fringe_len = len(fringe_list)
        (cost,node,path) = heapq.heappop(fringe_list)
        if cur_node.goal_test(node):
            return path
        
        hash_val = hash_func(node)
        if hash_val in visited_list and visited_list[hash_val] < cost:
            continue
        nodelist = cur_node.get_states(node)
        num_of_nodes+=len(nodelist)
        
        for (nextnode,cost) in nodelist:
            if cur_node.goal_test(nextnode):
                nextnode_cost = 0
            else:
                nextnode_cost = 1
            #Uniform cost search checks only for regular cost(g(n))
            if hash_func(nextnode) not in visited_list:
                heapq.heappush(fringe_list,(cost+nextnode_cost,nextnode, path + [nextnode]))
        visited_list[hash_val] = cost


def astar(cur_node):
	eval_func = cur_node.get_heuristic(cur_node.init_state)
	fringe_list = [(eval_func,0,cur_node.init_state,[cur_node.init_state])]
	visited_list = {}
	visited_list[hash_func(cur_node.init_state)] = 1
	num_of_nodes = 1
	max_fringe_len = 1
	while fringe_list:
		if max_fringe_len < len(fringe_list):
			max_fringe_len = len(fringe_list)
		(eval_func,init_cost,node,path) = heapq.heappop(fringe_list)
		if cur_node.goal_test(node):
			return path
		nodelist = cur_node.get_states(node)
		num_of_nodes+=len(nodelist)
#Astar search checks for heuristic cost and regular cost(h(n)+g(n))
		for (nextnode,cost) in nodelist:
			hash_val = hash_func(nextnode)
			if hash_val not in visited_list:
				nextnode_cost = init_cost+cost
				nextnode_eval_func = cur_node.get_heuristic(nextnode) + nextnode_cost
				heapq.heappush(fringe_list,(nextnode_eval_func,nextnode_cost,nextnode, path + [nextnode]))
				visited_list[hash_val] = 1
	
class pancake_problem():
	def __init__(self,init_state,func_name,maxnum):
		self.init_state = init_state
		self.goal_state = range(1,maxnum)
		self.maxnum = maxnum
		self.func_name = func_name


	def get_states(self,cur_state):
        #function to get the statespace graph based on the user input
		new_states = [(list(cur_state),1) for i in range(1,self.maxnum)]
		new_states[0][0][0] = -new_states[0][0][0]
		for i in range(2,self.maxnum):
			for j in range(0,i/2):
				temp = -new_states[i-1][0][i-j-1]
				new_states[i-1][0][i-j-1] = -new_states[i-1][0][j]
				new_states[i-1][0][j] = temp
		return new_states

	def goal_test(self,cur_state):
    #check if current state is goal test
            return cur_state == self.goal_state

	def get_heuristic(self,cur_state):
        #heuristic function to find the largest pancake out of position
		goal_state = self.goal_state
		if self.func_name == "misplaced_pancake":
			count=0
			for i in range(1,self.maxnum):
				if abs(cur_state[i-1])!=i:
					count+=1
			return count
		
def pancake(filename,search_algorithm,heuristic_func="misplaced_pancake"):
	with open(filename) as f:
		lines = f.readlines()
		lines = [line.strip('\r\n') for line in lines]
		path = []
		cur_node = None
		
		
		if lines[0] == "pancakes":
			init_state = re.findall(r'\((.+)\)',lines[1])[0].split(',')
			init_state = [int(val.strip(' ')) for val in init_state]
			maxnum = 0
			for i in init_state:
				if abs(i)>maxnum:
					maxnum = abs(i)

			cur_node = pancake_problem(init_state,heuristic_func,maxnum+1)
			# print cur_node.goal_state
		
		
		if search_algorithm == "dfs":
			path = dfs(cur_node)
		elif search_algorithm == "uniformcost":
			path = uniformcost(cur_node)
		elif search_algorithm == "greedy":
			path = greedySearch(cur_node)
		elif search_algorithm == "astar":
			path = astar(cur_node)


		if path:
			# print "Path cost:"+str(cost)
			for state in path:
				    print state
		else:
			print "No Solution\n"

        print "Length of path:"+str(len(path))
if __name__ == '__main__':
	if len(sys.argv) < 3:
		print "Insufficient arguments.  "
	if len(sys.argv) == 3:
		pancake(sys.argv[1],sys.argv[2])


	
