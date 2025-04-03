@echo off

rem   __________________________________
rem  /                                  \
rem | USO: commitar "sua mensagem aqui"  |
rem  \__________________________________/

git add .
git commit -m %*
git push origin master