
### Robolectric tests with coverage inside Android studio

Run a single classes with coverage works but all together does fail. https://github.com/robolectric/robolectric/issues/3023

> java.lang.IllegalStateException: this method should only be called by Robolectric

On commandline these line fix this issue: `tasks.withType(Test) { jacoco.includeNoLocationClasses = true }`

In Android Studio try https://github.com/robolectric/robolectric/issues/3023#issuecomment-397166230

> In Android Studio to get rid of this error I have to append the -ea -noverify to the VMOptions in the EditConfigurations of RobolectricTests. Intellij Idea Code Coverage now success for all reboelectric tests in my case.


### Error log

```
java.lang.VerifyError: Bad return type
Exception Details:
  Location:
    android/content/res/ResourcesImpl.$$robo$$android_content_res_ResourcesImpl$loadComplexColorForCookie(Landroid/content/res/Resources;Landroid/util/TypedValue;ILandroid/content/res/Resources$Theme;)Landroid/content/res/ComplexColor; @565: areturn
  Reason:
    Type 'java/lang/Object' (current frame, stack[0]) is not assignable to 'android/content/res/ComplexColor' (from method signature)
  Current Frame:
    bci: @565
    flags: { }
    locals: { 'android/content/res/ResourcesImpl', 'android/content/res/Resources', 'android/util/TypedValue', integer, 'android/content/res/Resources$Theme', 'java/lang/Object', 'java/lang/String', 'java/lang/Object', 'android/content/res/XmlResourceParser', 'android/util/AttributeSet', integer, 'java/lang/String' }
    stack: { 'java/lang/Object' }
  Bytecode:
    0x0000000: 128a b800 903a 0519 0511 0437 b800 942c
    0x0000010: b401 b1c7 002a 1905 1104 38b8 0094 bb04
    0x0000020: 4159 bb01 2359 b701 2413 0443 b601 2a2c
    0x0000030: b402 b7b6 0187 b601 34b7 0444 bf19 0511
    0x0000040: 043c b800 942c b401 b1b9 01b2 0100 3a06
    0x0000050: 1905 1104 4db8 0094 013a 0719 0511 044f
    0x0000060: b800 9414 01d4 1906 b801 dd19 0511 0450
    0x0000070: b800 9419 0613 0391 b603 9599 0154 1905
    0x0000080: 1104 52b8 0094 2a19 061d 2cb4 01af 1304
    0x0000090: 46b6 0399 3a08 1905 1104 55b8 0094 1908
    0x00000a0: b804 4c3a 0919 0511 0457 b800 9419 08b9
    0x00000b0: 044f 0100 5936 0a05 9f00 0c15 0a04 9f00
    0x00000c0: 06a7 ffe4 1905 1104 5bb8 0094 150a 059f
    0x00000d0: 0016 1905 1104 5cb8 0094 bb03 d559 1304
    0x00000e0: 51b7 0452 bf19 0511 045f b800 9419 08b9
    0x00000f0: 0455 0100 3a0b 1905 1104 60b8 0094 190b
    0x0000100: 1304 57b6 019a 9900 1a19 0511 0461 b800
    0x0000110: 942b 1908 1909 1904 b804 5d3a 07a7 002a
    0x0000120: 1905 1104 62b8 0094 190b 1304 5fb6 019a
    0x0000130: 9900 1719 0511 0463 b800 942b 1908 1909
    0x0000140: 1904 b804 623a 0719 0511 0465 b800 9419
    0x0000150: 08b9 03a0 0100 1905 1104 6db8 0094 a700
    0x0000160: bf19 0511 0466 b800 943a 0819 0511 0467
    0x0000170: b800 9414 01d4 b802 6719 0511 0468 b800
    0x0000180: 94bb 0016 59bb 0123 59b7 0124 1301 b8b6
    0x0000190: 012a 1906 b601 2a13 0464 b601 2a1d 1905
    0x00001a0: 1104 6ab8 0094 b801 30b6 012a b601 34b7
    0x00001b0: 0137 3a09 1905 1104 6bb8 0094 1909 1908
    0x00001c0: b601 cc57 1905 1104 6cb8 0094 1909 bf19
    0x00001d0: 0511 046f b800 9414 01d4 b802 6719 0511
    0x00001e0: 0470 b800 94bb 0016 59bb 0123 59b7 0124
    0x00001f0: 1301 b8b6 012a 1906 b601 2a13 01ba b601
    0x0000200: 2a1d 1905 1104 72b8 0094 b801 30b6 012a
    0x0000210: 1304 2eb6 012a b601 34b7 0137 bf19 0511
    0x0000220: 0474 b800 9414 01d4 b802 6719 0511 0476
    0x0000230: b800 9419 07b0
  Exception Handler Table:
    bci [126, 342] => handler: 353
  Stackmap Table:
    append_frame(@61,Object[#4])
    full_frame(@165,{Object[#2],Object[#24],Object[#428],Integer,Object[#27],Object[#4],Object[#208],Null,Object[#927],Object[#1129]},{})
    append_frame(@196,Integer)
    same_frame(@229)
    append_frame(@288,Object[#208])
    full_frame(@327,{Object[#2],Object[#24],Object[#428],Integer,Object[#27],Object[#4],Object[#208],Object[#4],Object[#927],Object[#1129],Integer,Object[#208]},{})
    full_frame(@353,{Object[#2],Object[#24],Object[#428],Integer,Object[#27],Object[#4],Object[#208],Object[#4]},{Object[#335]})
    full_frame(@463,{Object[#2],Object[#24],Object[#428],Integer,Object[#27],Object[#4],Object[#208],Null},{})
    full_frame(@541,{Object[#2],Object[#24],Object[#428],Integer,Object[#27],Object[#4],Object[#208],Object[#4],Object[#927],Object[#1129],Integer,Object[#208]},{})


	at android.content.res.Resources.__constructor__(Resources.java:228)
	at android.content.res.Resources.<init>(Resources.java)
	at org.robolectric.shadows.ShadowResources.getSystem(ShadowResources.java:79)
	at android.content.res.Resources.getSystem(Resources.java)
	at org.robolectric.android.internal.ParallelUniverse.setUpApplicationState(ParallelUniverse.java:197)
	at org.robolectric.RobolectricTestRunner.beforeTest(RobolectricTestRunner.java:377)
	at org.robolectric.internal.SandboxTestRunner$2.evaluate(SandboxTestRunner.java:252)
	at org.robolectric.internal.SandboxTestRunner.runChild(SandboxTestRunner.java:130)
	at org.robolectric.internal.SandboxTestRunner.runChild(SandboxTestRunner.java:42)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.robolectric.internal.SandboxTestRunner$1.evaluate(SandboxTestRunner.java:84)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runners.Suite.runChild(Suite.java:128)
	at org.junit.runners.Suite.runChild(Suite.java:27)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)

[Robolectric] com.template.project.data.local.TodoDaoTest.insert list on duplicate error: sdk=28; resources=legacy
[Robolectric] NOTICE: legacy resources mode is deprecated; see http://robolectric.org/migrating/#migrating-to-40

java.lang.IllegalStateException: this method should only be called by Robolectric

	at org.robolectric.shadows.ShadowDisplayManager.configureDefaultDisplay(ShadowDisplayManager.java:43)
	at org.robolectric.android.Bootstrap.setUpDisplay(Bootstrap.java:18)
	at org.robolectric.android.internal.ParallelUniverse.setUpApplicationState(ParallelUniverse.java:194)
	at org.robolectric.RobolectricTestRunner.beforeTest(RobolectricTestRunner.java:377)
	at org.robolectric.internal.SandboxTestRunner$2.evaluate(SandboxTestRunner.java:252)
	at org.robolectric.internal.SandboxTestRunner.runChild(SandboxTestRunner.java:130)
	at org.robolectric.internal.SandboxTestRunner.runChild(SandboxTestRunner.java:42)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.robolectric.internal.SandboxTestRunner$1.evaluate(SandboxTestRunner.java:84)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runners.Suite.runChild(Suite.java:128)
	at org.junit.runners.Suite.runChild(Suite.java:27)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
```
