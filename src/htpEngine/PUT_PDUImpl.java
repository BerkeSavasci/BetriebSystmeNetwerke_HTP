package htpEngine;

public class PUT_PDUImpl implements PUT_PDU {
    private final String FILE_NAME;
    private final int BYTE_NUM;
    private final byte[] FILE_CONTENT;

    public PUT_PDUImpl(String fileName, int byteNum, byte[] fileContent) {
        FILE_NAME = fileName;
        BYTE_NUM = byteNum;
        FILE_CONTENT = fileContent;
    }

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    public int getByteNum() {
        return BYTE_NUM;
    }

    @Override
    public byte[] getFileContent() {
        return FILE_CONTENT;
    }


}
