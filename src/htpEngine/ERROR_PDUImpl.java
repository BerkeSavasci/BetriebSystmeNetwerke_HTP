package htpEngine;

public class ERROR_PDUImpl implements ERROR_PDU {
    private final String File_Name;
    private final String ERROR_MESSAGE;
    private final byte ERROR_BYTE;

    public ERROR_PDUImpl(String fileName, String errMessage, byte errCode) {
        this.File_Name = fileName;
        this.ERROR_MESSAGE = errMessage;
        this.ERROR_BYTE = errCode;
    }

    @Override
    public String getFileName() {
        return File_Name;
    }

    @Override
    public String getErrorMessage() {
        return ERROR_MESSAGE;
    }

    @Override
    public Byte GetErrorCode() {
        return ERROR_BYTE;
    }
}
