function [ state ] = next_state (proba_transition)
% NEXT_STATE : retourne le numéro de l’état de la trajectoire à l’instant t+1,
% en fonction des probabilités de transition de la chaîne au départ
% de l’état actuel de la chaîne à l’instant t
% [IN]
%     proba_transition : Vecteur des probabilités de transition
%     entre l’état actuel à l’instant t considéré, et tous les autres états de la chaîne
% [OUT]
%     state : Après un tirage aléatoire (en fonction des probabilités de transition), état de la chaîne à l’instant t+1

% L'etat suivant a un indice compris entre 1 et K
% Plus la valeur de transition vers un autre état est grand, plus grande est la chance
% que la somme calculée + 1 indique le numéro de cet état.
state = sum(rand > cumsum(proba_transition)) + 1 ;

end