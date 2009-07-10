package org.ops4j.splinter.api;

import java.io.Serializable;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.Version;
import org.osgi.service.deploymentadmin.BundleInfo;
import org.osgi.service.deploymentadmin.DeploymentPackage;

/**
 * DeploymentPackage adapter to be used as a remote copy.
 */
public class ExternDeploymentPackage implements DeploymentPackage, Serializable
{

    private boolean m_isStale;
    private String m_name;

    public ExternDeploymentPackage( DeploymentPackage deploymentPackage )
    {
        // read all data so object can be transfered safely.
        m_isStale = deploymentPackage.isStale();
        m_name = deploymentPackage.getName();
    }

    public boolean isStale()

    {
        return m_isStale;
    }

    public String getName()

    {
        return m_name;
    }

    public Version getVersion()

    {
        return null;
    }

    public BundleInfo[] getBundleInfos()

    {
        return new BundleInfo[0];
    }

    public Bundle getBundle( String s )

    {
        return null;
    }

    public String[] getResources()

    {
        return new String[0];
    }

    public ServiceReference getResourceProcessor( String s )

    {
        return null;
    }

    public String getHeader( String s )

    {
        return null;
    }

    public String getResourceHeader( String s, String s1 )

    {
        return null;
    }

    public void uninstall()
    {

    }

    public boolean uninstallForced()
    {
        return false;
    }
}
