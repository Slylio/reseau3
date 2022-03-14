import java.net.*;
import java.io.*;

public class FileTransfer{

    public static void main(String[] args){
        if (args.length==2){
            Server server = new Server(args[0],Integer.parseInt(args[1]));
            try {
                server.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (args.length==3){
            Client client = new Client(args[0],Integer.parseInt(args[1]), args[2]);
            try {
                client.run();
            } catch (Exception e) {
                System.err.println("les paramètres attendus sont : hostname, numéro de socket, nom du fichier qu'on veut lire");
            }
        }
    }
    
    
    private class Client{
        private String hostname;
        private int socketNum;
        private String filename;

        private Client(String hostname, int socketNum, String filename){
            this.hostname=hostname;
            this.socketNum=socketNum;
            this.filename=filename;
        }

        private void run() throws UnknownHostException, IOException{
            Socket client = new Socket(hostname,socketNum);
            System.out.println("Connexion au serveur...");
            try (InputStream in = new FileInputStream(new File(filename))){
                byte[] data= new byte[1024];
                int l= in.read(data,0,1024);
                OutputStream out = client.getOutputStream();
                while (l!= -1) {
                    out.write(data,0,l);
                    l= in.read(data,0,1024);
                }
                out.close();
                in.close();
            } catch (IOException e){
                System.err.println(e);
            }
        }
    }

    private class Server{
        private String hostname;
        private int socketNum;
        private Server(String hostname, int socketNum){
            this.hostname=hostname;
            this.socketNum=socketNum;
        }

        private void run() throws IOException{
            ServerSocket fileServer = new ServerSocket(socketNum);
            System.out.println("Serveur lancé sur le port : "+ socketNum);
            System.out.println("Ecoute...");
            Socket socketClient = fileServer.accept();
            OutputStream out = 
            System.out.println("Connexion : "+socketClient.getInetAddress());
        }
    }
}

