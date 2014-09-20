clear all;
close all;

% x = nombre de transformations
% y = nombre de placements
% z = nombre de touch?s

% x + 3y + 6z = 35
% x -  y +  z = 0
% x +  y +  z = 10

% A: matrice correspondant au syst?me d'?quations
A = [ 1 3 6; 1 -1 1; 1 1 1];

% B: matrice du r?sultat des ?quations
B = [ 35; 0; 10 ];

% X: solution du syst?me
X = inv(A)*B

% La solution est unique car le d?terminant de A != 0
det = det(A)