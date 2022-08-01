@echo off

echo build all go modules to build/godemo.aar

gomobile bind -ldflags "-s -w" -v -target android -javapkg me.izzp.godemo.go -o build/godemo.aar goandroidmoddemo/ghttp goandroidmoddemo/gcodec
