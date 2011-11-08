flutelator is a part of ua.ho.gloryofrobots package
so you must create   
	path_to_you_folder/src/ua/ho/gloryofrobots/flutelator  
	path_to_you_folder/bin/ua/ho/gloryofrobots/flutelator  

build and run commands examples:

$cd path_to_you_folder


compiling

$javac -d   bin  -Xlint  -sourcepath src      src/ua/ho/gloryofrobots/flutelator/Flutelator.java
$javac -d bin -sourcepath src   src/ua/ho/gloryofrobots/flutelator/MakeFlute.java


running
$java   -classpath bin    ua.ho.gloryofrobots.flutelator.MakeFlute
$java   -classpath bin    ua.ho.gloryofrobots.flutelator.Flutelator

or you can change source code and directory structure
