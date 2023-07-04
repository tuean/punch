#!/bin/bash

# 本地目录路径
local_path="./archive"

# 远程服务器信息
remote_user="tuean"
remote_host="oursweetlife.site"
remote_path="/opt/blog"

# 删除远程目录
ssh "${remote_user}@${remote_host}" "rm -rf ${remote_path}"

# 将本地目录上传至远程服务器
#rsync -rltDvz --delete "${local_path}/" "${remote_user}@${remote_host}:${remote_path}"
scp -r "${local_path}/" "${remote_user}@${remote_host}:${remote_path}"

# 在远程服务器上执行命令
ssh "${remote_user}@${remote_host}" "cd ${remote_path} && node build"

forever start ./build/index.js

