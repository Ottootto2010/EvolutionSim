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
		ArrayList<Creature> selected_creatures = proportionateSelect();
		ArrayList<Creature> evolved_creatures = 
				mateAndMutate(selected_creatures);
	}
	
	private ArrayList<Creature> mateAndMutate(
			ArrayList<Creature> selected_survivors) {
		
		ArrayList<Creature> new_generation = 
				new ArrayList<Creature>(POPULATION_SIZE);
		
		int missing_individuals = POPULATION_SIZE - selected_survivors.size();
		
		
		
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