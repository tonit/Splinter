package org.ops4j.splinter.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.ops4j.io.StreamUtils;
import org.ops4j.splinter.api.SIinputStream;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jul 8, 2009
 * Time: 5:55:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Activator implements BundleActivator
{

    private static final String CON = "r-osgi://localhost:9278";
    private InstallSplinterServices m_installSplinterServices;
    private Client m_client;

    public void start( final BundleContext context )
        throws Exception
    {
        m_installSplinterServices = new InstallSplinterServices( CON, context );
        m_installSplinterServices.install();
        m_client = new Client( context );
        new Thread( new Runnable()
        {
            public void run()
            {

                m_client.doX();
            }
        }
        ).start();
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        m_client.close();
        m_installSplinterServices.uninstall();
    }

    private static InputStream intoMemory( InputStream inputStream )
    {
        ByteArrayOutputStream inp = new ByteArrayOutputStream();
        try
        {
            StreamUtils.copyStream( inputStream, inp, true );
            return new SIinputStream( inp.toByteArray() );
        } catch( IOException e )
        {
            throw new RuntimeException( e );
        }

    }
}
