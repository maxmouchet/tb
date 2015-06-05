close all;
clear all;

FIR
Fe = 16000;
Fc = 2000;

wn = Fc/(Fe/2);

N = 19;

h_8_ham = fir1(N, wn, hamming(N+1));
[H_8_Ham, f] = freqz(h_8_ham, 1, 512, 'whole', Fe);

h_8_box = fir1(N, wn, boxcar(N+1))
[H_8_Box, g] = freqz(h_8_box, 1, 512, 'whole', Fe)

figure('Color',[1 1 1]);

subplot(2,2,1)
plot((f/(2*pi))-(1/2), abs(H_8_Ham))
title('N = 19, Hamming. Module de la reponse frequentielle')

subplot(2,2,2)
plot((f/(2*pi))-(1/2), 20*log10(abs(H_8_Ham)))
title('N = 19, Hamming. Module de la reponse frequentielle (dB)')

subplot(2,2,3)
plot((g/(2*pi))-(1/2), abs(H_8_Box))
title('N = 19, Boxcar. Module de la reponse frequentielle')

subplot(2,2,4)
plot((g/(2*pi))-(1/2), 20*log10(abs(H_8_Box)))
title('N = 19, Boxcar. Module de la reponse frequentielle (dB)')

clear all;

% IIR
Fe = 16000
Fp = 2000
Fa = 4000
delta1 = 3
delta2 = 30

[N, Fc] = buttord(Fp/(Fe/2), Fa/(Fe/2), delta1, delta2)
[A,B] = butter(N, Fc)
[H, f] = freqz(A, B, 512, 'whole', Fe);

figure('Color',[1 1 1]);

subplot(2,1,1)
plot((f/(2*pi))-(1/2), 20*log10(abs(H)))
title('Module de la reponse frequentielle (dB)')

subplot(2,1,2)
plot((f/(2*pi))-(1/2), unwrap(angle(H)))
title('Phase de la reponse frequentielle')

% Decomposition en cellules du 1er et du 2eme ordre
Z = zp2sos(roots(A), roots(B), A(1))


[H_1, f] = freqz(Z(1,1:3), Z(1,4:6), 512, 'whole', Fe);
[H_2, g] = freqz(Z(2,1:3), Z(2,4:6), 512, 'whole', Fe)

figure('Color',[1 1 1]);

subplot(2,2,1)
plot((f/(2*pi))-(1/2), 20*log10(abs(H_1)))
title('Cellule 1. Module de la reponse frequentielle (dB)')

subplot(2,2,2)
plot((f/(2*pi))-(1/2), unwrap(angle(H_1)))
title('Cellule 1. Phase de la reponse frequentielle')

subplot(2,2,3)
plot((g/(2*pi))-(1/2), 20*log10(abs(H_2)))
title('Cellule 2. Module de la reponse frequentielle (dB)')

subplot(2,2,4)
plot((g/(2*pi))-(1/2), unwrap(angle(H_2)))
title('Cellule 2. Phase de la reponse frequentielle')