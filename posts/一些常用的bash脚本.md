---
title: sh
date: 2020-12-02 17:15:54
tags:
    - shell
cover: https://i.loli.net/2021/02/18/o7jSzVZ4n9RBl5I.jpg
---


### 查询大文件
```shell
find . -type f -size +800M
```
### 大文件路径
```shell
find . -type f -size +800M  -print0 | xargs -0 ls -l
```

### 大文件详细大小
```shell
find . -type f -size +800M  -print0 | xargs -0 du -h
```

### 文件大小排序
```shell
find . -type f -size +100M  -print0 | xargs -0 du -h | sort -nr
```

### 查看项目占用内存
```shell
ps aux | grep *.jar | grep -v grep | awk '{print $11 "\t" $6/1024"MB" }'
```

### 端口使用情况
```shell
ss -nplt
```

### 替换文件信息
```shell
sed -i s/{old}/{new}/g `grep {old} -rl ./`
```

### 查找文件中出现的次数
```shell
cat info.log |grep ${search} |wc -l
```

### 查看linux系统journal日志使用
```shell
journalctl --disk-usage
```

### mysql处理bin_log文件过大
```sql
show variables like 'expire_logs_days';  

show variables like 'max_binlog_size';  

set global expire_logs_days = 7;

flush logs;  -- clean log files

show master logs
```

### 日期打印
```bash
echo $(date "+%Y-%m-%d %H:%M:%S")
```

### Mac/linux查看网络交互

```bash
sudo tcpdump -i en0 -n port 80 -vv
```

### 查询网络流量消耗大的进程

```bash
sudo nethogs -d 1 -v 4 -l
```

