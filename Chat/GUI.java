import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import commlayer.*;
import java.io.*;
import java.net.*;

public class GUI extends JFrame implements MessageReceived
{
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    
    private JLabel lblConnectTo;
    private JTextField txtConnectTo, txtInput;
    private JTextArea txtOutput;
    private JScrollPane spOutput;
    private JButton btnConnect, btnSend;
    
    private InputKeyPressHandler ikpHandler;
    private ConnectButtonHandler cbHandler;
    private SendButtonHandler sbHandler;
    
    private Server server;
    private Client client;
    
    public GUI()
    {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        //Output textarea
        txtOutput = new JTextArea(10, 20);
        spOutput = new JScrollPane(txtOutput);
        txtOutput.setEditable(false);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weighty = 1;
        pane.add(spOutput, gbc);
        
        //Connect to
        lblConnectTo = new JLabel("Connect to");
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        pane.add(lblConnectTo, gbc);
        
        //txtConnectTo
        txtConnectTo = new JTextField();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1;
        pane.add(txtConnectTo, gbc);
        
        //btnConnect
        btnConnect = new JButton("Connect");
        cbHandler = new ConnectButtonHandler();
        btnConnect.addActionListener(cbHandler);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1;
        pane.add(btnConnect, gbc);
        
        //txtInput
        txtInput = new JTextField();
        txtInput.setEditable(false);
        ikpHandler = new InputKeyPressHandler();
        txtInput.addActionListener(ikpHandler);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        pane.add(txtInput, gbc);
        
        //btnSend
        btnSend = new JButton("Send");
        btnSend.setEnabled(false);
        sbHandler = new SendButtonHandler();
        btnSend.addActionListener(sbHandler);
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1;
        pane.add(btnSend, gbc);
        
        setTitle("StreetChat - Java version");
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        server = new Server(666);
        server.addListener(this);
    }
    
    private void connect()
    {
        try
        {
            client = new Client(txtConnectTo.getText(), 666);
            txtInput.setEditable(true);
            btnConnect.setEnabled(false);
            btnSend.setEnabled(true);
        }
        catch (UnknownHostException uhe)
        {
            println("Unknown host: " + uhe.getMessage());
        }
        catch (IOException ioe)
        {
            println("ERROR: " + ioe);
        }
    }
    
    private void println(String line)
    {
        txtOutput.append(line + "\n\r");
    }
    
    public void messageReceived(String message)
    {
        println(message);
    }
    
    private void sendMessage()
    {
        if (txtInput.getText().length() > 0)
        {
            try
            {
                println(">" + txtInput.getText());
                client.sendMessage(txtInput.getText());
                txtInput.setText("");
            }
            catch (Client.CouldNotSendException ex)
            {
                println(ex.getMessage());
            }
        }
        
        txtInput.requestFocus();
    }
    
    private class InputKeyPressHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            sendMessage();
        }
    }
    
    private class ConnectButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            connect();
        }
    }
    
    private class SendButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            sendMessage();
        }
    }
}