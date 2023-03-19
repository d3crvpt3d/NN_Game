public class NeuralNetwork {
    
    Double[] inputs;
    Double[][] weights, bias, tmp_out;
    int output, it, currLayer;
    Layer[] layer;

    public NeuralNetwork(int inputDepth, int[] hiddenLayer, int outputDepth){
        
        //set layer size
        this.layer = new Layer[hiddenLayer.length+2];

        //set input layer depth
        layer[0] = new Layer(inputDepth);

        //set hidden layer depth
        for(it = 1; it < layer.length - 1 ; it++){
            layer[it] = new Layer(hiddenLayer[it]);
        }

        //set output layer depth
        layer[-1] = new Layer(outputDepth);

        weights = new Double[hiddenLayer.length + 1][Math.max(inputDepth * hiddenLayer[0], hiddenLayer[0] * outputDepth)]; //["][max layer*layer]
        bias = new Double[hiddenLayer.length + 1][Math.max(Math.max(inputDepth, hiddenLayer[0]), outputDepth)]; // [total layer -1] [maximum layer depth]
    }

    //get Action to be performed (0:null,1:right,2:left,3:jump)
    public int getOutput(Double[] inputs){
        this.inputs = inputs;
        updateOutput();
        return output;
    }

    private void updateOutput(){

        //per layer
        for(currLayer = 0; currLayer < weights[0].length; currLayer++){
            //per neuron
            for (it = 0; it < bias[currLayer].length; it++) {
                tmp_out[currLayer][it] += inputs[it] * weights[currLayer][0];
            }
        }
    }
}


// output = max(outLayer[0:-1])