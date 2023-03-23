public class Layer {
    Neuron[] neurons;
    private int depth;

    public Layer(int depth){
        this.depth = depth;
        neurons = new Neuron[depth];
        for(int i = 0; i < depth; i++){
            neurons[i] = new Neuron(1, "relu");
        }
    }

    public int getDepth() {
        return depth;
    }

    double getBias(int x){
        return neurons[x].bias;
    }
}