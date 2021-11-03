#!/bin/bash
cd bin
for i in {1..500}
do
    java PowerBSTApp TestFiles/test$i.txt
done
