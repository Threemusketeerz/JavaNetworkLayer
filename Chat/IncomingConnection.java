import javax.swing.JOptionPane;

public class IncomingConnection
{
    public static boolean IncomingConnection(String from)
    {
        String message = "Incoming message from " + from + ". Accept?";
        int answer = JOptionPane.showConfirmDialog(null, message, "Incoming request", JOptionPane.YES_NO_OPTION);
        return answer == 0;
    }
}