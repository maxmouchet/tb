function [q_exponentielle, q_gaussienne, q_uniforme] = chi_square(data)

x = linspace(min(data), max(data), sqrt(length(data)));

%% Estimateurs
% Exponentiel
lambda_est = estimateurExponentielle(data);

% Gaussien
[mu_est, sigma2_est] = estimateurGaussien(data);

% Uniforme
[min_est, max_est] = estimateurUniforme(data);

%% Ki²
% Calcul des k_i
ki = hist(data, x);
ki = ki(1:sqrt(length(data)) - 1);

% Calcul des fonctions de repartitions theoriques
f_exponentielle = CDFExponential(x, lambda_est);
f_gaussienne = CDFGauss(x, mu_est, sqrt(sigma2_est));
f_uniforme = CDFUniform(x, min_est, max_est);

% Calcul des p_i
pi_exponentielle = zeros(1, length(x) - 1);
pi_gaussienne = zeros(1, length(x) - 1);
pi_uniforme = zeros(1, length(x) - 1);

for i = 1:length(x) - 1
    pi_exponentielle(i) = f_exponentielle(i+1) - f_exponentielle(i);
    pi_gaussienne(i) = f_gaussienne(i+1) - f_gaussienne(i);
    pi_uniforme(i) = f_uniforme(i+1) - f_uniforme(i);
end

% Calcul des q
n = length(data);

q_exponentielle = sum(((ki - n*pi_exponentielle) .^2) ./ (n*pi_exponentielle));
q_gaussienne    = sum(((ki - n*pi_gaussienne)    .^2) ./ (n*pi_gaussienne));
q_uniforme      = sum(((ki - n*pi_uniforme)      .^2) ./ (n*pi_uniforme));

end