clear all
close all

%% Constantes
% s_signal
load('symbole.mat')

% sigX
load('signal1.mat')
load('signal2.mat')
load('signal3.mat')

c = 3e8;    % m/s
fe = 500e3; % Hz

% Position des antennes
P = [0 0; 40 0; 0 40]

%% Filtrage
% Création du filtre adapté
h = fliplr(s_signal);

% Filtrage des signaux
S = [sig1; sig2; sig3];
S_filtre = conv2(h, S);

% Détermination des maximums (= corrélation max.)
[Y, X] = max(S_filtre,[],2)

figure(1)

% subplot(3,1,1)
% plot(sig1_filtre)
% line([X1 X1], get(gca, 'YLim'), 'Color', 'r')
% set(gca, 'XTick', [0 X1 length(sig1_filtre)])
% set(gca, 'XTickLabel', [0 t1 length(sig1_filtre)/fe])
% 
% subplot(3,1,2)
% plot(sig2_filtre)
% line([X2 X2], get(gca, 'YLim'), 'Color', 'r')
% set(gca, 'XTick', [0 X2 length(sig2_filtre)])
% set(gca, 'XTickLabel', [0 t2 length(sig2_filtre)/fe])
% 
% subplot(3,1,3)
% plot(sig3_filtre)
% line([X3 X3], get(gca, 'YLim'), 'Color', 'r')
% set(gca, 'XTick', [0 X3 length(sig3_filtre)])
% set(gca, 'XTickLabel', [0 t3 length(sig3_filtre)/fe])

%% Détermination des intervalles de temps
T = X / fe        % Récupération du temps par rapport à la fréq. éch.
I = T - min(T)    % Retards relatifs à l'antenne la plus proche
D = I * c / 1000  % Distances minimales par rapport au retard

%% Résolution du système

% Trois équations à résoudre
% (x-0)²  + (y-0)²  = r1²
% (x-40)² + (y-0)²  = (r1 + t2*c)²
% (x-0)²  + (y-40)² = (r1 + t3*c)²

% x(1) : x
% x(2) : y
% x(3) : r1
syst = @(x)[(x(1) - P(1,1))^2 + (x(2) - P(1,2))^2 - (x(3) + D(1,:))^2;
            (x(1) - P(2,1))^2 + (x(2) - P(2,2))^2 - (x(3) + D(2,:))^2;
            (x(1) - P(3,1))^2 + (x(2) - P(3,2))^2 - (x(3) + D(3,:))^2];

x0 = [0, 0, 0];
x = fsolve(syst, x0)

% On rajoute r1 maintenant qu'on le connait (pour tracer)
D = D + x(:,3);

figure(2)
circle(0,  0, D(1,:), 'r')
circle(40, 0, D(2,:), 'g')
circle(0, 40, D(3,:), 'b')
hold on
plot(x(:,1),x(:,2),'y*', 'MarkerSize', 10)
