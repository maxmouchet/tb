figure (100);
hold on;
clr = ['r' , 'g' , 'b' , 'c' , 'm' , 'k'];

% (Q5) Estimation des distribution d'états à partir des trajectoires simulées
for kState = 1 : nState
    plot (time, sum(traj==kState, 1)/nTraj, strcat(clr(mod(kState- ...
                                                      1, nState) + 1), '--'), 'LineWidth', 2);
end
grid on;
title (sprintf(['Evolution des probabilités d''états \\' ...
                'pi estimées sur %d trajectoires'], nTraj));

lg = [];
for kState = 1 : nState
    lg = [lg; sprintf('\\pi_%d(k)', kState)];
end
leg = legend (lg, 'Location', 'NorthEast');
xlabel ('temps (k)');
ylabel ('\pi(k)');

% (Q6) Affichage de la distribution initiale
for kState = 1 : nState
    plot (0 , pi_0(kState) , strcat(clr(mod(kState-1,nState)+1),'o') , 'LineWidth' , 2);
end
