package htpEngine;

public interface ERROR_PDU {
    /**
     * @return file name
     */
    String getFileName();

    /**
     * @return error messages 1-3
     */
    String getErrorMessage();


    /**
     * @return return error codes 1-3
     */
    Byte GetErrorCode();

}
