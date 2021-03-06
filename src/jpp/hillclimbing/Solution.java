package jpp.hillclimbing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Solution {

	Problem pr;
	private static Random ran = new Random();
	int[] sol;
	int[] solH;
	int[] sol2;

	public Solution(Problem problem) throws IllegalArgumentException {
		if (problem == null) {
			throw new IllegalArgumentException();
		}
		this.pr = problem;
		sol = new int[problem.getJobs()];

		for (int i = 0; i < sol.length; i++) {
			sol[i] = getRandom().nextInt(problem.getCPUs());

		}
		solH = sol.clone();
		sol2 = sol.clone();

	}

	public static void setRandom(Random r) {
		ran = r;
	}

	public static Random getRandom() {
		return ran;
	}

	public Problem getProblem() {
		return pr;
	}

	public int getCPU(int job) {
		if (job < 0 || job > pr.getJobs() - 1) {
			throw new IndexOutOfBoundsException("falsche Eingabe!!!");
		} else {
			return sol[job];

		}

	}

	public int getTotalRuntime() {

		int max = 0;
		int i = 0;
		max = getRuntime(i);
		for (i = 1; i < pr.getCPUs(); i++) {
			if (getRuntime(i) > max) {
				max = getRuntime(i);
			}
		}
		return max;

	}

	public int getRuntime(int cpu) {
		int h = 0;
		if (cpu < 0 || cpu >= pr.getCPUs()) {
			throw new IndexOutOfBoundsException("cpu ist nicht existiert!!");
		} else {
			for (int i = 0; i < pr.getJobs(); i++) {
				if (sol[i] == cpu) {
					h += pr.jobs[i];
				}
			}
		}
		return h;

	}

	public Iterator<Solution> getNeighbourhood() {
		List<Solution> solList = new ArrayList<Solution>();

		for (int i = 0; i < getProblem().getCPUs(); i++) {
			for (int j = 0; j < sol.length; j++) {
				if (i != sol[j]) {
					solH[j] = i;
					Solution so1 = new Solution(getProblem());
					so1.sol = solH.clone();

					if (!solList.contains(so1)) {
						solList.add(so1);
					}
					sol = sol2.clone();

				}

			}

		}
		Collections.shuffle(solList, getRandom());
		Iterator<Solution> it = solList.iterator();
		return it;

	}

	public String toString() {
		String s1 = "CPU,Process-IDs,Runtime\n";

		for (int i = 0; i < getProblem().getCPUs(); i++) {
			s1 = s1 + i + ",";
			for (int j = 0; j < sol.length; j++) {
				if (i == sol[j]) {
					s1 = s1 + j + " ";
				}
			}
			s1 = s1 + "," + getRuntime(i) + "\n";

		}

		return s1 + "Total Runtime," + getTotalRuntime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pr == null) ? 0 : pr.hashCode());
		result = prime * result + Arrays.hashCode(sol);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution other = (Solution) obj;
		if (pr == null) {
			if (other.pr != null)
				return false;
		} else if (!pr.equals(other.pr))
			return false;
		if (!Arrays.equals(sol, other.sol))
			return false;
		return true;
	}

}
