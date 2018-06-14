#!/bin/sh
APPLICATION="ChainPlatform"
java -cp classes cid.tools.ManifestGenerator
/bin/rm -f ${APPLICATION}.jar
jar cfm ${APPLICATION}.jar resource/cid.manifest.mf -C classes . || exit 1
/bin/rm -f ${APPLICATION}service.jar
jar cfm ${APPLICATION}service.jar resource/cidservice.manifest.mf -C classes . || exit 1

echo "jar files generated successfully"