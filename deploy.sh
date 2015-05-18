#!/bin/bash  
#declaring varibles
USER1="root"
IP[0]="45.55.210.12"
IP[1]="45.55.207.62"
PATH1[0]="/home/code/newTestRepo"
PATH1[1]="/home/code1/newTestRepo"

COUNTER=0
for i in ${IP[@]}; do
	echo "connecting to the $i with $USER1"
	echo "PATH1=${PATH1[$COUNTER]}" > temp1.sh
	cat temp2.sh >> temp1.sh
	ssh $USER1@$i 'bash -s' < temp1.sh
	let COUNTER=COUNTER+1
	rm -rf temp1.sh
done