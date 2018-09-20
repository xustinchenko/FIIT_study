
import database.Database;
import nn.TestNeuralNetwork;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database();
        database.connectToDatabase();
        new TestNeuralNetwork().startTest();
        /*server.Server server = server.Server.getServer();
        Thread t = new Thread(server);
        t.start();*/
    }
}
