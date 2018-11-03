import numpy as np
import random
#import sys
up = 0
down = 1
left = 2
right = 3
# The function gridWorld returns the reward associated with the movements
def gridWorld(G, B):
    # Grid world strucure:
    #
    #  ---------------------
    #  |  0 |  1 |  2 |  3 |
    #  ---------------------
    #  |  4 |  5 |  6 |  7 |
    #  ---------------------
    #  |  8 |  9 | 10 | 11 |
    #  ---------------------
    #  | 12 | 13 | 14 | 15 |
    #  ---------------------
    
    
    reward = -np.ones(17)
    #G represents GOAL state
    #B represents bad state
    #reward specifications
    reward[G] = 100  # goal state
    reward[B] = - 100 # bad state
    reward[16] = 0    # end state
    
    return reward




#defining the structure of the gridworld and the movements of the agent
# W represents wall state
def grid_state_fixed(initial_state, direction,W):
    new_state = initial_state
    if direction == up and initial_state - 4 >= 0:
        new_state = initial_state - 4
    elif direction == down and initial_state + 4 < 16:
        new_state = initial_state + 4
    elif direction == left and initial_state not in [0, 4, 8, 12]:
        new_state = initial_state - 1
    elif direction == right and initial_state not in [3, 7, 11]:
        new_state = initial_state + 1
    while(new_state==W):
        return initial_state
    return new_state

#methid to get new state
def get_new_state(initial_state, direction, eps, G, W):
    if initial_state == G:
        return G+1
    rand = random.random()
    if direction == left or direction == right:
        if rand < eps:
            return grid_state_fixed(initial_state, up, W)
        if rand < 2*eps:
            return grid_state_fixed(initial_state, down, W)
        return grid_state_fixed(initial_state, direction, W)
    elif direction == up or direction == down:
        if rand < eps:
            return grid_state_fixed(initial_state, left, W)
        if rand < 2*eps:
            return grid_state_fixed(initial_state, right, W)
        return grid_state_fixed(initial_state, direction, W)
    else:
        print ("Wrong direction")

#method to pick action
def pick_action(qvals, epsilon):
    if random.random() >= epsilon:
        return np.argmax(qvals)
    else:
        return random.randint(0, 3)

#Method to pick a feasible route for the agent using Q-learning
def Q_learning(epsilon = 0.05):
    qvals = np.zeros((17, 4))
    count = np.zeros((17, 4))
    discount = 0.90
    eps = 0.1
    W = np.int(input("Enter Wall value:"))
    G=np.int(input("Enter value for Goal between 13 and 15: "))
    B=np.int(input("Enter value for Badbox between 6 and 12: "))
    Q= np.int(input("Enter the value of Q we want to obtain:"))
    
    
    reward = gridWorld(G, B)
    actions = []
    states = []
    iterations = 10000 #maximum iterations is 10000
    for i in range(iterations):
        actions = []
        states = []
        cur_s = 0
        while cur_s != G+1:
            states += [cur_s]
            action = pick_action(qvals[cur_s], epsilon)
            count[cur_s][action] += 1
            alpha = 1.00/count[cur_s][action]
            Qsa = qvals[cur_s][action]
            actions += [action]
            new_state = get_new_state(cur_s, action, eps, G, W)
            
            qvals[cur_s][action] = Qsa + alpha * (reward[cur_s] + discount*np.max(qvals[new_state]) - Qsa) #Qlearning formula
            cur_s = new_state

    for n,i in enumerate(actions):
        if i==0:
            actions[n]='up'
        elif i==1:
            actions[n]='down'
        elif i==2:
                actions[n]='left'
        else:
            actions[n]='right'
    #print the states, actions and Qvalue tables
    print ("States through which the agent transition")
    print (states)
    print ("--------------------------------")
    print ("Actions taken by the agent")
    print(actions[:-1])
    print ("--------------------------------")
    print ("Q Value Table ")
    print("   up           down           left          right")
    print (qvals[Q])
    #print (qvals)
# currently epsilon set at 0.1
Q_learning(0.1)


