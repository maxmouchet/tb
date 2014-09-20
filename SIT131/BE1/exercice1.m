clear all;
close all;

% x = nombre de transformations
% y = nombre de placements
% z = nombre de touches

% x + 3y + 6z = 35
% x -  y +  z = 0
% x +  y +  z = 10

% A: matrice correspondant au systeme d'equations
A = [ 1 3 6; 1 -1 1; 1 1 1];

% B: matrice du resultat des equations
B = [ 35; 0; 10 ];

% X: solution du systeme
X = inv(A)*B

% La solution est unique car le determinant de A != 0
det = det(A)
