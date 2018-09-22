package server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ConnectionWorker implements Runnable {

    /* сокет, через который происходит обмен данными с клиентом*/
    private Socket clientSocket = null;
    /* входной поток, через который получаем данные с сокета */
    private InputStream inputStream = null;
    public ConnectionWorker(Socket socket) {
        clientSocket = socket;
    }

    int fromByteArrayToInt(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    @Override
    public void run() {
        /* получаем входной поток */
        try {
            inputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Cant get input stream");
        }
        /* создаем буфер для данных */
        byte[] buffer = new byte[1024*4];
        while(true) {
            try {
                /*
                * * получаем очередную порцию данных
                * * в переменной count хранится реальное количество байт, которое получили
                * */
                int count = inputStream.read(buffer,0,buffer.length);
                /* проверяем, какое количество байт к нам прийшло */
                if (count > 0) {
                    int numOfMassage = fromByteArrayToInt(buffer);
                    System.out.println(numOfMassage);
                } else
                    /* если мы получили -1, значит прервался наш поток с данными  */
                    if (count == -1 ) {
                        System.out.println("close socket");
                        clientSocket.close();
                        break;
                    }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
