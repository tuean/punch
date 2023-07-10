#!/bin/bash

# set -e
set -o errexit

rm -rf ./archive

mkdir archive

npm run build

cp package.json ./archive
cp -R ./build ./archive

