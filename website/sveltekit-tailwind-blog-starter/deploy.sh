#!/bin/bash

set -e -x

./build.sh

local_path="./archive" # 本地目录路径

remote_user="root"
remote_host="oursweetlife.site"
remote_path="/opt/blog"

# todo first
#ln -s /usr/local/lib/node/nodejs/bin/pm2 /usr/local/bin/ # avoid error: pm2: command not found
#ln -s /usr/local/lib/node/nodejs/bin/node /usr/local/bin/ # avoid error:  /usr/bin/env: ‘node’: No such file or directory
# stop the old server process
ssh "${remote_user}@${remote_host}" "cd ${remote_path} && pm2 stop ./build/index.js"

# delete the old files
ssh "${remote_user}@${remote_host}" "rm -rf ${remote_path}"

# upload files
scp -r "${local_path}/" "${remote_user}@${remote_host}:${remote_path}"

# start the new server
ssh "${remote_user}@${remote_host}" "cd ${remote_path} && pm2 start ./build/index.js"

#forever start ./build/index.js
echo 'successfully started'
