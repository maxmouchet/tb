function [ b ] = Wiener (t , sigma2)
% WIENER Génère une trajectoire d’un processus de Wiener de paramètre 'sigma2'
% Simulation d’une trajectoire temporelle décrite par 't'
% [IN]
%   t : Axe des temps échantillonné (liste des instants considérés)
%   sigma2 : Paramètre du processus de Wiener
% [OUT]
%   b : Trajectoire de la réalisation (valeur du processus de
%   Wiener en chaque instant du tableau 't')
    
    % Nombre de déplacements élémentaires le long de la trajectoire
    n_depl = length(t)-1;

    % (Q2)(Q3) Génération des variations de valeurs pour chaque pas
    % d’échantillonnage temporel
    % C’est l’écart-type ici !!! var(W(t)-W(s)) = sigma2 * |t-s| !!!!
    db = sqrt((sigma2) * (t(2)-t(1))) * randn(1,n_depl);
    
    % (Q4) Construction de la trajectoire par cumul des variations (départ de 0)
    b = zeros (size(t)) ; b(2:end) = cumsum(db);
end
