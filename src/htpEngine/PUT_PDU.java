package htpEngine;

public interface PUT_PDU {
    /**
     * @return file name as absolute or relative file name
     */
    String getFileName();

    /**
     * @return Number of Bytes that will be sent
     */
    int getByteNum();

    /**
     * @return file contents as bytes
     */
    byte[] getFileContent();

}
