package TCPIntro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private Socket socket;

    public Server(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                String echoString = input.readLine();
                System.out.println("Recieved client input: " + echoString);
                if(echoString.equals("exit")){
                    break;
                }
                try{
                    Thread.sleep(3000);

                }catch(InterruptedException e){
                    System.out.println("Thread interrupted");
                }
                output.println(echoString);
            }
        }catch(IOException e){
            System.out.println("Oops: "+ e.getMessage());
        }finally{
            try{
                socket.close();
            }catch (IOException e){
                //no flagging
            }
        }
    }

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            while (true) {
                new Server(serverSocket.accept()).start();
            }

        }catch(IOException e){
            System.out.println("TCPIntro.Server exception " + e.getMessage());
        }
        new Client();
    }

}

