@echo off
setlocal

echo [INFO] Creating installer for App...

jpackage ^
  --type exe ^
  --input . ^
  --name ssam_client ^
  --main-jar App.jar ^
  --main-class com.aloha.Main ^
  --java-options "--module-path app/lib/javafx-sdk-23.0.2/lib --add-modules javafx.controls,javafx.fxml,javafx.media -Xmx512m -Djava.awt.headless=false" ^
  --icon icon_client.ico ^
  --win-dir-chooser ^
  --win-menu ^
  --win-shortcut

echo [INFO] Installer build complete.

pause
