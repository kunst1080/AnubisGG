set OUTPUT=out2.html
del %OUTPUT%
(
	call :OUTPUT-HTML-START 表2
	for /f "usebackq tokens=*" %%a in (`dir %WORKDIR% /b`) do call :OUTPUT-EACH2-1 %%a
	call :OUTPUT-HTML-END
)>>%OUTPUT%

goto :eof


:OUTPUT-EACH2-1
	echo ^<h1^>%1^</h1^>
	echo ^<table border=1^>
	echo ^<tr^>^<th^>市^</th^>^<th^>内訳^</th^>^<th^>ルーター^</th^>^<th^>ホスト名^</th^>^</tr^>
	echo ^<tr^>
	for /f "usebackq tokens=*" %%a in (`dir %WORKDIR%\%1 /b`) do call :OUTPUT-EACH2-2 %%a %WORKDIR%\%1\%%a
	echo ^</table^>
exit /b

:OUTPUT-EACH2-2
	for /f "usebackq tokens=*" %%a in (`type %2 ^| find /v /c "" `) do set ROWSPAN=%%a
	echo ^<td rowspan=%ROWSPAN%^>%1^</td^>
	for /f "usebackq delims=, tokens=1,2" %%a in (`type %2`) do call :OUTPUT-EACH2-3 %%a %%b
exit /b

:OUTPUT-EACH2-3
	echo ^<td^>%2:%1^</td^>
	echo ^<td^>%1^</td^>
	echo ^<td^>%2^</td^>
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
