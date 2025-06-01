import java.util.Arrays;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial {
    double[] coefficients;
    int[] exponents;
    
    public Polynomial() {
    	this.coefficients = new double[0];
    	this.exponents = new int[0];
    }
    
    public Polynomial(double[] coeffs, int[] exps) {
    	this.coefficients = coeffs;
    	this.exponents = exps;
    }
    
    public Polynomial(File file) throws FileNotFoundException {
    	Scanner scanner = new Scanner(file);
    	String line = scanner.nextLine();
    	scanner.close();
    	
    	line = line.replace("-", "+-"); // Makes negative easy to split
    	if(line.startsWith("+-")) {
    		line = line.substring(1); // Remove a leading '+' if it was negative originally
    	}
    	
    	String[] terms = line.split("\\+");
    	
    	ArrayList<Double> coeffList = new ArrayList<>();
    	ArrayList<Integer> expList = new ArrayList<>();
    	
    	for(String term : terms) {
    		term = term.trim();
    		if(term.contains("x")) {
    			String[] parts = term.split("x");
    			double coeff;
    			if(parts[0].isEmpty() || parts[0].equals("+")) {
    				coeff = 1;
    			} else if(parts[0].equals("-")) {
    				coeff = -1;
    			} else {
    				coeff = Double.parseDouble(parts[0]);
    			}
    			
    			int exp;
    			if(parts.length > 1) {
    				exp = Integer.parseInt(parts[1]);
    			} else {
    				exp = 1;
    			}
    			
    			coeffList.add(coeff);
    			expList.add(exp);
    		} else {
    			coeffList.add(Double.parseDouble(term));
    			expList.add(0);
    		}
    	}
    	
    	this.coefficients = new double[coeffList.size()];
    	this.exponents = new int[expList.size()];
    	
    	for(int i = 0; i < coeffList.size(); i++) {
    		this.coefficients[i] = coeffList.get(i);
    		this.exponents[i] = expList.get(i);
    	}
    }
    
    public void print() {
    	for(int i = 0; i < this.coefficients.length; i++) {
    		System.out.println(this.coefficients[i] + " ^" + this.exponents[i]);
    	}
    }
    
    public Polynomial add(Polynomial p) {
    	int maxSize = this.coefficients.length + p.coefficients.length;
    	double[] tempCoeffs = new double[maxSize];
    	int[] tempExps = new int[maxSize];
    	int index = 0;
    	
    	for(int i = 0; i < this.coefficients.length; i++) {
    		tempCoeffs[index] = this.coefficients[i];
    		tempExps[index] = this.exponents[i];
    		index++;
    	}
    	
    	for(int i = 0; i < p.coefficients.length; i++) {
    		boolean found = false;
    		for(int j = 0; j < index; j++) {
    			if(tempExps[j] == p.exponents[i]) {
    				tempCoeffs[j] += p.coefficients[i];
    				found = true;
    				break;
    			}
    		}
    		
    		if(!found) {
    			tempCoeffs[index] = p.coefficients[i];
    			tempExps[index] = p.exponents[i];
    			index++;
    		}
    	}
    	
    	int count = 0;
    	for(int i = 0; i < index; i++) {
    		if(tempCoeffs[i] != 0) count++;
    	}
    	
    	double[] finalCoeffs = new double[count];
    	int[] finalExps = new int[count];
    	int k = 0;
    	for(int i = 0; i < index; i++) {
    		if(tempCoeffs[i] != 0) {
    			finalCoeffs[k] = tempCoeffs[i];
    			finalExps[k] = tempExps[i];
    			k++;
    		}
    	}
    	return new Polynomial(finalCoeffs, finalExps);
    }
    
    public double evaluate(double x) {
    	double sum = 0;
    	for(int i = 0; i < this.coefficients.length; i++) {
    		sum += (this.coefficients[i] * Math.pow(x, this.exponents[i]));
    	}
    	return sum;
    }
    
    public boolean hasRoot(double x) {
    	if(this.evaluate(x) == 0) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public Polynomial multiply(Polynomial p) {
    	int maxSize = this.coefficients.length * p.coefficients.length;
    	double[] tempCoeffs = new double[maxSize];
    	int[] tempExps = new int[maxSize];
    	int count = 0;
    	
    	for(int i = 0; i < this.coefficients.length; i++) {
    		for(int j = 0; j < p.coefficients.length; j++) {
    			double coeff = this.coefficients[i] * p.coefficients[j];
    			int exp = this.exponents[i] + p.exponents[j];
    			
    			boolean found = false;
    			for(int k = 0; k < count; k++) {
    				if(tempExps[k] == exp) {
    					tempCoeffs[k] += coeff;
    					found = true;
    					break;
    				}
    			}
    			
    			if(!found) {
    				tempCoeffs[count] = coeff;
    				tempExps[count] = exp;
    				count++;
    			}
    		}
    	}
    	
    	double[] finalCoeffs = new double[count];
    	int[] finalExps = new int[count];
    	for(int i = 0; i < count; i++) {
    		finalCoeffs[i] = tempCoeffs[i];
    		finalExps[i] = tempExps[i];
    	}
    	
    	return new Polynomial(finalCoeffs, finalExps);
    }
    
    private String formatTerm(double coeff, int exp) {
    	String coeffStr;
    	if(coeff == (int) coeff) {
    		coeffStr = Integer.toString((int) coeff);
    	} else {
    		coeffStr = Double.toString(coeff);
    	}
    	
    	if(exp == 0) {
    		return coeffStr;
    	} else {
    		return coeffStr + "x" + exp;
    	}
    }
    
    public void saveToFile(String filename) {
    	try {
	    	FileWriter writer = new FileWriter(filename);
	    	
	    	String output = "";
	    	
	    	for(int i = 0; i < coefficients.length; i++) {
	    		double coeff = coefficients[i];
	    		int exp = exponents[i];
	    		
	    		if(coeff == 0) continue;
	    		
	    		String term = formatTerm(coeff, exp);
	    		
	    		if(output.length() == 0) {
	    			output = term;
	    		} else {
	    			if(coeff < 0) {
	    				output += term;
	    			} else {
	    				output += "+" + term;
	    			}
	    		}
	    	}
	    	
	    	writer.write(output);
	    	writer.close();
	    	System.out.println("Polynomial saved to " + filename);
    	} catch (IOException e) {
    		System.out.println("Error writing to file: " + e.getMessage());
    	}
    }
    
}