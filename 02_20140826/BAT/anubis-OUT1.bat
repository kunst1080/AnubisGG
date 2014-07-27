set OUTPUT=out1.html
del %OUTPUT%
(
	call :OUTPUT-HTML-START •\1
	echo ^<table border=1^>
	echo ^<tr^>^<th^>Œ§^</th^>^<th^>Žs^</th^>^<th^>“à–ó^</th^>^</tr^>
	for /f "usebackq tokens=*" %%a in (`dir %WORKDIR% /b`) do call :OUTPUT-EACH1-1 %%a
	echo ^</table^>
	call :OUTPUT-HTML-END
)>>%OUTPUT%

goto :eof


:OUTPUT-EACH1-1
	for /f "usebackq tokens=*" %%a in (`findstr .* "%WORKDIR%\%1\*" ^| find /v /c "" `) do set ROWSPAN=%%a
	echo ^<tr^>
	echo ^<td rowspan=%ROWSPAN%^>%1^</td^>
	for /f "usebackq tokens=*" %%a in (`dir %WORKDIR%\%1 /b`) do call :OUTPUT-EACH1-2 %%a %WORKDIR%\%1\%%a
exit /b

:OUTPUT-EACH1-2
	for /f "usebackq tokens=*" %%a in (`type %2 ^| find /v /c "" `) do set ROWSPAN=%%a
	echo ^<td rowspan=%ROWSPAN%^>%1^</td^>
	for /f "usebackq delims=, tokens=1,2" %%a in (`type %2`) do call :OUTPUT-EACH1-3 %%a %%b
exit /b

:OUTPUT-EACH1-3
	echo ^<td^>%2:%1^</td^>
	echo ^</tr^>
	echo ^<tr^>
exit /b


:OUTPUT-HTML-START
	set TITLE=%~1
	echo ^<html^>
	echo ^<head^>
	echo ^<title^>%TITLE%^</title^>
	echo ^<body^>
exit /b

:OUTPUT-HTML-END
	echo ^</body^>
	echo ^</html^>
exit /b
