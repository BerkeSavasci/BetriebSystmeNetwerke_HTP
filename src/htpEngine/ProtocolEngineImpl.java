package htpEngine;

import java.io.*;

public class ProtocolEngineImpl implements ProtocolEngine {
    private final InputStream is;
    private final OutputStream os;
    private final HTPSerializer serializer;
    private final String PATH = "/Users/berkesavasci/Documents/Betriebsysteme/BSys";

    public ProtocolEngineImpl(InputStream is, OutputStream os, HTPSerializer serializer) {
        this.is = is;
        this.os = os;
        this.serializer = serializer;
    }

    /**
     * requests a file from foreign user
     *
     * @param filename to be requested
     * @throws IOException error :(
     */
    @Override
    public void getFile(String filename) throws IOException {
        GET_PDUImpl getPdu = new GET_PDUImpl(filename);
        this.serializer.serializeGetPDU(getPdu, this.os);
    }

    /**
     * sends the file
     *
     * @param filename to be sent
     * @throws IOException error :(
     */
    @Override
    public void putFile(String filename) throws IOException {
        File file = new File(filename);

        if (file.isFile()) {
            try {
                FileInputStream fis = new FileInputStream(filename);
                byte[] fileContent = fis.readAllBytes();
                fis.close();

                PUT_PDU putPdu = new PUT_PDUImpl(filename, fileContent.length, fileContent);
                // send PDU
                this.serializer.serializePutPDU(putPdu, this.os);

            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    /**
     * sends an error message if something went wrong
     *
     * @param filename   file name with the error
     * @param errMessage given error Message
     * @param errCode    given error code
     * @throws IOException error :(
     */
    public void sendError(String filename, String errMessage, byte errCode) throws IOException {
        ERROR_PDUImpl errorPdu = new ERROR_PDUImpl(filename, errMessage, errCode);
        this.serializer.serializeErrorPDU(errorPdu, this.os);
    }

    /**
     * check wich PDU type we receive
     * deserialize accordingly
     *
     * @throws IOException error :(
     */
    public void readFromInputStream() throws IOException {
        DataInputStream dais = new DataInputStream(this.is);
        PDU_Type pduType = this.serializer.readProtocolHeader(dais);
        switch (pduType) {
            //if engine gets a "GET" request, receiver sends
            case GET:
                GET_PDU getPdu = this.serializer.deserializeGetPDU(this.is);
                this.handleGetPDU(getPdu);
                break;
            //if engine gets a "PUT" request, check if it can create than read
            case PUT:
                PUT_PDU putPdu = this.serializer.deserializePutPDU(this.is);
                this.handlePutPDU(putPdu);
                break;
            case ERROR:
                ERROR_PDU errorPdu = this.serializer.deserializeErrorPDU(this.is);
                this.handleErrorPDU(errorPdu);
                break;
        }
    }

    /**
     * when user sends a get method check if the file at the given path exists if yes
     * call "putFile" with the requested file name
     * else send error message to the requester
     *
     * @param getPdu pdu type get
     * @throws IOException if something went wrong
     */
    private void handleGetPDU(GET_PDU getPdu) throws IOException {
        File f = new File(getPdu.getFileName()); // file path
        if (f.isFile()) {
            try {
                putFile(getPdu.getFileName());
            } catch (IOException e) {
                System.out.println("Send an error");
            }
        } else {
            sendError(getPdu.getFileName(), "file doesn't exist", (byte) 1);
            System.out.println("Error: file doesnt exist");
        }
    }

    /**
     * check if the file can be created in PATH
     * write the content file in test
     *
     * @param putPdu pdu type get
     * @throws IOException error :(
     */
    private void handlePutPDU(PUT_PDU putPdu) throws IOException {
        String dateiname = putPdu.getFileName();
        byte[] dateiInhalt = putPdu.getFileContent();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dateiname);
            fos.write(dateiInhalt);
            fos.flush();
            System.out.println("File send successful " + dateiname);

        } catch (IOException e) {

            System.err.println("Send error");

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.err.println("Error");
                }
            }
        }
    }

    private void handleErrorPDU(ERROR_PDU errorPdu) throws IOException {
        String filename = errorPdu.getFileName();
        String errMessage = errorPdu.getErrorMessage();
        byte errCode = errorPdu.GetErrorCode();

        System.out.println("The file " + filename + " couldn't be send.\n" +
                "Error code: " + errCode + "\nMessage: " + errMessage);
    }
}
