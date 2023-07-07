package htpEngine;

public class GET_PDUImpl implements GET_PDU {
    private final String filename;

    public GET_PDUImpl(String filename) {
        this.filename = filename;
    }

    @Override
    public String getFileName() {
        return filename;
    }
}
