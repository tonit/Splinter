package org.ops4j.splinter.api;

import java.io.InputStream;
import java.util.Date;
import org.osgi.framework.Bundle;
import org.osgi.service.deploymentadmin.DeploymentAdmin;
import org.osgi.service.deploymentadmin.DeploymentException;
import org.osgi.service.deploymentadmin.DeploymentPackage;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Remote DeploymentAdmin implementation.
 * Each return value will be translated to a immutable value object (serializable)
 */
public class ExternDeploymentAdminImpl implements DeploymentAdmin
{

    private ServiceTracker m_tracker;
    private final Date m_started;

    public ExternDeploymentAdminImpl( final ServiceTracker tracker )
    {
        // once deploymentadmin arives, we need to wire it to extern
        m_tracker = tracker;
        m_started = new Date();
        System.out.println( "External Wrapper created on " + m_started );
    }

    public DeploymentPackage installDeploymentPackage( InputStream inputStream )
        throws DeploymentException

    {
        return new ExternDeploymentPackage( ( getDeploymentAdmin() ).installDeploymentPackage( inputStream ) );

    }

    public DeploymentPackage[] listDeploymentPackages()
    {
        DeploymentPackage[] deploymentPackages = ( getDeploymentAdmin().listDeploymentPackages());
        // wrap each to new extern array:
        for( int i = 0; i < deploymentPackages.length; i++ )
        {
            deploymentPackages[ i ] = new ExternDeploymentPackage( deploymentPackages[ i ] );
        }
        return deploymentPackages;

    }

    public DeploymentPackage getDeploymentPackage( String s )
    {
        return new ExternDeploymentPackage( getDeploymentAdmin().getDeploymentPackage( s ) );
    }

    public DeploymentPackage getDeploymentPackage( Bundle bundle )
    {
        return new ExternDeploymentPackage( getDeploymentAdmin().getDeploymentPackage( bundle ) );
    }

    public boolean cancel()
    {
        return getDeploymentAdmin().cancel();
    }

    private DeploymentAdmin getDeploymentAdmin()
    {
        return (DeploymentAdmin) m_tracker.getService();
    }
}
