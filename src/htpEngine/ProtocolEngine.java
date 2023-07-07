package htpEngine;

import java.io.IOException;

public interface ProtocolEngine {
    void getFile(String filename) throws IOException;

    void putFile(String filename) throws IOException;

    void sendError(String filename, String errMessage, byte errCode) throws IOException;

    void readFromInputStream() throws IOException;
}
