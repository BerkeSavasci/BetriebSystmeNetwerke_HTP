import java.io.*;

import networks.ConnectionCreatedListener;

public class MyAppLogic implements ConnectionCreatedListener {
    public MyAppLogic() {
    }

    public void connectionCreated(InputStream is, OutputStream os, boolean asServer, String otherPeerAddress) {
        System.out.println("new connection created");
        DataOutputStream dos = new DataOutputStream(os); // create dos object to write correctly
        DataInputStream dis = new DataInputStream(is);   // create dis object to read correctly

        try {
            dos.writeInt(42); //output stream first write int
            dos.writeUTF("Hello"); //Then writeUtf (string)
            dos.writeBoolean(true);
            dos.writeDouble(2.98);

            int integer = -1;

            do {
                System.out.println("going to read");


                integer  = dis.readInt(); //input stream !!read Int first!
                String string = dis.readUTF(); // then the UTF (String)
                boolean bool = dis.readBoolean();
                double doub = dis.readDouble();

                System.out.println("read: " + integer);
                System.out.println("read: " + string);
                if (bool){
                    System.out.println("read: true");
                }
                System.out.println("read: " + doub);

                if (integer == -1) {
                    System.out.println("no more data in stream");
                }


            } while(integer > 0);
        } catch (IOException var6) {
            System.err.println("give up: " + var6.getLocalizedMessage());
        }

    }
}