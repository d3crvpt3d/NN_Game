public class NeuralNetwork {
    
    double lastMax;
    double[] inputs;
    double[][] tmp_out;
    double[][][] weights;
    int it, it2, it3, indx, output;
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

        weights = new double[hiddenLayer.length + 1][Math.max(inputDepth * hiddenLayer[0], hiddenLayer[0] * outputDepth)][Math.max(inputDepth * hiddenLayer[0], hiddenLayer[0] * outputDepth)];

        //declare weights random
        for(it = 0; it < weights.length; it++){
            for(it2 = 0; it2 < weights[0].length; it2++){
                for(it3 = 0; it3 < weights[0][0].length; it3++){
                    weights[it][it2][it3] = -1 + 2 * Math.random(); //[currLayer][inputNeurons][outputNeurons]
                }
            }
        }
    }

    //returns next weight from file
    double getNextWeight(){
        weightReadNumber++;
        return -1 + 2 * Math.random();//TODO
    }

    //get Action to be performed (0:null,1:right,2:left,3:jump)
    public int getOutput(double[] inputs){
        tmp_out[0] = inputs;
        updateOutput();
        return output;
    }

    //update Output
    private void updateOutput(){

        for(it = 0; it < tmp_out.length-1; it++){
            tmp_out[it+1] = matrixMultiplication(weights[it], tmp_out[it]);
        }

        output = getIndexOfMax();
        //end of updateOutput
    }

    //returns dot product of input and weights (Layer+1)
    double[] matrixMultiplication(double[][] a, double[] b){
        double[] tmp = new double[a[0].length];

        for(it2 = 0; it2 < a.length; it2++){
            for(it3 = 0; it3 < b.length; it3++){
                tmp[it2] += a[it2][it3] * b[it3];
            }    
        }

        return tmp;
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