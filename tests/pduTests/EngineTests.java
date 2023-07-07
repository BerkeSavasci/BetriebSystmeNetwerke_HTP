package pduTests;

import htpEngine.HTPSerializer;
import htpEngine.ProtocolEngine;
import htpEngine.ProtocolEngineImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class EngineTests {
    private final String FILE_NAME = "test file";

    @Test
    public void readFromFile() throws IOException {
        //input stream
        ByteArrayInputStream isA = new ByteArrayInputStream(new byte[0]);
        //output stream
        ByteArrayOutputStream osA = new ByteArrayOutputStream();

        HTPSerializer serializerA = new HTPSerializer();
        ProtocolEngine engineA = new ProtocolEngineImpl(isA, osA, serializerA);
        engineA.getFile(this.FILE_NAME);

        byte[] serializeGetPDU = osA.toByteArray();
        ByteArrayInputStream isB = new ByteArrayInputStream(serializeGetPDU);
        ByteArrayOutputStream osB = new ByteArrayOutputStream();

        HTPSerializer serializerB = new HTPSerializer();
        ProtocolEngine engineB = new ProtocolEngineImpl(isB,osB,serializerB);

        engineB.readFromInputStream();

        byte[] outputB = osB.toByteArray();
        Assertions.assertTrue(outputB.length>0);

        isA = new ByteArrayInputStream(outputB);
        osA = new ByteArrayOutputStream();

        engineA = new ProtocolEngineImpl(isA,osA,serializerA);
        engineA.readFromInputStream();

    }
}
