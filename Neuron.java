public class Neuron{

    double bias;
    OutFunction out_function;

    public Neuron(double bias, String func){
        this.out_function = new OutFunction(func);
        this.bias = bias;
    }

    public double calculate(int x){
        return out_function.calc(x);
    }
}