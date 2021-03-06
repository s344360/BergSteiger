package jpp.hillclimbing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class HillClimber {

	int i = 0;
	List<Integer> jobs = new ArrayList<Integer>();
	int[] resultJobs;

	public Problem read(InputStream in) throws IOException {
		if (in.available() == 0) { // erhaetlich in in.
			throw new IOException("invalid value!!");
		}
		Scanner sc = new Scanner(in);
		String s = sc.nextLine();

		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new IOException("invalid value!!");

		}
		int cpu = Integer.parseInt(s);

		if (!s.matches("[0-9]*")) {
			throw new IOException("invalid value!");

		}
		if (in.equals(null)) {
			throw new IOException("invalid value!");
		}
		if (cpu < 0 || cpu < 2) {
			throw new IOException("invalid value!!");
		}

		while (sc.hasNext()) {
			String s1 = sc.nextLine();
			try {
				Integer.parseInt(s1);
			} catch (NumberFormatException e) {
				throw new IOException("invalid value!!");

			}
			int runtime = Integer.parseInt(s1);

			if (!s1.matches("[0-9]*")) {
				throw new IOException("invalid value!");

			}

			if (runtime < 0 || runtime < 2) {
				throw new IOException("invalid value!!");
			}

			jobs.add(runtime);

		}
		if (jobs.size() < 2) {
			throw new IOException("invalid value!!");
		}
		resultJobs = gibtArray(jobs);
		Problem problem = new Problem(cpu, resultJobs);
		return problem;

	}

	public int[] gibtArray(List<Integer> jobs) {
		int[] result = new int[jobs.size()];
		for (int i = 0; i < jobs.size(); i++) {
			result[i] = jobs.get(i);
		}
		return result;

	}

	public Solution getSuccessor(Solution solution) {
		Solution betterSolution;
		Solution bestSolution = null;
		Iterator<Solution> it = solution.getNeighbourhood();
		while (it.hasNext()) {
			betterSolution = it.next();
			if (solution.getTotalRuntime() >= betterSolution.getTotalRuntime()) {
				bestSolution = betterSolution;
				return bestSolution;
			}

		}

		return null;

	}

	public Solution optimize(Solution start, int maxPlateau) {

		Solution betterNeighbour = start;
		int counter = 0;

		while (counter < maxPlateau) {
			betterNeighbour = getSuccessor(start);
			if (betterNeighbour == null) {
				return start;
			}
			if (betterNeighbour.getTotalRuntime() == start.getTotalRuntime()) {
				counter++;
				start = betterNeighbour;

			}

			if (betterNeighbour.getTotalRuntime() < start.getTotalRuntime()) {
				start = betterNeighbour;
				counter = 0;
			}

		}

		return start;

	}

}
