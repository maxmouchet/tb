% Définition de l’échantillonnage temporel de la trajectoire
t = 0:0.1:10;

% (Q10) Trajectoire issue d’un processus d’intensité lambda1 sur l’intervalle temporel échantillonné ’t’ ’t_arr1’ contient les instants d’arrivée des différents événements du processus de Poisson
% (les instants de t_arr1 sont compris entre t(0) et t(end))
% n1 contient la valeur du processus de Poisson pour chaque instant de ’t’
lambda1 = 1;
[t_arr1, n1] = Poisson (t, lambda1);

% (Q11)
lambda2 = 3;
[t_arr2, n2] = Poisson (t, lambda2);

% (Q12)(Q13) Affichage comparatif des deux trajectoires
figure (10);
stairs(t, n1, 'b', 'LineWidth', 2);
hold on;
stairs(t, n2, 'r', 'LineWidth', 2);

grid on ;
xlabel ('\it t') ;
ylabel ('\it N(t)') ;
title ('Trajectoires d''un processus de Poisson');
legend (sprintf('\\lambda = %d', lambda1), ...
        sprintf('\\lambda = %d', lambda2), 'Location', 'NorthWest');