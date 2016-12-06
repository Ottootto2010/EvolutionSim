import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

public class sim {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testPopulation();
	}
	
	@SuppressWarnings("unused")
	private static void testCreatures() {
		Creature test_creature = new Creature();
		Random rand = new Random();
		test_creature.randomlyGenerate(rand);
		test_creature.update(test_creature.genome);
		test_creature.printInfo();
	}
	
	private static void testPopulation() {
		Population test_population = new Population();
		Random rand = new Random();
		test_population.generateNewPopulation();
		
		test_population.runGeneration();
		
		for (int a = 0; a < 100; a ++) {
			test_population.runGeneration();
		}

		writeReportToCvs(test_population);
	}
	
	public static void writeAllCreaturesReportToCvs(Population population) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String outputfile = "D:\\evolutionsim\\evolutionsim_data_all_creatures" + dateFormat.format(date) + ".csv";
		System.out.println("Writing report to '" + outputfile + "'");
		
		try{
			PrintWriter writer = new PrintWriter(outputfile, "UTF-8");
			for (int generation = 0; generation < population.current_generation; generation ++) {
				for (int individual = 0; individual < population.population_history.get(generation).size(); individual++) {
					writer.print(population.population_history.get(generation).get(individual).returnFittness() + ", ");
				}
				writer.println();
			}
		    writer.close();
		} catch (IOException e) {
		   e.printStackTrace();
		   System.err.println("SOMETHING WENT TERRIBLY WRONG...(IOException)");
		}
	}
	
	public static void writeReportToCvs(Population population) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();
		String outputfile = "D:\\evolutionsim\\evolutionsim_data_" + dateFormat.format(date) + ".csv";
		System.out.println("Writing report to '" + outputfile + "'");
		
		ArrayList<Double> average = new ArrayList<Double>();
		ArrayList<Double> average_vol = new ArrayList<Double>();
		ArrayList<Double> average_tempLoss = new ArrayList<Double>();
		ArrayList<Double> average_area = new ArrayList<Double>();
		ArrayList<Double> max = new ArrayList<Double>();
		ArrayList<Double> min = new ArrayList<Double>();
		/*for (int i = 0; i < population.statistic_max.size(); i ++) {
			average.add(Arrays.asList(population.population_history.get(i)).
					stream().mapToDouble(Creature -> Creature.returnFittness()).average().getAsDouble());
			average_vol.add(Arrays.asList(population.population_history.get(i)).
					stream().mapToDouble(Creature -> Creature.getVolume()).average().getAsDouble());
			average_tempLoss.add(Arrays.asList(population.population_history.get(i)).
					stream().mapToDouble(Creature -> Creature.getTempLoss()).average().getAsDouble());
			average_area.add(Arrays.asList(population.population_history.get(i)).
					stream().mapToDouble(Creature -> Creature.getArea()).average().getAsDouble());
			//max.add(Collections.max(Arrays.asList(population.population_history.get(i))).returnFittness());
			//min.add(Collections.min(Arrays.asList(population.population_history.get(i))).returnFittness());
		}*/
		
		try{
		    PrintWriter writer = new PrintWriter(outputfile, "UTF-8");
		    writer.println("Min,Max,Mean,Average,Mean Volume,Average Volume,Average X,Average Y,Average Z");
		    for (int row = 0; row < population.statistic_max.size(); row++) {
		    	writer.print(population.statistic_min.get(row) + ",");
		    	writer.print(population.statistic_max.get(row) + ",");
		    	writer.print(population.statistic_mean.get(row) + ",");
		    	writer.print(population.statistic_average.get(row) + ",");
		    	writer.print(population.statistic_mean_creature.get(row).getVolume() + ",");
		    	writer.print(population.statistic_average_volume.get(row) + ",");
		    	// writer.println(population.statistic_mean_creature.get(row).getTempLoss() + ",");
		    	writer.print(population.statistic_average_x.get(row) + ",");
		    	writer.print(population.statistic_average_y.get(row) + ",");
		    	writer.println(population.statistic_average_z.get(row) + ",");
		    	//writer.print(average_tempLoss.get(row) + ",");
		    	//writer.print(average_area.get(row) + ",");
		    }
		    writer.close();
		} catch (IOException e) {
		   e.printStackTrace();
		   System.err.println("SOMETHING WENT TERRIBLY WRONG...(IOException)");
		}
	}
}
