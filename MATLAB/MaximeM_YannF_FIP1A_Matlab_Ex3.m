% FIP 1A - Matlab - Ex 2
% Yann Feunteun, Maxime Mouchet
close all
clear
clc

figure

% Sinewave signal
subplot(2,2,1)

t = 0:0.0000001:0.0006;
A = 0.3;
f = 100*10^3;
w = 2*pi*f;
phi = pi/6;
y = A*sin(w*t+phi);

plot(t,y);
title('Sinewave signal');
xlabel('Time (s)');
ylabel('Magnitude (v)');

% AM modulated signal
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

plot(t,y);
title('AM modulated signal');
xlabel('Time (s)');
ylabel('Magnitude (v)');

% White noise signal
subplot(2,2,3);

t = 0:0.01:6;
yr = -0.1 + (0.2).*rand(length(t),1);

plot(t, yr);
ylim([-0.2 0.2]);
title('White noise signal');
xlabel('Time (s)');
ylabel('Magnitude (v)');

% Phase modulated signal
subplot(2,2,4)

t = 0:0.0000001:0.0006;

A = 1;
fc = 100*10^3;
w = 2*pi*fc;

y = A*sin(w*t+yr);
plot(t, y);
title('Phase modulated signal');
xlabel('Time (s)');
ylabel('Magnitude (v)');