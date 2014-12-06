#!/bin/bash

TIME=/usr/bin/time
FLAGS=-f%U

# Use gnu-time on OSX
# brew install gnu-time
if [[ $OSTYPE == darwin* ]]; then
    TIME=/usr/local/bin/gtime
fi

if [ $# -lt 2 ]; then
    echo "Usage: $0 ALGORITHM FILES..."
    exit 1
fi

ALGORITHM=$1
shift

OUTPUT_FILE=$ALGORITHM".txt"
echo '' > $OUTPUT_FILE

printf "%-15s%-10s%s\n" File Words Seconds

for file in $@; do
    word_count=`wc -w $file | grep -Eo '\d+\s'`
    running_time=$( { $TIME $FLAGS $ALGORITHM $file >/dev/null; } 2>&1 )
    
    printf "%-15s" $file
    printf "%-10s" $word_count
    printf "%s\n" $running_time

    echo "$word_count $running_time" >> $OUTPUT_FILE
done
