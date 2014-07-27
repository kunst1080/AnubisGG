#!/bin/bash

WORKDIR=anubis

mkdir -p $WORKDIR
cat Data.csv | awk 'NR>1' | tr ',' '/' | xargs -I% echo mkdir -p $WORKDIR/% | sh

function spread() {
    echo "<td rowspan=\"`find "$2" | wc -l`\">$1</td>"
    ls "$2" | while read L
    do
        spread $L "$2/$L"
        if [ `ls "$2" | wc -l` ] ; then
            echo "</tr><tr>"
        fi
    done
}

cat <<++EOS
<html>
<body>
<table border=1>
<tr>
`spread $WORKDIR $WORKDIR`
</table>
</body>
</html>
++EOS

