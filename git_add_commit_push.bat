REM For use in all project sandboxes.
REM Save this in the project's root directory,
REM with the name:
REM    git_add_commit_push.bat

REM call z_backup_..._data_folder_delete_existing_first.bat

;set branch=%1
;set commit_msg=%2
set branch=master
set commit_msg=%1

ECHO The commit message is the one and only command-line parameter.
ECHO About to do
ECHO 1.  git add --all :/
ECHO 2.  git commit -m "%commit_msg%"
ECHO 3.  git push -u origin %branch%
REM PAUSE Press a key to proceed.

git add --all
REM PAUSE
git commit -m %commit_msg%
REM PAUSE
git push -u origin %branch%
