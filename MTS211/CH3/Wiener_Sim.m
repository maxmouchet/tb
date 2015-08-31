% Définition de l'échantillonnage temporel de la trajectoire
t = 0:0.01:10;

% Trajectoire issue d'un processus de paramètre sigma21 sur l'intervalle temporel échantillonné 't'
% b1 contient la valeur du processus de Wiener pour chaque instant de 't'
sigma21 = 1;
[b1] = Wiener(t, sigma21);

% Trajectoire issue d’un processus de paramètre sigma22
sigma22 = 50;
[b2] = Wiener(t, sigma22);

% Affichage comparatif des deux trajectoires
figure (11);
stairs(t, b1, 'b', 'LineWidth', 2);
hold on;
stairs(t, b2, 'r', 'LineWidth', 2);
grid on;
xlabel('\it t');
ylabel('\it W(t)');
title('Trajectoires d un processus de Wiener');
legend(sprintf('\\sigma^2 = %d' , sigma21), ...
       sprintf('\\sigma^2 = %d', sigma22), ...
       'Location', 'NorthWest');