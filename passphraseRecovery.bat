@REM Attempt to recover mis-typed passphrase
@echo *********************************************************************
@echo * Use this batch file to search for a lost passphrase.              *
@echo *********************************************************************

if exist jre ( 
    set javaDir=jre\bin\
)

%javaDir%java.exe -Xmx1024m -cp "classes;lib/*;conf" -Dcid.runtime.mode=desktop cid.tools.PassphraseRecovery
