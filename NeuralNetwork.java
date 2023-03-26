public class NeuralNetwork {
    
    double lastMax;
    double[] inputs;
    double[][] tmp_out;
    double[][][] weights;
    int it, it2, it3, indx, output, ouputDepth;
    Layer[] layer;

    long weightReadNumber;

    public NeuralNetwork(int inputDepth, int[] hiddenLayer, int outputDepth){

        this.ouputDepth = outputDepth;

        this.layer = new Layer[hiddenLayer.length+2];

        //set tmp_out dimension (max layer depth)
        tmp_out = new double[hiddenLayer.length+2][Math.max(Math.max(inputDepth, hiddenLayer[0]), outputDepth)];

        //set input layer depth
        layer[0] = new Layer(inputDepth);

        //set hidden layer depth
        for(int it = 1; it < layer.length - 1 ; it++){
            layer[it] = new Layer(hiddenLayer[it-1]);
        }

        //set output layer depth
        layer[layer.length-1] = new Layer(outputDepth);

        //+++++++++++++
        weights = new double[hiddenLayer.length + 1]
                            [Math.max(Math.max(inputDepth, hiddenLayer[0]), outputDepth)]
                            [Math.max(Math.max(inputDepth, hiddenLayer[0]), outputDepth)];
        
        //+++++++++++++

        //declare weights random
        for(int it = 0; it < weights.length; it++){
            for(int it2 = 0; it2 < weights[it].length; it2++){
                for(int it3 = 0; it3 < weights[it][it2].length; it3++){

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

        for(int it = 0; it < tmp_out.length-1; it++){
            tmp_out[it+1] = matrixMultiplication(weights[it], tmp_out[it]);
        }

        output = getIndexOfMax();
        //end of updateOutput
    }

    //returns dot product of input and weights (Layer+1)
    double[] matrixMultiplication(double[][] a, double[] b){

        double[] tmp = new double[a[0].length]; //out size

        for(int it2 = 0; it2 < a[0].length; it2++){    //out iterator
            for(int it3 = 0; it3 < b.length; it3++){   //in iterator
                tmp[it2] += a[it3][it2] * b[it3];
            }    
        }
        return tmp;
    }

    //return index of max output neuron
    private int getIndexOfMax(){
        for(int it = 0; it < ouputDepth; it++){
            if(tmp_out[tmp_out.length-1][it] > lastMax){
                lastMax = tmp_out[tmp_out.length-1][it];
                indx = it;
            }
        }
        return indx;
    }
}