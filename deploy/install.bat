@echo off
setlocal

echo [INFO] Creating installer for App...

jpackage ^
  --type exe ^
  --input . ^
  --name ssam ^
  --main-jar App.jar ^
  --main-class com.aloha.Main ^
  --java-options "--module-path app/lib/javafx-sdk-23.0.2/lib --add-modules javafx.controls,javafx.fxml -Xmx512m -Djava.awt.headless=false" ^
  --icon icon.ico ^
  --win-dir-chooser ^
  --win-menu ^
  --win-shortcut

echo [INFO] Installer build complete.

pause
