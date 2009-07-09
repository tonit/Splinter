package org.ops4j.splinter.api;

import java.io.InputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.ByteArrayInputStream;

/**
 * Delegating Stream.
 */
public class SIinputStream extends InputStream implements Serializable
{

    private transient ByteArrayInputStream realStream = null;
    private byte[] m_bytes;

    public SIinputStream( byte[] bytes )
    {
        m_bytes = bytes;
    }

    public int read()
        throws IOException
    {
        init();
        return realStream.read();
    }

    private void init()
    {
        if( realStream == null )
        {
            realStream = new ByteArrayInputStream( m_bytes );
        }
    }

    @Override
    public int read( byte[] bytes )
        throws IOException
    {
        init();
        return realStream.read( bytes );
    }

    @Override
    public int read( byte[] bytes, int i, int i1 )
        throws IOException
    {
        init();
        return realStream.read( bytes, i, i1 );
    }

    @Override
    public long skip( long l )
        throws IOException
    {
        init();
        return realStream.skip( l );
    }

    @Override
    public int available()
        throws IOException
    {
        init();
        return realStream.available();
    }

    @Override
    public void close()
        throws IOException
    {
        init();
        realStream.close();
    }

    @Override
    public void mark( int i )
    {
        init();
        realStream.mark( i );
    }

    @Override
    public void reset()
        throws IOException
    {
        init();
        realStream.reset();
    }

    @Override
    public boolean markSupported()
    {
        init();
        return realStream.markSupported();
    }
}
