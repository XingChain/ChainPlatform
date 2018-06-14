#!/bin/sh

PATHSEP=":" 
if [ "$OSTYPE" = "cygwin" ] ; then
PATHSEP=";" 
fi

java -cp "classes${PATHSEP}lib/*${PATHSEP}conf" cid.tools.ConstantsExporter html/www/js/data/constants.js


