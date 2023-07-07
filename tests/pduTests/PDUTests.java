package pduTests;

import htpEngine.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PDUTests {
    private final String FILE_NAME ="File.txt";
    private final int BYTE_NUM = 1;
    private final byte [] FILE_CONTENT = {123};
    private final String ERROR_MESSAGE ="Die angefragte Datei existierte nicht";
    private final byte ERROR_CODE = 0x01;
    @Test
    void get_PDUTest() throws IOException {
        HTPSerializer serializer = new HTPSerializer();
        GET_PDU getPdu = new GET_PDUImpl(FILE_NAME);

        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // create a byte array to test data out/input

        serializer.serializeGetPDU(getPdu, baos); // simulates data output

        byte[] sendBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(sendBytes);
        GET_PDU receivedGetPDU = serializer.deserializeGetPDU(bais);

        Assertions.assertEquals(getPdu.getFileName(),receivedGetPDU.getFileName());
    }
    @Test
    void put_PDUTest()throws IOException{
        HTPSerializer serializer = new HTPSerializer();
        PUT_PDU putPdu = new PUT_PDUImpl(FILE_NAME,BYTE_NUM,FILE_CONTENT);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serializer.serializePutPDU(putPdu,baos);

        byte[] sendBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(sendBytes);
        PUT_PDU receivedPutPDU = serializer.deserializePutPDU(bais);

        Assertions.assertEquals(putPdu.getFileName(),receivedPutPDU.getFileName());
        Assertions.assertEquals(putPdu.getByteNum(),receivedPutPDU.getByteNum());
        Assertions.assertArrayEquals(putPdu.getFileContent(), receivedPutPDU.getFileContent());
    }
    @Test
    void error_PDUTest()throws IOException{
        HTPSerializer serializer = new HTPSerializer();
        ERROR_PDU errorPdu = new ERROR_PDUImpl(FILE_NAME,ERROR_MESSAGE,ERROR_CODE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serializer.serializeErrorPDU(errorPdu,baos);

        byte[] sendBytes = baos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(sendBytes);
        ERROR_PDU receivedErrorPDU = serializer.deserializeErrorPDU(bais);

        Assertions.assertEquals(errorPdu.getFileName(),receivedErrorPDU.getFileName());
        Assertions.assertEquals(errorPdu.getErrorMessage(),receivedErrorPDU.getErrorMessage());
        Assertions.assertEquals(errorPdu.GetErrorCode(),receivedErrorPDU.GetErrorCode());
    }
}
