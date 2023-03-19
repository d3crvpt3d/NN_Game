public class OutFunction {
    
    private String type;

    public OutFunction(String type){
        this.type = type;    
    }

    public double calc(double x){
        switch(type){
            case "relu": return relu(x);
            case "sigmoid": return sigmoid(x);
            case default: return relu(x);
        }
    }

    private double relu(double x){
        return Math.max(0, x);
    }

    private double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    }
}
