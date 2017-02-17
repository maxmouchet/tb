function map = SOM_OneStepLearning (map , data , iter , iter_max)
% SOM_OneStepLearning : R?alise une it?ration de l'apprentissage d'une
% carte de Kohonen.
% Les poids sont mis ? jour directement dans la structure map
% [IN]
%   map      : Structure contenant la carte de Kohonen
%   data     : Structure contenant les donn?es
%   iter     : Num?ro de l'it?ration courante
%   iter_max : Nombre maximum d'it?ration
% [OUT]
%   Les poids de la carte sont mis ? jour dans la structure map

% patterns = data.pattern;
patterns = datasample(data.pattern, 10);

% for i = 1:data.n_pattern
% for i = 1:10
for i = 1:10
   patt = patterns(i,:);
   [~, inode] = SOM_NearestNode(patt, map);
   map = SOM_UpdateWeights(map, patt, inode, iter, iter_max);
end
