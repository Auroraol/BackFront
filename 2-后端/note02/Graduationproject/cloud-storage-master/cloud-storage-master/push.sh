projectPath=$(cd `dirname $0`; pwd)

cd $projectPath

mysqldump  -h 127.0.0.1 -P 3306 -uroot -p123456  fileserver > sql/init.sql &&
git commit -am "备份数据库" &&
git push