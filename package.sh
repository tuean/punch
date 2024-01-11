set -o errexit

echo "front package build start"
cd ./punchs
npm run build
echo "front package build finish"

echo "apt compile & install start"
cd ../punch-apt
mvn compile install
echo "apt compile & install finish"

echo "punch compile & package start"
cd ../support
mvn compile package
echo "punch compile & package finish"

echo "zip files start"
cd ..
tar -czvf punch.tar.gz ./punchs/dist ./support/target/punch.jar
echo "zip files finish"


