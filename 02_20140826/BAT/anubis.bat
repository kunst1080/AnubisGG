@echo off

set WORKDIR=%~dp0wk
set HEAD=1

rem init
rd /s /q "%WORKDIR%"
mkdir "%WORKDIR%"

rem input
for /f "usebackq delims=, tokens=1,2,3,4" %%a in ("Data.sjis.csv") do call :READ-EACH %%a %%b %%c %%d

rem output1
call %~dp0anubis-OUT1.bat

rem output2
call %~dp0anubis-OUT2.bat

pause
goto :eof

:READ-EACH
	if "%HEAD%"=="1" set HEAD=0 && exit /b
	if not exist "%WORKDIR%\%1" mkdir "%WORKDIR%\%1"
	echo %3,%4>>"%WORKDIR%\%1\%2"
exit /b
