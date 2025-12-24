@echo off
echo Compiling and running Student Record System...
echo.

cd /d "%~dp0"

where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Maven not found in PATH.
    echo Please add Maven to your PATH or run from your IDE.
    echo.
    echo To add Maven:
    echo 1. Download Maven from https://maven.apache.org/download.cgi
    echo 2. Extract to C:\Program Files\Apache\maven
    echo 3. Add C:\Program Files\Apache\maven\bin to your PATH
    pause
    exit /b 1
)

mvn clean javafx:run

pause

