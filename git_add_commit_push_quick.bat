REM For use in all project sandboxes.
REM Save this in the project's root directory,
REM with the name:
REM    git_add_commit_push_no_msg.bat


set branch=%1
set commit_msg="Quick save (no message)"

REM ECHO About to do
REM ECHO 1.  git add --all :/
REM ECHO 2.  git commit -m "%commit_msg%"
REM ECHO 3.  git push -u origin %branch%
REM PAUSE Press a key to proceed.

git add --all :/
REM PAUSE
git commit -m %commit_msg%
REM PAUSE
git push -u origin %branch%
