@ECHO OFF
ECHO Congratulations! Your first batch file executed successfully.
cd server
cd public
php -S 127.0.0.1:8000
PAUSE