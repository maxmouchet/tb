function output = neuron_propagation (neuron , pattern)
% Propagation d'un pattern au travers d'un neurone.
% NEURON_PROPAGATION (neuron,pattern) retourne la sortie du neurone 
% 'neuron' quand le pattern 'pattern' est pr?sent? ? son entr?e.
% Param?tres :
%   neurone : Structure contenant la d?finition du neurone (poids synaptiques)
%   pattern : Pattern en entr?e [1 x dim_input]
% R?sultats :
%   output  : R?sultat du neurone [1 x 1]
% Contraintes :
%   Les poids synaptiques du neurone doivent ?tre initialis?s (en g?n?ral,
%   suite ? un apprentissage).

% Au vecteur d'entr?e doit ?tre ajout?e une entr?e suppl?mentaire mise ? 1 
% correspondant au biais

% tanh
output = tanh(pattern*neuron.weights);

% sigmoid
% output = sigmoid(pattern*neuron.weights);

% threshold
% x = pattern*neuron.weights;
% if x > 0
%     output = 1;
% else
%     output = -1;
% end

end