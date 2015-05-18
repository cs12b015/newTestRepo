#!/bin/bash  

#ssh root@45.55.210.12
#declaring varibles
USER="root"
IP="45.55.210.12"

echo "connecting to the $IP with $USER"

#ssh $USER@$IP


#chmod +x temp.sh
ssh $USER@$IP 'bash -s' < temp2.sh