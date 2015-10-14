function [q_exponentielle, q_gaussienne, q_uniforme] = kolmogorov(data)

x = linspace(min(data), max(data), sqrt(length(data)));

%% Estimateurs
% Exponentiel
lambda_est = estimateurExponentielle(data);

% Gaussien
[mu_est, sigma2_est] = estimateurGaussien(data);

% Uniforme
[min_est, max_est] = estimateurUniforme(data);

%% Kolmogorov-Smirnov
% Fonction de repartion estime (normalisee)
f_estime = cumsum(hist(data, x)) / length(data);

% Fonction de repartitions theoriques
f_exponentielle = CDFExponential(x, lambda_est);
f_gaussienne = CDFGauss(x, mu_est, sqrt(sigma2_est));
f_uniforme = CDFUniform(x, min_est, max_est);

% Calcul de q
q_exponentielle = max(abs(f_estime - f_exponentielle));
q_gaussienne = max(abs(f_estime - f_gaussienne));
q_uniforme = max(abs(f_estime - f_uniforme));

end
