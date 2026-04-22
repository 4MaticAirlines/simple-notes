@ECHO OFF
SET DIR=%~dp0
SET WRAPPER_JAR=%DIR%gradle\wrapper\gradle-wrapper.jar
IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO gradle-wrapper.jar not found. Open the project in Android Studio to regenerate the wrapper.
  EXIT /B 1
)
"%JAVA_HOME%in\java.exe" -jar "%WRAPPER_JAR%" %*
