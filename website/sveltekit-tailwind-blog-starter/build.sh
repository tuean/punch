#!/bin/bash

rm -rf ./archive

mkdir archive

npm run build

cp package.json ./archive
cp -R ./build ./archive

