package org.ops4j.splinter.api;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import org.osgi.framework.Bundle;
import org.osgi.service.deploymentadmin.DeploymentAdmin;
import org.osgi.service.deploymentadmin.DeploymentException;
import org.osgi.service.deploymentadmin.DeploymentPackage;
import org.osgi.util.tracker.ServiceTracker;

/**
 *
 */
public class ExternDeploymentAdminImpl implements DeploymentAdmin, Serializable
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

    {
        try
        {
            System.out.println( "-----------------------------------------installing.." );
            DeploymentPackage aPackage = new ExternDeploymentPackage( ( (DeploymentAdmin) m_tracker.getService() ).installDeploymentPackage( inputStream ) );
            System.out.println( "Done. DP is " + aPackage );
            return aPackage;
        } catch( DeploymentException e )
        {
            throw new RuntimeException( e );
        }
    }

    public DeploymentPackage[] listDeploymentPackages()
    {
        return null;
    }

    public DeploymentPackage getDeploymentPackage( String s )
    {
        return null; //( (DeploymentAdmin) m_tracker.getService() ).getDeploymentPackage( s );
    }

    public DeploymentPackage getDeploymentPackage( Bundle bundle )
    {
        return null; //( (DeploymentAdmin) m_tracker.getService() ).getDeploymentPackage( bundle );
    }

    public boolean cancel()
    {
        return false; // ( (DeploymentAdmin) m_tracker.getService() ).cancel();
    }
}
