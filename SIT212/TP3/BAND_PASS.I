$ -------------------------------------------------------------
$ File     : Z:\MES DOCUMENTS\BAND_PASS.I
$ Schema file  : Z:\MES DOCUMENTS\BAND_PASS.N
$ Generated with APLAC Editor version 2.5.0
$ Mon Sep 07 11:49:56 2015
$ -------------------------------------------------------------



Ind L1 Port20 GND 
+ 15.82n
Cap C1 Port20 GND 
+ 328.36p
Cap C2 Port20 node1 
+ 5.69p
Ind L2 node1 Port10 
+ 913.07n
Ind L3 Port10 GND 
+ 15.82n
Cap C3 Port10 GND 
+ 328.36p
DefNPort nport 2
+ Port10 GND 50
+ Port20 GND 50
Sweep "band_pass"
+ LOOP 1001 FREQ LIN 0G 5G
+ Window 0 Y "|Sij|" "dB" -40 0
+ Window 1 SMITH
Show
+ Window 0 Y MagdB(S(1,1)) Y MagdB(S(2,1))
+ Window 1 RI S(1,1) RI S(2,1)
EndSweep
