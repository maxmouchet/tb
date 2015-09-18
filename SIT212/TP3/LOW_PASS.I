$ -------------------------------------------------------------
$ File     : Z:\MES DOCUMENTS\LOW_PASS.I
$ Schema file  : Z:\MES DOCUMENTS\LOW_PASS.N
$ Generated with APLAC Editor version 2.5.0
$ Mon Sep 07 11:43:36 2015
$ -------------------------------------------------------------



Cap C5 Port10 GND 
+ 6.57p
Cap C4 Port30 GND 
+ 6.57p
Ind L1 Port10 Port30 
+ 18.26n
DefNPort nport 2
+ Port10 GND 50
+ Port30 GND 50
Sweep "low_pass"
+ LOOP 1001 FREQ LIN 0G 5G
+ Window 0 Y "|Sij|" "dB" -40 0
+ Window 1 SMITH
Show
+ Window 0 Y MagdB(S(1,1)) Y MagdB(S(2,1))
+ Window 1 RI S(1,1) RI S(2,1)
EndSweep
