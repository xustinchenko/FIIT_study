
import database.Database;
import nn.TestNeuralNetwork;
import server.Server;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database();
        database.connectToDatabase();
        //new TestNeuralNetwork().startTest();
        Server server = Server.getServer();
        Thread t = new Thread(server);
        t.start();
    }
}
