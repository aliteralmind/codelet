REM For use in all project sandboxes.
REM Save this in the project's root directory,
REM with the name:
REM    git_add_all_commit_push.bat

set branch=%1
set commit_msg_file=C:\Users\aliteralmind\Desktop\zz_ONE_LINE_git_commit_message_DO_NOT_DELETE.txt
;set commit_msg=%2
set /p commit_msg=<%commit_msg_file%

ECHO The branch name is the one and only command-line parameter. The commit message comes from
ECHO %commit_msg_file%
ECHO About to do
ECHO git add --all :/
ECHO git commit -m "%commit_msg%"
ECHO git push -u origin %branch%
PAUSE Press a key to proceed.

git add --all :/
PAUSE
git commit -m "%commit_msg%"
PAUSE
git push -u origin %branch%
