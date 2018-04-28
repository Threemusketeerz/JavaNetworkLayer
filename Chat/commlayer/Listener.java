package commlayer;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Listener
{
    private ServerSocket server = null;
    private Socket socket = null;
    private DataInputStream streamIn = null;
    private int port;
    private ArrayList<MessageReceived> listeners = new ArrayList<>();

    public Listener()
    {
        
    }
}