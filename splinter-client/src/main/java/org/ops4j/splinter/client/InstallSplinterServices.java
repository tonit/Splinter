package org.ops4j.splinter.client;

import java.io.IOException;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.BundleContext;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;
import org.ops4j.splinter.api.Mini;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jul 8, 2009
 * Time: 10:31:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class InstallSplinterServices
{

    private RemoteOSGiService m_remote;
    private BundleContext m_bundleContext;
    private String m_url;

    public InstallSplinterServices( String url, BundleContext bundleContext )
    {
        m_bundleContext = bundleContext;
        m_url = url;
    }

    public void install()
    {
        ServiceTracker tracker = new ServiceTracker( m_bundleContext, RemoteOSGiService.class.getName(), new ServiceTrackerCustomizer()
        {
            public Object addingService( ServiceReference serviceReference )
            {
                m_remote = (RemoteOSGiService) m_bundleContext.getService( serviceReference );
                // connect
                try
                {
                    RemoteServiceReference[] remoteServiceReferences = m_remote.connect( new URI( m_url ) );
                    System.out.println( "Found " + remoteServiceReferences.length + " services." );

                    // install all exposed services as proxies.
                    for( RemoteServiceReference found : remoteServiceReferences )
                    {
                        Object instance = m_remote.getRemoteService( found );
                        if( instance instanceof Mini )
                        {
                            System.out.println( instance.getClass().getName() + " is instance of " + Mini.class.getName() );
                        }
                        else
                        {
                            System.out.println( instance.getClass().getName() + " is NOT instance of " + Mini.class.getName() );

                        }
                    }

                } catch( IOException e )
                {
                    System.out.println( "Problem: " + e.getMessage() );
                }
                return m_remote;
            }

            public void modifiedService( ServiceReference serviceReference, Object o )
            {

            }

            public void removedService( ServiceReference serviceReference, Object o )
            {
                m_remote.disconnect( new URI( m_url ) );
                m_remote = null;
            }
        }
        );
        tracker.open();
    }

    public void uninstall()
    {
        m_remote.disconnect( new URI( "r-osgi://localhost:9278" ) );
        m_remote = null;
        // m_remoteOSGiActivator.stop( bundleContext );
    }
}
