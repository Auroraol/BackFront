projectPath=$(cd `dirname $0`; pwd)

cd $projectPath

git pull &&
mysql  -h 127.0.0.1 -P 3306 -uroot -p123456  fileserver < sql/init.sql