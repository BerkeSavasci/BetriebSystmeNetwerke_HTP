package FileCopy;

import networks.ConnectionCreatedListener;

import java.io.*;

public class AppLogicFileCopy implements ConnectionCreatedListener {
    private final String fileName;

    public AppLogicFileCopy(String fileName) {
        this.fileName = fileName;
    }

    void copy(OutputStream os, InputStream is) throws IOException {
       int value = 0;
        while (value > -1){
            value = is.read();
            if (value > -1) { // checks if read value is -1
                os.write(value);
            }else{
                os.close();
            }
        }
        /* while(true){
            int value = is.read();
            os.write(value);
        }*/
    }
    @Override
    public void connectionCreated(InputStream is, OutputStream os, boolean asServer, String otherPeerAddress) {
        InputStream sourceIS = null;
        OutputStream targetOS = null;

        try {
            if(asServer) { //if i am server
                // I am data sink
                // open file output stream - TODO
                targetOS = new FileOutputStream(fileName);
                // what is source IS? - TODO
                sourceIS = is;

            } else { // else I am client
                // I am data source
                // open file input stream and write into output stream - TODO
                sourceIS = new FileInputStream(fileName);
                // what is targetOS? - TODO
                targetOS = os;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage());
            System.err.println("give up");
            return;
        }

        try {
            this.copy(targetOS, sourceIS);
            sourceIS.close();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
