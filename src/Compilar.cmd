REM set path=%path%;D:\Program Files (x86)\Java\jdk1.8.0_60\bin
javac Model/*.java Controller/*java
jar cvf Libreria.jar Model/*.class Controller/*.class
javac -cp Libreria.jar View/*.java
jar cvfm Juego_Cuy.jar MANIFEST.MF View/*.class *.txt
