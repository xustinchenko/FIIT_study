package nn;

import com.googlecode.fannj.*;

public class TestNeuralNetwork {

    public void startTest() {
        Fann fann = new Fann("ann");
        float[][] tests = {
                {1, 0, 0, 0, 0, 1, 0, 0},
        };
        for (float[] test:tests){
            System.out.println(getAction(fann.run(test)));
        }
    }

    private static String getAction(float[] out){
        int i = 0;
        for (int j = 1; j < 2; j++) {
            if(out[i]<out[j]){
                i = j;
            }
        }
        switch (i){
            case 0:return "троль";
            case 1:return "не троль";
        }
        return "";
    }

}
