@echo off

REM JavaFX 라이브러리 경로 설정
set FX_LIB=lib\javafx-sdk-23.0.2\lib

REM JavaFX 애플리케이션 실행
start javaw --module-path "%FX_LIB%" ^
     --add-modules javafx.controls,javafx.fxml ^
     -jar App.jar

exit