echo "start"
echo "BootCompSys: `adb shell getprop sys.boot_completed`"
echo "BootCompDev: `adb shell getprop dev.bootcomplete`"
echo "BootAnim: `adb shell getprop init.svc.bootanim`"
adb shell input keyevent 82
echo "ende"