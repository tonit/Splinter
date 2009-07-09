#!/bin/sh
#
# Splinter Endpoint.
# Uses Pax Runner for provisioning.
#
#

SCRIPTS=`readlink $0`
if [ "${SCRIPTS}" != "" ]
then
  SCRIPTS=`dirname $SCRIPTS`
else
  SCRIPTS=`dirname $0`
fi

java $JAVA_OPTS -cp .:$SCRIPTS:$SCRIPTS/pax-runner-1.1.1.jar org.ops4j.pax.runner.Run --args=file:splinter-endpoint.args scan-file:file:splinter-endpoint.bundles