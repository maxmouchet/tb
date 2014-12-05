clear all;
close all;

A = [ 2 2 1; 5 5 5; 0 0 1 ]
dim = length(A)

% Question 3
% On calcule le polynome caracteristique
% det(A - LambdaId)
polynome = det(A - sym('lambda')*eye(dim))

% On calcule les valeurs propres
% polynome = 0
eigenvalues = roots(sym2poly(polynome)).'

% Question 4
% On calcule les sous-espaces propres
P = [];
for eigenvalue = eigenvalues
    nullspace = null(A-eigenvalue*eye(dim))
    P = [P, nullspace];
end

% Question 5
P

% Question 6
% D = P^-1AP
D = inv(P)*A*P

% Question 7
% TODO

% Question 8
B = A - 4*eye(dim)
M = D - 4*eye(dim)
nullspace = null(B)
