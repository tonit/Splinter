package org.ops4j.splinter.intern;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.deploymentadmin.DeploymentAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ops4j.splinter.api.Mini;
import org.ops4j.splinter.api.ExternDeploymentAdminImpl;

/**
 *
 */
public class Activator implements BundleActivator
{
    private static final Log LOG = LogFactory.getLog( Activator.class );


    private Publishing m_publisher;
    private ServiceTracker m_tracker;
    // private RemoteOSGiActivator m_remoteOSGiActivator;

    public void start( final BundleContext bundleContext )

    {
        LOG.info( "------- Starting Splinter Service" );

        try
        {
            m_tracker = new ServiceTracker( bundleContext, DeploymentAdmin.class.getName(), null );
            m_tracker.open();

            m_publisher = new Publishing( bundleContext );
            //m_publisher.bind( org.ops4j.pax.splinter.api.DeploymentAdmin.class.getName(), new ExternDeploymentAdminImpl( m_tracker ) );
            m_publisher.bind( Mini.class.getName(), new ServerMini() );
            LOG.info( "------- Binding External Deployment Admin Wrapper" );
            m_publisher.bind( DeploymentAdmin.class.getName(),new ExternDeploymentAdminImpl( m_tracker ) );
            LOG.info( "------- All Registered." );

        } catch( Exception e )
        {
            throw new RuntimeException( e );
        }
    }

    public void stop( BundleContext bundleContext )
        throws Exception
    {
        //  m_remoteOSGiActivator.stop( bundleContext );
        m_publisher.unbindAll();
        m_tracker.close();
       
    }
}
