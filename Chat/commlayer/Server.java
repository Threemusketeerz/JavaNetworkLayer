package commlayer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server extends Thread implements MessageReceived
{
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream streamIn = null;
    private int port;
    private ArrayList<MessageReceived> listeners = new ArrayList<>();
    
    public Server(int port)
    {
        this.port = port;
        start();
    }
    
    public void addListener(MessageReceived toAdd)
    {
        listeners.add(toAdd);
    }
    
    public void run()
    {
        try
        {
            server = new ServerSocket(port);
            socket = server.accept();
            
            streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            
            boolean done = false;
            
            while (!done)
            {
                try
                {
                    String line = streamIn.readUTF();
                    messageReceived(line);
                    done = line.equals(".bye");
                }
                catch(IOException ioe)
                {
                    done = true;
                }
            }
            
            close();
        }
        catch (IOException ioe)
        {
            System.out.println("Could not start server!");
        }
    }
    
    private void close() throws IOException
    {
        if (socket != null) socket.close();
        if (streamIn != null) streamIn.close();
    }
    
    public void messageReceived(String message)
    {
        for (MessageReceived listener : listeners)
            listener.messageReceived(message);
    }
}