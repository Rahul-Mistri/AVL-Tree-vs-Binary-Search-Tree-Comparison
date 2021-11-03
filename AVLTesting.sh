#!/bin/bash
cd bin
for i in {1..500}
do
    java PowerAVLApp TestFiles/test$i.txt
done
