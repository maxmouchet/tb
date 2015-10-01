% Chaîne de Markov homogène => matrice de transition
P = [ ...
    1/3 2/3 0.0 ; ...
    1/4 1/4 1/2 ; ...
    7/8 1/8 0.0 ];

% Probabilités d'état initiales
pi_0 = [ 0.1 0.3 0.6 ];
nState = length(pi_0);

% Nombre de trajectoires
nTraj = 10000;

% Longueur des trajectoires
nTime = 20;
time = 0 : nTime - 1;

% Conservation des trajectoires simulées
traj = zeros(nTraj, nTime);

% Simulation de chaque trajectoire
for kTraj = 1 : nTraj
    
    % (Q2) Départ de la trajectoire 'kTraj'
    traj(kTraj, 1) = next_state(pi_0) ;

    % Simulation des états suivants, instant par instant
    for kTime = 2 : nTime

        % Etat courant
        s = traj(kTraj, kTime -1);
        
        % (Q3) Simulation de l'état suivant à partir des probabilités
        % de transition au départ de l'état courant 's'
        sn = next_state(P(s,:));
        
        % Stockage
        traj(kTraj, kTime) = sn;
    end
end

% Affichage de la trajectoire 1
clr = ['r' , 'g' , 'b' , 'c' , 'm' , 'k'];
iTraj = 1;
figure (101);
plot (traj(iTraj,:), strcat(clr(mod(iTraj, nState) + 1), 'o:'), 'LineWidth', 2);
grid on;
set(gca, 'YTick', [1:nState], 'YLim', [0 nState+1]); ...
    title(sprintf('Trajectoire %d', iTraj));
xlabel ('temps (k)');
ylabel ('Etat');