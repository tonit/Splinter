package org.ops4j.splinter.intern;

import java.rmi.RemoteException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import ch.ethz.iks.r_osgi.RemoteOSGiService;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jul 7, 2009
 * Time: 11:01:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Publishing
{

    /**
     * JCL logger.
     */
    private static final Log LOG = LogFactory.getLog( Publishing.class );

    private List<ServiceRegistration> m_regs = null;
    private BundleContext m_context;

    public Publishing( BundleContext context )
        throws RemoteException, BundleException
    {
        m_context = context;
        // try to find port from property
        m_regs = new ArrayList<ServiceRegistration>();

    }

    public void bind( final String type, final Object service )
        throws Exception
    {
        LOG.info( "Bindung Service: " + type + " of instance " + service );
        Dictionary properties = new Hashtable();
        properties.put( RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE );
        m_regs.add( m_context.registerService( type, service, properties ) );

        //!! Absolutely necessary for RMIClassLoading to work
//        ContextClassLoaderUtils.doWithClassLoader(
//            null, // getClass().getClassLoader()
//            new Callable<Object>()
//            {
//                public Object call()
//                    throws Exception
//                {
//                    try
//                    {
//                        exposeToRMI( type, service );
//                    }
//                    catch( Exception e )
//                    {
//                        throw new BundleException( "Cannot setup RMI registry", e );
//                    }
//                    return null;
//                }
//            }
//        );
    }

    public void unbindAll()
    {
        for( ServiceRegistration reg : m_regs )
        {
            try
            {
                System.out.println( "Unregistering " + reg.getReference().toString() );
                reg.unregister();
            } catch( Exception e )
            {
                //
            }
        }


    }


}
