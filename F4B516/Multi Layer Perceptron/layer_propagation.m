function output = layer_propagation (input , weights)
% Propage l'entr?e 'input' au travers de la couche d?finie par les poids
% synaptiques 'weights'. La fonction d'activation utilis?e est la fonction
% 'tanh'.
% Param?tres :
%   * input   : Vecteur ligne contenant les donn?es ? propager (par
%               exemple, un pattern ou les sorties d'une couche)
%               [1 x dim_input]
%   * weights : Matrice contenant les poids synaptiques correspondant ? la
%               couche dans laquelle doit s'op?rer la propagation
%               [(dim_input+1) x dim_output]
% R?sultats :
%   * output : Vecteur ligne de sortie de la couche apr?s propagation
%

% Au vecteur d'entr?e doit ?tre ajout?e une entr?e suppl?mentaire mise ? 1 
% correspondant au neurone de biais  

% tanh
input = [input 1];
output = tanh(input*weights);