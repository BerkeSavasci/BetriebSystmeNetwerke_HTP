package htpEngine;

import java.io.*;


public class HTPSerializer {
    public static final String PROTOCOL_NAME = "HTP";
    public static final int GET_PDU_BYTE = 1;
    public static final int PUT_PDU_BYTE = 2;
    public static final int ERROR_PDU_BYTE = 3;

    int readPDUType(InputStream is) throws IOException {
        return is.read();
    }

    void sendProtocolHeader(OutputStream os, PDU_Type pduType) throws IOException {
        DataOutputStream daos = new DataOutputStream(os);
        daos.writeUTF(PROTOCOL_NAME);
        switch (pduType) {
            case GET:
                os.write(GET_PDU_BYTE);
                break;
            case PUT:
                os.write(PUT_PDU_BYTE);
                break;
            case ERROR:
                os.write(ERROR_PDU_BYTE);
                break;
        }
    }

    PDU_Type readProtocolHeader(DataInputStream dais) throws IOException {
        String protocolString = dais.readUTF();
        if (!protocolString.equalsIgnoreCase(PROTOCOL_NAME)) {
            throw new EOFException("wrong protocol on stream");
        }
        byte pduType = (byte) dais.read();
        switch (pduType) {
            case GET_PDU_BYTE:
                return PDU_Type.GET;
            case PUT_PDU_BYTE:
                return PDU_Type.PUT;
            case ERROR_PDU_BYTE:
                return PDU_Type.ERROR;
            default:
                throw new IOException("unknown pdu type byte");
        }
    }

    public void serializeGetPDU(GET_PDU getPdu, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        this.sendProtocolHeader(dos, PDU_Type.GET);
        dos.writeUTF(getPdu.getFileName());
    }

    public GET_PDU deserializeGetPDU(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        String fileName = dis.readUTF();

        return new GET_PDUImpl(fileName);
    }

    public void serializePutPDU(PUT_PDU putPdu, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        this.sendProtocolHeader(dos, PDU_Type.PUT);

        dos.writeUTF(putPdu.getFileName()); //file name
        dos.writeInt(putPdu.getByteNum()); // number of bytes that will be sent
        dos.write(putPdu.getFileContent());    //file content
    }

    public PUT_PDU deserializePutPDU(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        String fileName = dis.readUTF();
        int byteNum = dis.readInt();

        byte[] fileContent = new byte[byteNum];
        dis.readFully(fileContent);
        return new PUT_PDUImpl(fileName, byteNum, fileContent);
    }

    public void serializeErrorPDU(ERROR_PDU errorPdu, OutputStream os) throws IOException {
        DataOutputStream dos = new DataOutputStream(os);
        this.sendProtocolHeader(dos, PDU_Type.ERROR);
        dos.writeUTF(errorPdu.getFileName());
        dos.writeByte(errorPdu.GetErrorCode());
        dos.writeUTF(errorPdu.getErrorMessage());
    }

    public ERROR_PDU deserializeErrorPDU(InputStream is) throws IOException {
        DataInputStream dis = new DataInputStream(is);
        String fileName = dis.readUTF();
        byte code1 = dis.readByte();
        String error_message1 = dis.readUTF();
        return new ERROR_PDUImpl(fileName, error_message1, code1);
    }

}

