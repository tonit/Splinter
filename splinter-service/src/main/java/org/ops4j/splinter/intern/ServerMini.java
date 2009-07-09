package org.ops4j.splinter.intern;

import org.ops4j.splinter.api.Mini;

/**
 * Created by IntelliJ IDEA.
 * User: tonit
 * Date: Jul 8, 2009
 * Time: 1:47:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerMini implements Mini
{

    public String getIdentification( String s )
    {
        return "Hello, " + s + " !";
    }
}
