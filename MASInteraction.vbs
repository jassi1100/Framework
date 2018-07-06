OPTION EXPLICIT
On Error Resume Next

REM This line calls the macro subroutine
dim args
set args = Wscript.arguments
trigger
WScript.Quit(1)

sub trigger()
	Dim WshShell, strCurDir
	Set WshShell = CreateObject("WScript.Shell")
	strCurDir    = WshShell.CurrentDirectory
	Dim objExcel, objWorkbook, result
	Set objExcel = CreateObject("Excel.Application")
	Set objWorkbook = objExcel.Workbooks.Open(strCurDir & "\TestData\TestData.xlsm")
	result = objExcel.Application.Run("ExecuteTestcases", args(0),args(1),args(2),args(3))
	objWorkbook.Saved = True
	objWorkbook.Close
	
	if(result = "PASS") then
		WScript.Quit(0)
	else
		WScript.Quit(1)
	end if
	
end sub

