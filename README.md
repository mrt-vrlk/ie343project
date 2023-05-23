# Spring-23-Term-Project

1-	According to given dataset, if we increase the cooling rate average execution time decreases because the temperature drops faster compared to a smaller cooling rate. Therefore we can reach stopping temperature with less number of iteration. Since each iteration effects the execution time if we increase the cooling rate the algorithm reachs the stoping  condition with less iterations.

2-	As the difference between starting temperature and stoping temperature increases, quality of solution and execution time increase because as the difference gets bigger we have a wider range of solutions to check. Since the range is wider required number of iterations increase. Therefore, average execution time increases. Quality of solution increases as well because the algorithm needs to check a bigger feasible region more find an optimal solution. Although the size of the feasible region may proivde a better solution, this doesn’t necesserily mean that the solution will get better.

Code Description:

After learning that we needed to create two experiments, we decided to create simulated annealing as a seperate method which took two variables. These variable were chosen as regarding the experiments we are going to conduct. They were finally chosen as the double array of stopping temperatures and double array of cooling rates.

In each iteration of the code:
1-To record the time elapsed to execute we used the default System.nanoTime(). 
2-Recorded each iteration's best value, in arrays.

In simulatedAnnealing method, although in the first place we decided to use a set number of iterations, we decided to change our way of approach in regards to the questions, as the experiment would focus on vast range of variables, which was out of our scope. Thus we decided to use a stopping temperature as means to break the loop.

The simulatedAnnealing method works with the principles of Metropolis criterion. In each iteration a new neighbour is created and valued. The Metropolis criterion then evaluates if best solution should be updated or not.

Murat Varlık & Oğuzalp Erkovan

