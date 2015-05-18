#!/bin/bash  
PATH1="/home/code/newTestRepo"
echo "logged in"
cd $PATH1
PWD1=`pwd`
echo "present working directory is $PWD1"
git pull
echo "git pulling finnished"