package de.invesdwin.norva.beanpath;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class LongCountingOutputStream extends FilterOutputStream {
    private long bytesWritten = 0;

    public LongCountingOutputStream(final OutputStream out) {
        super(out);
    }

    @Override
    public void write(final int b) throws IOException {
        out.write(b);
        count(1);
    }

    @Override
    public void write(final byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public void write(final byte[] b, final int off, final int len) throws IOException {
        out.write(b, off, len);
        count(len);
    }

    /**
     * Increments the counter of already written bytes. Doesn't increment if the EOF has been hit (written == -1)
     *
     * @param written
     *            the number of bytes written
     */
    protected void count(final int written) {
        if (written != -1) {
            bytesWritten += written;
        }
    }

    /**
     * Returns the current number of bytes written to this stream.
     *
     * @return the number of written bytes
     */
    public long getCount() {
        return bytesWritten;
    }
}
