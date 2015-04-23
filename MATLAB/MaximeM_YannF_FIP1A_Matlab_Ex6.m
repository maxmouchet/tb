% FIP 1A - Matlab - Ex 6
% Yann Feunteun, Maxime Mouchet
close all
clear
clc

A = rand(1000,1000);
b = rand(1000,1);

tic;inv(A);toc
tic;x = inv(A)*b;toc
tic;mldivide(A,b);toc

% Random matrix is not well-conditioned so this does not work
tic;cgs(A,b);

B = rand(1000,1000);

A = rand(1000,1000);
b = rand(1000,1);

tic;inv(A);toc
tic;x = inv(A)*b;toc
tic;mldivide(A,b);toc