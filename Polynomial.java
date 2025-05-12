public class Polynomial {
    double[] coeffs;
    
    public Polynomial() {
    	this.coeffs = new double[]{0};
    }
    
    public Polynomial(double[] coefficients) {
    	this.coeffs = coefficients;
    }
    
    public Polynomial add(Polynomial p) {
    	Polynomial q = new Polynomial();
    	
    	double[] a;
    	
    	if(this.coeffs.length >= p.coeffs.length) {
    		a = new double[this.coeffs.length];
    	} else {
    		a = new double[p.coeffs.length];
    	}
    	
    	for(int i = 0; i < a.length; i++) {
    		if(i < this.coeffs.length && i < p.coeffs.length) {
    			a[i] = this.coeffs[i] + p.coeffs[i];
    		} else if (i < this.coeffs.length && i >= p.coeffs.length) {
    			a[i] = this.coeffs[i];
    		} else {
    			a[i] = p.coeffs[i];
    		}
    	}
    	q.coeffs = a;
    	return q;
    }
    
    public double evaluate(double x) {
    	double sum = 0;
    	for(int i = 0; i < this.coeffs.length; i++) {
    		sum += (this.coeffs[i] * Math.pow(x, i));
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
    
}