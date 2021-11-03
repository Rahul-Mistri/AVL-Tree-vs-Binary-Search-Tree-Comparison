#!/bin/bash

for i in {1..500}
do
    tail -n +2 ./bin/cleaned_data.csv | head -n $i > ./bin/TestFiles/test$i.txt
done
