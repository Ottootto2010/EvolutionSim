import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class Population{
	int POPULATION_SIZE = 100;
	int current_generation = 0;
	
	ArrayList<Double> statistic_max = new ArrayList<Double>();
	ArrayList<Double> statistic_min = new ArrayList<Double>();
	ArrayList<Double> statistic_mean = new ArrayList<Double>();
	ArrayList<Double> statistic_average = new ArrayList<Double>();
	ArrayList<Double> statistic_average_volume = new ArrayList<Double>();
	ArrayList<Double> statistic_average_x = new ArrayList<Double>();
	ArrayList<Double> statistic_average_y = new ArrayList<Double>();
	ArrayList<Double> statistic_average_z = new ArrayList<Double>();
	ArrayList<Creature> statistic_mean_creature = new ArrayList<Creature>();

	ArrayList<Creature> creatures = new ArrayList<Creature>();
	ArrayList<ArrayList<Creature>> population_history =
			new ArrayList<ArrayList<Creature>>();
	
	public void generateNewPopulation() {
		Random rand = new Random();
		for (int i = 0; i < POPULATION_SIZE; i++){
			creatures.add(new Creature());
			creatures.get(i).randomlyGenerate(rand);
			}
		}
	
	public void evaluateFitness(ArrayList<Creature> array) {
		for (Creature c: array) c.getFittness();
	}
	
	private ArrayList<Creature> order(ArrayList<Creature> array){
		evaluateFitness(array);
		Collections.sort(array, new Comparator<Creature>() {
			public int compare(Creature c1, Creature c2) {
				if (c1.returnFittness() == c2.returnFittness()) {
					return 0;
				}
				else {
					return c1.returnFittness() 
							> c2.returnFittness() ? -1 : 1;
				}
			}
		});
		return array;
	}
	
	public void runGeneration() {
		System.out.println("Generation " + current_generation);
		evaluatePopulation();
		System.out.println("Starting Selection...");
		ArrayList<Creature> selected_creatures = proportionateSelect();
		System.out.println("Done!");
		System.out.println("Starting Crossover and Mutation...");
		ArrayList<Creature> evolved_creatures = 
				mateAndMutate(selected_creatures);
		creatures = evolved_creatures;
		System.out.println("Done!");
		System.out.println(Collections.max(creatures));
		current_generation ++;
	}
	
	private void evaluatePopulation() {
		evaluateFitness(creatures);
		ArrayList<Creature> tmp = order(creatures);
		
		population_history.add(tmp);
		double max = creatures.get(0).returnFittness();
		statistic_max.add(max);
		double min = creatures.get(creatures.size() - 1).returnFittness();
		statistic_min.add(min);
		statistic_mean.add(creatures.get(creatures.size()/2).returnFittness());
		statistic_mean_creature.add(creatures.get(creatures.size()/2));
		
		double sum_of_fitness = 0;
		for (int i = 0; i < tmp.size() - 1; i ++) {
			sum_of_fitness += tmp.get(i).returnFittness();
		}
		statistic_average.add(sum_of_fitness/(tmp.size() - 1));
		
		double sum_of_volume = 0;
		for (int i = 0; i < tmp.size() - 1; i ++) {
			sum_of_volume += tmp.get(i).getVolume();
		}
		statistic_average_volume.add(sum_of_volume/(tmp.size() - 1));
		
		double sum_of_x = 0;
		for (int i = 0; i < tmp.size() - 1; i ++) {
			sum_of_x += tmp.get(i).genome[0];
		}
		statistic_average_x.add(sum_of_x/(tmp.size() - 1));
		
		double sum_of_y = 0;
		for (int i = 0; i < tmp.size() - 1; i ++) {
			sum_of_y += tmp.get(i).genome[1];
		}
		statistic_average_y.add(sum_of_y/(tmp.size() - 1));
		
		double sum_of_z = 0;
		for (int i = 0; i < tmp.size() - 1; i ++) {
			sum_of_z += tmp.get(i).genome[2];
		}
		statistic_average_z.add(sum_of_z/(tmp.size() - 1));
	}
	
	private ArrayList<Creature> mateAndMutate(
			ArrayList<Creature> selected_survivors) {
		
		ArrayList<Creature> new_generation = 
				new ArrayList<Creature>(POPULATION_SIZE);
		
		int GENE_SPLIT = (int) Creature.GENOME_LEN/2;
		double MUTATION_RATE = 0.5; //%
		
		new_generation.addAll(selected_survivors);
		
		for (int n = selected_survivors.size() - 1;
				n < POPULATION_SIZE; n++) {
			System.out.println("Mating Round " + n);
			Random rand = new Random();
			boolean has_mated = false;
			double[] new_genome = new double[Creature.GENOME_LEN];
			
			while (!has_mated) {
				int c1 = rand.nextInt(selected_survivors.size());
				int c2 = rand.nextInt(selected_survivors.size());
				boolean mutation =
						(rand.nextDouble() < MUTATION_RATE) ? true:false;

				if (c1 != c2) {
					for (int gen = 0; gen < Creature.GENOME_LEN; gen++) {
						System.out.println("Gen " + gen  + "/" + (Creature.GENOME_LEN - 1));
						if (!mutation && gen < GENE_SPLIT) {
							new_genome[gen] = 
									selected_survivors.get(c1).genome[gen];
						}
						else if (!mutation) {
							new_genome[gen] = 
									selected_survivors.get(c2).genome[gen];
						}
						else {
							if (rand.nextBoolean()) {
								new_genome[gen] = selected_survivors.
										get(c1).genome[gen] *
										(rand.nextDouble()-0.5);
							}
							else {
								new_genome[gen] = selected_survivors.
										get(c2).genome[gen] *
										(rand.nextDouble()-0.5);
							}
						}
					}
					has_mated = true;
				}
				new_generation.add(new Creature());
				new_generation.get(n).generate(new_genome);
			}
		}
		
		return new_generation;
	}
	
	private ArrayList<Creature> proportionateSelect() {
		double SELECTION_RATE = 0.333; //%
		ArrayList<Creature> tmp = new ArrayList<Creature>();
		creatures = order(creatures);
		
		for (int i = 0; i < creatures.size() * SELECTION_RATE; i++) {
			tmp.add(creatures.get(i));
		}
		return tmp;
	}
	
	private void printPopulation() {
		for (int i = 0; i < POPULATION_SIZE; i++) {
			System.out.println(creatures.get(i).returnFittness());
		}
	}
}