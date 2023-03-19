public class Layer {
    private Neuron[] neurons;
    private int depth;

    public Layer(int depth){
        this.depth = depth;
        for(int i = 0; i < depth; i++){
            neurons[i] = new Neuron(Math.random(), "relu");
        }
    }

    public int getDepth() {
        return depth;
    }
}