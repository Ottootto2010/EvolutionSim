/*
 * Simulation of the Bergmann rule
 * 
 * By Ferdinand Biere (c) 2016
 * */



import java.util.Random;
import java.lang.Comparable;

public class Creature implements Comparable<Creature>{
	// Minimum and Maximum Size of Creature (per dimension)
	static double MIN_SIZE = 0.0001; // m
	static double MAX_SIZE_X = 5; // m
	static double MAX_SIZE_Y = 5; // m
	static double MAX_SIZE_Z = 5; // m
	static int GENOME_LEN = 3;
	
	static double FOOD_AVAILABLE = 20000.0; // kcal
	static double TEMP_OF_CREATURE = 20.0; // �C
	static double TEMP_OF_ENVIRONMENT = 15.0; // �C
	static double RUNTIME = 10; //h
	static double WIND_SPEED = 10; // m/s (2 <= v <= 20)
	static double METABOLIC_EFFICIENCY = 0.25; // %
	static double METABOLIC_RATE_FACTOR = 5; // 1 <= p <= 20 (heavy work)
	
	
	static double FLESH_DENSITY = 1010; // kg/m^3
	static double HEAT_CAPACITY = 0.83; // kcal/�C kg
	static double BASE_METABOLIC_RATE = 70; // kcal/h
	static double SKIN_EMMISSIVITY = 0.98; // 0 to 1
	
	private static double BOLZMANN_CONST = 5.670367*Math.pow(10, -8); // W�m^-2�K^-4
	
	static double METABOLIC_RATE = BASE_METABOLIC_RATE * METABOLIC_RATE_FACTOR;
	double[] genome = new double[GENOME_LEN];
	
	private double x_len = genome[0];
	private double y_len = genome[1];
	private double z_len = genome[2];
	double heatloss;
	
	public double fittness = 0;
	
	public void getFittness() {
		double metabolic_loss = RUNTIME * METABOLIC_RATE;
		fittness = (FOOD_AVAILABLE * METABOLIC_EFFICIENCY) - (getTempLoss() + metabolic_loss);
		// fittness = FOOD_AVAILABLE - getTempLoss();
	}
	
	public double returnFittness() {
		getFittness();
		return fittness;
	}
	
	private double getTempLossByRadiation() {
		return /*RUNTIME * */SKIN_EMMISSIVITY * BOLZMANN_CONST * getArea() * (Math.pow(TEMP_OF_CREATURE, 4) - 
				Math.pow(TEMP_OF_ENVIRONMENT, 4));
	}
	
	private double getTempLossByConvection() {
		return /*RUNTIME * */(10.45 - WIND_SPEED + 10 * Math.sqrt(WIND_SPEED)) *
				getArea() * (TEMP_OF_CREATURE - TEMP_OF_ENVIRONMENT);
	}
	
	@SuppressWarnings("unused")
	private double toKelvin(double celcius) {
		return celcius + 273.15;
	}
	
	private double getMass() {
		return getVolume() * FLESH_DENSITY;
	}
	
	public double getTempLoss() {
		//return (toKelvin(TEMP_OF_CREATURE) - toKelvin(TEMP_OF_ENVIRONMENT)) * (1 / R_VAL_CREATURE)
		//		 * getArea();
		return (getTempLossByConvection() + getTempLossByRadiation())/RUNTIME;
	}
	
	public void printInfo() {
		System.out.println("Genome:");
		this.printGenome();
		System.out.println("Area:");
		System.out.println(getArea() + " m^2");
		System.out.println("Temperature Loss:");
		System.out.println(getTempLoss() + " kcal/h");
		System.out.println("Volume: ");
		System.out.println(getVolume() + " m^3");
		System.out.println("Mass: ");
		System.out.println(getMass() + " kg");
		System.out.println("Fittness: ");
		System.out.println(fittness);
	}
	
	public void printGenome() {
		for (int n = 0; n < GENOME_LEN; n++) {
			System.out.print(genome[n] + " ");
		}
		System.out.println();
	}
	
	public void update(double[] new_genome) {
		genome = new_genome;
		setSize(genome[0], genome[1], genome[2]);
	}
	
	public void randomlyGenerate(Random rand) {
		//generate initial genome
		genome[0] = rand.nextDouble() * MAX_SIZE_X + MIN_SIZE;
		genome[1] = rand.nextDouble() * MAX_SIZE_Y + MIN_SIZE;
		genome[2] = rand.nextDouble() * MAX_SIZE_Z + MIN_SIZE;
		setSize(genome[0], genome[1], genome[2]);
		if (!isSizeOk(x_len, y_len, z_len)) {
			randomlyGenerate(rand);
		}
	}
	
	public void generate(double[] genome) {
		this.genome = genome;
		setSize(this.genome[0], this.genome[1], this.genome[2]);
	}
	
	public void setSize(double x, double y, double z) {
		if (isSizeOk(x, y, z)) { //just change if changes are allowed
			this.x_len = x;
			this.y_len = y;
			this.z_len = z;
		}
		
	}
	
	public double getVolume() {
		return x_len * y_len * z_len;
	}
	
	public double getArea() {
		return 2.0 * (x_len * y_len + y_len * z_len + x_len * z_len);
	}
	
	private boolean isSizeOk(double x, double y, double z) {
		boolean min_ok = (x >= MIN_SIZE & y >= MIN_SIZE & z >= MIN_SIZE);
		boolean max_ok = (x <= MAX_SIZE_X & y <= MAX_SIZE_Y & z <= MAX_SIZE_Z);
		return min_ok & max_ok;
	}
	
	public void printFittness() {
		System.out.println(this.fittness);
	}

	@Override
	public int compareTo(Creature other_creature) throws ClassCastException {
		if (!(other_creature instanceof Creature)) {
			throw new ClassCastException("A Creature expected.");
		}
		double other_fittness = ((Creature) other_creature).fittness;
		// System.err.println("comparing with result: " + (int) -(Math.round(this.fittness - other_fittness)));
		return (int) -(Math.round(this.fittness - other_fittness));
	}

}
