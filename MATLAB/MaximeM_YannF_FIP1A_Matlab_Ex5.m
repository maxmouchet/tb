% FIP 1A - Matlab - Ex 5
% Yann Feunteun, Maxime Mouchet
close all
clear
clc

% Figure 1
figure

% Sinewave signal FFT
subplot(2,2,1)

t = 0:0.0000001:0.0006;
A = 0.3;
f = 100*10^3;
w = 2*pi*f;
phi = pi/6;
y = A*sin(w*t+phi);

N = length(y);
spectre = abs(fft(y,N));
Fs = 1/0.0000001;
freq = (0:N/2-1)*Fs/N;

plot(freq, 2*spectre(1:N/2));
title('Sinewave signal FFT');
xlabel('Frequency (Hz)');
ylabel('Amplitude');

% AM modulated signal FFT
subplot(2,2,2)

t = 0:0.0000001:0.0006;
A = 0.1;
fm = 5*10^3;
w = 2*pi*fm;
signal = A*cos(w*t);

fc = 100*10^3;
w = 2*pi*fc;
carrier = sin(w*t);

y = signal.*carrier;

N = length(y);
spectre = abs(fft(y,N));
Fs = 1/0.0000001;
freq = (0:N/2-1)*Fs/N;

plot(freq, 2*spectre(1:N/2));
title('AM modulated signal FFT');
xlabel('Frequency (Hz)');
ylabel('Amplitude');

% White noise signal FFT
subplot(2,2,3);

t = 0:0.0000001:0.0006;
yr = -0.1 + (0.2).*randn(length(t),1);

N = length(yr);
spectre = abs(fft(yr,N));
Fs = 1/0.0000001;
freq = (0:N/2-1)*Fs/N;

plot(freq, 2*spectre(1:N/2));
title('White noise signal FFT');
xlabel('Frequency (Hz)');
ylabel('Amplitude');

% Phase modulated signal FFT
subplot(2,2,4)

t = 0:0.0000001:0.0006;

A = 1;
fc = 100*10^3;
w = 2*pi*fc;

y = A*sin(w*t+yr');

N = length(y);
spectre = abs(fft(y,N));
Fs = 1/0.0000001;
freq = (0:N/2-1)*Fs/N;

plot(freq, 2*spectre(1:N/2))

title('Phase modulated signal FFT');
xlabel('Frequency (Hz)');
ylabel('Amplitude');