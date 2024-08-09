Question 1:

We have a large and complex workflow, made of tasks. And
have to decide who does what, when, so it all gets done on time.
All tasks have dependency on other tasks to complete
Each task(t) has a
D[t] = duration of task
EFT[t] = the earliest finish time for a task
LFT[t] = the latest finish time for a task
EST[t] = the earliest start time for a task
LST[t] = the last start time for a task
Assume
that “clock” starts at 0 (project starting time).
We are given the starting task T_START where the EST[t] = 0 and LST[t] = 0
You have to write an Java/Python/JS/Typescript console application to answer the following questions
Earliest time all the tasks will be completed?
Latest time all tasks will be completed?


Both Alice & Bob have friends. Create a Java/Python/JS/Typescript console application to find all the friends of Alice and all the friends of Bob & common friends of Alice and Bob.
Your algorithm should be able to do the following:
Take any 2 friends and find the common friends between the 2 friends
Take any 2 friends find the nth connection - for example: connection(Alice, Janice) => 2
Alice has friend Bob and Bob has friend Janice, if the input given is Alice and Janice the output should be 2, meaning 2nd connection, that means Janice is the second connection of Alice and Bob being the 1st connection of Alice.
Likewise if input given is Alice and Bob, the output should be 1, for 1st connection
If there is no connection at all, it should return -1


