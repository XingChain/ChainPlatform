java -cp classes cid.tools.ManifestGenerator
del cid.jar
jar cfm cid.jar resource\cid.manifest.mf -C classes .
del cidservice.jar
jar cfm cidservice.jar resource\cidservice.manifest.mf -C classes .

echo "jar files generated successfully"