@echo off

rem   __________________________________
rem  /                                  \
rem | USO: commitar "sua mensagem aqui"  |
rem  \__________________________________/

git pull
git add .
git commit -m %*
git push origin main