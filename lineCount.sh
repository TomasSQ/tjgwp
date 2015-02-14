find -name '*.*' | egrep -v '\.settings|target|eclipse|git|img|plugins|\.project' | xargs wc -l
