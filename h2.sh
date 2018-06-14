#!/bin/sh
java -cp lib/h2*.jar org.h2.tools.Shell -url jdbc:h2:./cid_db/cid -user sa -password sa
