import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Population{
	int POPULATION_SIZE = 100;

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
	
	private ArrayList<Creature> order(ArrayList<Creature> array){
		for (Creature c: array) c.getFittness();
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
		printPopulation();
		ArrayList<Creature> selected_creatures = proportionateSelect();
		ArrayList<Creature> evolved_creatures = 
				mateAndMutate(selected_creatures);
		creatures = evolved_creatures;
		printPopulation();
	}
	
	private ArrayList<Creature> mateAndMutate(
			ArrayList<Creature> selected_survivors) {
		
		ArrayList<Creature> new_generation = 
				new ArrayList<Creature>(POPULATION_SIZE);
		
		int GENE_SPLIT = (int) Creature.GENOME_LEN/2;
		double MUTATION_RATE = 0.1; //%
		
		new_generation.addAll(selected_survivors);
		
		for (int n = selected_survivors.size() - 1;
				n < new_generation.size(); n++) {
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
		double SELECTION_RATE = 0.5; //%
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