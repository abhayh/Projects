For this assignment, instead of using the rigid state spacegraph given by professor, I decided to create a statespace graph with the specified input as root node using the get_states method. (I discussed this with professor and he said I can do this) 
I then implemented the 4 search algorithms.

As I wasn't sure how to handle strings in python, have used lists to handle input and output.  '-' is used to show when a pancake is flipped. For eg of 2 is flipped it becomes -2 and when flipped again it becomes 2.

Contents:
Pancake1.py
abhay.config (We have a config file with the input and goaltest called abhay.config)
readme.md

To run: 

python2 pancake1.py abhay.config [search-algo]
python2 pancake1.py abhay.config uniformcost
python2 pancake1.py abhay.config greedy
python2 pancake1.py abhay.config astar
python2 pancake1.py abhay.config dfs


Since I created the state space structure from scratch I did not know how to constraint the dfs algorthms and hence it goes into an infinite loop as expected. The algorithm however and its logic is correct in my belief.

The other algorithms work as expected.

