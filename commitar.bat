rem   __________________________________
rem  /                                  \
rem | USO: commitar "sua mensagem aqui"  |
rem  \__________________________________/

@echo off
git add .
git commit -m %*
git push origin master