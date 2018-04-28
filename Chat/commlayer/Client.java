package commlayer;
import java.net.*;
import java.io.*;

public class Client extends Thread
{
    private Socket socket = null;
    private DataOutputStream streamOut = null;
    private String serverName;
    private int serverPort;
    
    public Client(String serverName, int serverPort) throws IOException
    {
        this.serverName = serverName;
        this.serverPort = serverPort;
        
        socket = new Socket(serverName, serverPort);
        streamOut = new DataOutputStream(socket.getOutputStream());
    
        start();
    }
    
    public void run()
    {
        
    }
    
    public void sendMessage(String message) throws CouldNotSendException
    {
        try
        {
            streamOut.writeUTF(message);
            streamOut.flush();
        }
        catch (IOException ioe)
        {
            throw new CouldNotSendException(socket.getInetAddress().toString());
        }
    }
    
    public void stopClient()
    {
        try
        {
            if (streamOut != null) streamOut.close();
            if (socket != null) socket.close();
        }
        catch (IOException ioe)
        {
            
        }
    }
    
    public class CouldNotSendException extends Exception
    {
        public CouldNotSendException(String host)
        {
            super("Could not send message to " + host);
        }
    }
}