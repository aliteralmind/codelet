REM For use in all project sandboxes.
REM Save this in the project's root directory,
REM with the name:
REM    git_add_commit_push.bat

set do_prompt_for_commit_msg=%1
set branch=%2
set commit_msg_file=C:\Users\aliteralmind\Desktop\zz_ONE_LINE_git_commit_message_DO_NOT_DELETE.txt

if %do_prompt_for_commit_msg% == do_not_prompt_for_commit_msg GOTO postprompt
PAUSE About to open %commit_msg_file%. Enter the commit message there, then press a key to proceed (press a key now to open the file).
%commit_msg_file%
PAUSE Enter commit message, then press a key to proceed.
:postprompt

set /p commit_msg=<%commit_msg_file%

REM ECHO Two required parameters: "do[_not]_prompt_for_commit_msg", branch name. The commit message comes from
REM ECHO %commit_msg_file%
REM ECHO About to do
REM ECHO 1.  git add --all :/
REM ECHO 2.  git commit -m "%commit_msg%"
REM ECHO 3.  git push -u origin %branch%
REM PAUSE Press a key to proceed.

git add --all :/
REM PAUSE
git commit -m "%commit_msg%"
REM PAUSE
git push -u origin %branch%
