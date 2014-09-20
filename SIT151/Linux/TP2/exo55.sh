#!/bin/sh

grep $1 /etc/passwd > /dev/null

if test $? -eq 0
then
    echo "l'utilisateur $1 existe sur cette machine"
    exit 0
else
    echo "$1 n'est pas un utilisateur enregistre sur cette machine"
    exit 1
fi
