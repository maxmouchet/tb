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
[Y, X] = max(S_filtre, [], 2)

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

%% Affichage
% Affichage de signaux filtrés
figure(1)

for i = 1:size(S,1)
    subplot(size(S,1), 1, i)
    plot(S_filtre(i,:))
    
    % Maximum déterminé
    line([X(i) X(i)], get(gca, 'YLim'), 'Color', 'r')
    
    % Axes en temporel
    set(gca, 'XTick',      [0 X(i) length(S_filtre(i,:))])
    set(gca, 'XTickLabel', [0 T(i) length(S_filtre(i,:))/fe])
end

% Affichage de la localisation
% On rajoute r1 maintenant qu'on le connait (pour tracer)
D = D + x(:,3);

figure(2)
circle(0,  0, D(1,:), 'r')
circle(40, 0, D(2,:), 'g')
circle(0, 40, D(3,:), 'b')
hold on
plot(x(:,1),x(:,2),'y*', 'MarkerSize', 10)
