import java.io.IOException;
import java.net.*;
import java.util.StringTokenizer;

/**
 * Created by hasintha on 17/01/2017.
 */
public  class Node {
    private DatagramSocket sock = null;
    private String regMsg;

    private int serverPort =55555;
    private InetAddress serverIP;

    private int myPort = 55557;
    private InetAddress myIP = null;





    public Node() {
        /*set servers ip address and port number*/
        try {
            serverIP = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    /*get myIP address of the hosted pc*/
        try {
            myIP = InetAddress.getLocalHost();
            String ip= this.myIP.getHostAddress();
            System.out.println("myIP of my system is := " +ip );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        try {
            sock = new DatagramSocket(myPort);
            //System.out.println("Node created with serverPort 555556");
        } catch (SocketException e) {
            e.printStackTrace();

        }

        String reply = "REG "+this.myIP.getHostAddress()+" "+myPort+" node"+myPort;
        reply = String.format("%04d", reply.length() + 5) + " " + reply;
        System.out.println(reply);

        DatagramPacket dpReply = new DatagramPacket(reply.getBytes() , reply.getBytes().length , myIP, serverPort);


        try {
            sock.send(dpReply);
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*waiting for reply message*/
        while(true)
        {
            byte[] buffer = new byte[65536];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
            try {
                sock.receive(incoming);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] data = incoming.getData();
            String s = new String(data, 0, incoming.getLength());
            System.out.println(s);
            StringTokenizer st = new StringTokenizer(s, " ");
            String length = st.nextToken();
            String command = st.nextToken();
            String code=st.nextToken();
            System.out.println(code);



        }


    }

//    public boolean isRegistered(){
//
//
//    }


}
