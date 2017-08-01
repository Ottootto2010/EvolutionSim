# EvolutionSim
Simulated evolution to proove Bergmann's rule

Creatures are defined as cubes that get a certain amount of calories to live from for given amount of time.They radiate and convect energy over time and their fitness is given by the energy that is left after their life (can be negative).

The simulation uses fitness proportionate select to choose the best adapted creatures and using those chosen ones to mate and mutate.
I used an floating point array as the genome, so I can use the x, y and z sizes as the genome. The mating works, by chosing two random individuals from the selected group and letting one of them give 2 and the other one 1 value to the new 'child' cube. There also is a certain chance that the value is mutated, or multiplied by a random number.

To adapt the evolutionary aspect of my code for your project, you only have to change the getFitness method in Creatures, the evaluatePopulation method in Population as it uses the Creatures size which you might not plan on using, and of course the export to csv to use your headers and whatever.

If you find any bugs feel free to contact me.
