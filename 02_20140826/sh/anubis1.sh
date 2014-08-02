#!/bin/bash

WORKDIR=tmp.anubis1
mkdir -p $WORKDIR
cat Data.csv | awk 'NR>1' | tr ',' '/' | xargs -I% echo mkdir -p $WORKDIR/% | sh

function each() {
    echo "<table border=1>"
    echo "<tr><th>都道府県</th><th>市</th><th>内訳</th><th>内訳2</th></tr>"
    echo "<tr>"
    ls $WORKDIR | while read L
    do
        spread $L $WORKDIR/$L
    done
    echo "</table>"
}

function spread() {
    #echo "--- find"
    #find "$2"
    echo "<td rowspan=\"`find "$2" -empty | wc -l`\">$1</td>"
    ls "$2" | while read L
    do
        spread $L "$2/$L"
        if [ `ls "$2/$L" | wc -l` -eq 0 ] ; then
            echo "</tr><tr>"
        fi
    done
}

cat <<++EOS
<html>
<body>
`each`
</body>
</html>
++EOS

