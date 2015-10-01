function [ t_arr, n ] = Poisson (t, lambda)
%POISSON Génère une trajectoire d’un processus de Poisson
% Simulation d’une trajectoire temporelle décrite par ’t’ suivant une loi de Poisson de paramètre 'lambda'
% [IN]
%    t : Axe des temps échantillonné (liste des instants considérés) lambda : Intensité du processus
% [OUT]
%    t_arr : Instants d’arrivée des événements
%    n : Trajectoire de la réalisation (valeur du processus de
%    comptage en chaque instant du tableau ’t’)

    %(Q1) Nombre d’événements à générer. On ne considèrera donc que ces ’n_evt’ arrivées.
    n_evt = t(end) * 2 * lambda;

    % (Q2) Génération des durées entre les instants d’arrivée des événements
    dt = -log(rand(1, n_evt)) / lambda;

    % (Q3) Calcul des instants d’arrivée des événements considérés
    t_arr = cumsum(dt);

    % (Q4) Création du tableau contenant la trajectoire du processus c’est-à-dire % la valeur du processus de comptage en chaque instant du tableau ’t’
    n = zeros(size(t));

    % (Q5) Remplissage du tableau contenant la trajectoire du processus : le compteur n(k) % contient le nombre d’événements étant arrivés avant l’instant t(k)
    for k = 1: n_evt
        t_inc = t > t_arr(k);
        n = n + t_inc;
    end

    % On ne retourne que les evenements dont l'instant d'arrivee est anterieure
    % au dernier instant du tableau ’t’
    % => troncage de t_arr pour rester dans l’intervalle défini par t
    t_arr = t_arr(t_arr < t(end));
end