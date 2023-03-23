public class NeuralNetwork {
    
    double lastMax, mutationRate = .1;
    double[] inputs;
    double[][] weights, tmp_out;
    int it, it2, indx, output;
    Layer[] layer;

    long weightReadNumber;

    public NeuralNetwork(int inputDepth, int[] hiddenLayer, int outputDepth){
        
        //set layer size
        this.layer = new Layer[hiddenLayer.length+2];

        //set tmp_out dimension (max layer depth)
        tmp_out = new double[hiddenLayer.length+2][Math.max(Math.max(inputDepth, hiddenLayer[0]), outputDepth)];

        //set input layer depth
        layer[0] = new Layer(inputDepth);

        //set hidden layer depth
        for(it = 1; it < layer.length - 1 ; it++){
            layer[it] = new Layer(hiddenLayer[it-1]);
        }

        //set output layer depth
        layer[layer.length-1] = new Layer(outputDepth);

        weights = new double[hiddenLayer.length + 1][Math.max(inputDepth * hiddenLayer[0], hiddenLayer[0] * outputDepth)]; //["][max layer*layer]

        //declare weights random
        for(it = 0; it < weights.length; it++){
            for(it2 = 0; it2 < weights[0].length; it2++){
                weights[it][it2] = -1 + 2 * Math.random();
            }
        }
        
    }

    //returns next weight from file
    double getNextWeight(){
        weightReadNumber++;
        return 0;//TODO
    }

    //get Action to be performed (0:null,1:right,2:left,3:jump)
    public int getOutput(double[] inputs){
        tmp_out[0] = inputs;
        updateOutput();
        return output;
    }

    //update Output (prob not properly)
    private void updateOutput(){//TODO

        //per layer
        for(it = 0; it < layer.length-1; it++){

            //per neuron
            for (it2 = 0; it2 < layer[it].getDepth(); it2++) {

                //dot multiplication
                tmp_out[it+1][it2] += layer[it].neurons[it2].calculate(tmp_out[it][it2] * weights[it][it2]); //neuron does bias and output func
            }

            //end of per layer
        }

        output = getIndexOfMax();
        //end of updateOutput
    }

    //return index of max output neuron
    private int getIndexOfMax(){
        for(it = 0; it < tmp_out[tmp_out.length-1].length; it++){
            if(tmp_out[tmp_out.length-1][it] > lastMax){
                lastMax = tmp_out[tmp_out.length-1][it];
                indx = it;
            }
        }
        return indx;
    }
}