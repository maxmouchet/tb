#!/bin/bash

TIME=/usr/bin/time
FLAGS=-f%U

# Use gnu-time on OSX
if [[ $OSTYPE == darwin* ]]; then
    TIME=/usr/local/bin/gtime
fi

if [ $# -lt 2 ]; then
    echo "Usage: $0 ALGORITHM FILES..."
    exit 1
fi

ALGORITHM=$1
shift

printf "%-15s%-10s%s\n" File Words Seconds

for file in $@; do
    printf "%-15s" $file
    printf "%-10s" `wc -w $file | grep -Eo '\d+\s'`
    $TIME $FLAGS $ALGORITHM $file >/dev/null
done
