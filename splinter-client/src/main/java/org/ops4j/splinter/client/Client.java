package org.ops4j.splinter.client;

import java.io.InputStream;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.deploymentadmin.DeploymentAdmin;
import org.ops4j.splinter.api.Mini;
import org.ops4j.pax.swissbox.tinybundles.dp.store.BinaryStore;
import org.ops4j.pax.swissbox.tinybundles.dp.store.TemporaryBinaryStore;
import org.ops4j.pax.swissbox.tinybundles.dp.store.BinaryHandle;
import static org.ops4j.pax.swissbox.tinybundles.dp.DP.*;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jul 8, 2009
 * Time: 10:35:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client
{

    private BundleContext m_context;
    private ServiceTracker m_miniTracker;
    private ServiceTracker m_deploymentadminTracker;

    public Client( final BundleContext context )
    {
        m_context = context;
        m_miniTracker = new ServiceTracker( context, Mini.class.getName(), null );
        m_miniTracker.open();
        m_deploymentadminTracker = new ServiceTracker( context, DeploymentAdmin.class.getName(), null );
        m_miniTracker.open();
        m_deploymentadminTracker.open();

    }

    /**
     * Blocking call to do X
     */
    public void doX()
    {
        try
        {
            System.out.println( "Mini: " + getMini().getIdentification( "Toni" ) );

            DeploymentAdmin deploymentAdmin = getDeploymentAdmin();

            BinaryStore<InputStream> cache = new TemporaryBinaryStore();
            BinaryHandle original = cache.store( newDeploymentPackage()
                .setSymbolicName( "MyFirstDeploymentPackage" )
                .setVersion( "1.0.0" )
                .setBundle( "t2", "mvn:org.ops4j.pax.web/pax-web-service/0.6.0" )
                .build()
            );

            System.out.println( "admin: " + deploymentAdmin );
            deploymentAdmin.installDeploymentPackage( cache.load( original ) );

        } catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    private DeploymentAdmin getDeploymentAdmin()
    {
        try
        {
            return (DeploymentAdmin) m_deploymentadminTracker.waitForService( 10000 );
        } catch( InterruptedException e )
        {
            System.out.println( "interrupted" );
        }
        System.out.println( "After 10 secs still no dp" );

        return null;
    }

    private Mini getMini()
    {
        try
        {
            return (Mini) m_miniTracker.waitForService( 10000 );
        } catch( InterruptedException e )
        {
        }
        System.out.println( "After 10 secs still no mini" );

        return null;
    }

    public void close()
    {
        m_miniTracker.close();
        m_deploymentadminTracker.close();
    }
}
