find -name '*.*' | egrep -v '\.settings|target|eclipse|git|img|plugins' | xargs wc -l
