@echo off
set /p sqlname= ����Ҫ���ɵ�sql�ļ���������.sql��׺��::::::::::
echo.
php ./PhalApi/phalapi-buildsqls ./Config/dbs.php %sqlname%
pause