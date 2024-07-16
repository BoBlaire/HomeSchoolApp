package com.example.gfgapp.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public class ByteArrayDataSource implements DataSource {
    private byte[] data; // Byte array to store the data
    private String type; // MIME type of the data

    /**
     * Constructor to initialize data and type.
     * @param data Byte array of the data.
     * @param type MIME type of the data.
     */
    public ByteArrayDataSource(byte[] data, String type) {
        super();
        this.data = data;
        this.type = type;
    }

    /**
     * Constructor to initialize data with default type.
     * @param data Byte array of the data.
     */
    public ByteArrayDataSource(byte[] data) {
        super();
        this.data = data;
    }

    /**
     * Sets the MIME type of the data.
     * @param type MIME type to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the MIME type of the data.
     * @return MIME type of the data. Defaults to "application/octet-stream" if type is null.
     */
    public String getContentType() {
        if (type == null)
            return "application/octet-stream";
        else
            return type;
    }

    /**
     * Gets an InputStream to read the data.
     * @return InputStream of the data.
     * @throws IOException if an I/O error occurs.
     */
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }

    /**
     * Gets the name of the data source.
     * @return Name of the data source.
     */
    public String getName() {
        return "ByteArrayDataSource";
    }

    /**
     * This method is not supported and will throw an IOException.
     * @return Unsupported operation.
     * @throws IOException always thrown as this method is not supported.
     */
    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Not Supported");
    }
}
