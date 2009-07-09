package org.ops4j.splinter.api;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jul 8, 2009
 * Time: 1:31:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Mini
{

    String getIdentification( String s )
        throws RemoteException;
}
