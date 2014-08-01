cd /d %~dp0

if not exist bin mkdir bin
set _JAVA_OPTIONS=-Dfile.encoding=UTF-8
javac -d bin anubisgg2\*
type Data.csv | java -cp bin anubisgg2.AnubisLogic "1" > out1.html
type Data.csv | java -cp bin anubisgg2.AnubisLogic "2" > out2.html

pause
