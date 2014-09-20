set `date "+%d %h %Y %H %M"`

echo "Today it's the $1 $2 $3"

if [ $5 -lt 30 ]; then
    echo "It's $5 past $1"
elif [ $5 -gt 30 ]; then
    m_left=$((60-$5))
    echo "It's $m_left to $4"
else
    echo "It's half past $4"
fi
