@echo off
set comp_flag = 0
set doc_flag = 0
set exec_flag = 0
for %%i in (%*) do (
    if %%i == -comp (
        set /A comp_flag = 1
    )
    if %%i == -javadoc (
        set /A doc_flag = 1
    )
    if %%i == -exec (
        set /A exec_flag = 1
    )
)
@echo on
if [%doc_flag%] == [1] javadoc src\application\java\ru\nsu\fit\lylova\*.java -d for_javadoc
if [%comp_flag%] == [1] javac src\application\java\ru\nsu\fit\lylova\*.java -d for_build
cd for_build
if [%exec_flag%] == [1] java ru.nsu.fit.lylova.ru.nsu.fit.lylova.Application
 
