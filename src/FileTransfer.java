import java.net.*;
import java.io.*;

public class FileTransfer{
    private String hostname;
    private int socketNum;
    private String filename;
    
    //cas ou serveur en attente     
    public FileTransfer(String hostname, int socketNum){
        this.hostname=hostname;
        this.socketNum=socketNum;
    }
    //cas de client qui envoie le fichier
    private FileTransfer(String hostname, int socketNum, String filename){
        this.hostname=hostname;
        this.socketNum=socketNum;
        this.filename=filename;
    }

    public static void main(String[] args){
        if (args.length==2){
            FileTransfer fileTransfer = new FileTransfer(args[0],Integer.parseInt(args[1]));
            try {
                fileTransfer.runServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (args.length==3){
            FileTransfer fileTransfer = new FileTransfer(args[0],Integer.parseInt(args[1]), args[2]);
            try {
                fileTransfer.runClient();
            } catch (Exception e) {
                System.err.println("les paramètres attendus sont : hostname, numéro de socket, nom du fichier qu'on veut lire");
            }
        }
    }
    //méthode en commun 
    private void transfer(InputStream in, OutputStream out) throws IOException{
        byte data[] = new byte[1024];   
        int l;
        while((l=in.read(data))!=-1){   //tant qu'il y a de la data (indicateur = -1)
            out.write(data,0,l);    //on écrit dans le output
        }
    }

    //méthode run pour le client
    private void runClient() throws UnknownHostException, IOException{
        Socket sock = new Socket(hostname,socketNum);
        //on prend un fichier en entrée et on renvoit la data
        transfer(new FileInputStream(filename),sock.getOutputStream());
        sock.close();
    }
        
    //méthode run pour le serveur
    private void runServer() throws UnknownHostException, IOException{
        try (Socket sock = new ServerSocket(socketNum).accept()) {  //try with ressource
            //on prend les data du client en entrée et on renvoie un file
            transfer(sock.getInputStream(),new FileOutputStream(filename)); 
            sock.close();
        } 
    }
}


