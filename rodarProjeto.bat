@echo off
cd "C:\Users\hugof\OneDrive\READET~1\CPIATR~1"

java ^
--module-path "C:\Users\hugof\Downloads\openjfx-24.0.1_windows-x64_bin-sdk\javafx-sdk-24.0.1\lib" ^
--add-modules javafx.controls,javafx.fxml ^
-cp "trablp3.jar;mysql-connector-j-9.3.0.jar" ^
Main

pause
