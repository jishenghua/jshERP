@echo off

title jshERP

java -Xms1000m -Xmx2000m -cp .\conf;.\lib\*; -XX:+CreateMinidumpOnCrash com.jsh.erp.ErpApplication
pause over