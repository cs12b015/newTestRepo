#!/bin/bash  
#declaring varibles
USER1="root"
IP[0]="45.55.210.12"
IP[1]="23.22.221.119"

for i in ${IP[@]}; do
	echo "connecting to the $i with $USER1"
	ssh $USER1@$IP 'bash -s' < temp2.sh
done