close all;
clear all;

% FIR
Fe = 16000;
Fc = 2000;

wn = Fc/(Fe/2);

N = 7;

h_8_ham = fir1(N, wn, hamming(N+1))

A = [1];
B = h_8_ham;

[H_8_Ham, f] = freqz(A, B, 512, 'whole', Fe);

figure('Color',[1 1 1]);

subplot(2,2,1)
stem(h_8_ham)

subplot(2,2,2)
plot(f, abs(H_8_Ham))

subplot(2,2,3)
semilogy(f, fftshift(20*log10(abs(H_8_Ham))))

subplot(2,2,4)
plot(f, fftshift(unwrap(angle(H_8_Ham))))

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

subplot(2,2,1)
semilogy(f, fftshift(20*log10(abs(H))))

subplot(2,2,2)
plot(f, fftshift(unwrap(angle(H))))

% D?composition en cellules du 1er et du 2eme ordre
Z = zp2sos(roots(A), roots(B), A(1))