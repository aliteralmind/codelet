REM For use in all project sandboxes.
REM Save this in the project's root directory,
REM with the name:
REM    git_add_all_commit_push.bat

set branch=%1
set commit_msg=%2
PAUSE About to do: 'git add --all', 'git commit -m %commit_msg%', 'git push -u origin %branch%'. The branch name is the first command-line parameter, the commit message is the second. Press a key to proceed.
git add --all
PAUSE
git commit -m %commit_msg%
PAUSE
git push -u origin %branch%
