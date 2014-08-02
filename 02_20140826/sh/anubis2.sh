#!/bin/bash

WORKDIR=tmp.anubis2
mkdir -p $WORKDIR
cat Data.csv | awk -F"," 'NR>1{print $1"/"$2"/"$4":"$3}' | xargs -I% echo mkdir -p $WORKDIR/% | sh

function each() {
    ls $WORKDIR | while read L
    do
        echo "<h1>$L</h1>"
        echo "<table border=1>"
        echo "<tr><th>市</th><th>内訳</th><th>ルーター</th><th>ホスト名</th></tr>"
        echo "<tr>"
        ls $WORKDIR/$L | while read M
        do
            spread $M $WORKDIR/$L/$M
        done
        echo "</table>"
    done
}

function spread() {
    echo "<td rowspan=\"`find "$2" -empty | wc -l`\">$1</td>"
    ls "$2" | while read L
    do
        spread $L "$2/$L"
        if [ `ls "$2/$L" | wc -l` -eq 0 ] ; then
        echo $L | awk -F":" '{print "<td>"$2"</td>""<td>"$1"</td>"}'
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

