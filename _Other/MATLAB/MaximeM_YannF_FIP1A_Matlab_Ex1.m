% FIP 1A - Matlab - Ex 1
% Yann Feunteun, Maxime Mouchet
clear
clc

% Q.1
% We search the radius of a cylinder which has a height of 15m
% and a volume 20% greater than a cylinder of radius 8m and height 15m.
r = sqrt((120/100)*(pi*8^2*15)*(1/(15*pi)))

% Q.2
% Complex computations
x = -5+9*1i;
y = 6-2*1i;

z=x+y
x*y
x/y

abs(z)
angle(z)

% Q.3
% Quadratic equation
roots([2,-3,-10])

% Q.4
% Capacitor loading
R = 10*10^3
C = 10*10^-6

t = -log(-0.63+1)*R*C
RC = R*C
RC-t