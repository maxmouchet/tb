close all;
clear all;

% Signal émis
t1 = [0:0.04:4];
y1 = sin(2*pi*1*t1);

figure(1)
plot(t1,y1)
title('Signal émis')

% Signal reçu sans bruit
a = 1;
t2 = [0:0.04:40];

decalage = 300;

y2 = zeros(1,length(t2));
y2(decalage:decalage+length(y1)-1) = a*y1;

figure(2)
plot(t2,y2)
title('Signal reçu sans bruit')
axis([0 40 -1 1])

% Signal reçu avec bruit
b = randn(1,length(t2)) * 1.25;
y3 = y2+b;

figure(3)
plot(t2,y3)
title('Signal reçu avec bruit')
axis([0 40 -1 1])

% Filtrage
h = fliplr(y1);
y4 = conv2(h,y3);

figure(4)
plot(t2,y4(1:1001))
title('Signal reçu filtré')
[Y, X] = max(y4)

% Double symbole émis
decalage1 = 300;
decalage2 = decalage1 + 300;

y5 = zeros(1,length(t2));
y5(decalage1:decalage1+length(y1)-1) = a*y1;
y5(decalage2:decalage2+length(y1)-1) = a*y1;
y5 = y5 + b;

figure(5)
plot(t2,y5)
title('Signal (deux symboles) reçu avec bruit')
axis([0 40 -1 1])

% Filtrage double symbole
y6 = conv2(h,y5);

figure(6)
plot(t2,y6(1:1001))
title('Signal reçu filtré')

% Détection max double symbole
[Y, X1] = max(y6)

y6(X1-60:X1+60) = zeros(1,121);

[Y, X2] = max(y6)