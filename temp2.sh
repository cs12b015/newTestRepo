#!/bin/bash  
PATH1="/home/code/newTestRepo"
echo "logged in"
cd $PATH1
PWD1=`pwd`
echo "present working directory is $PWD1"
PRESENT_IP=`ifconfig | grep 'inet addr:' | grep -v '127.0.0.1' | awk '{print $2}' `
git pull || { echo "process stopped working ar $PRESENT_IP" ; exit 1;}
echo "git pulling finnished"