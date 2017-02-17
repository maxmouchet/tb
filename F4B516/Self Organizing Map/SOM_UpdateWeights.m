function map = SOM_UpdateWeights (map , patt_in , inode , iter , iter_max)

eta = (iter/iter_max) * (map.learning_rate_f - map.learning_rate_i) + map.learning_rate_i;
sigma = (iter/iter_max) * (map.neighborhood_spread_f - map.neighborhood_spread_i) + map.neighborhood_spread_i;

pnode = map.topology(inode,:);

% Compute distance (L2 norm) to pnode for each nodes 
diffs = map.topology - pnode;
dists = sqrt(dot(diffs, diffs, 2));

% Compute alpha by evaluating a gaussian pdf
alphas = normpdf(dists, 0, sigma);

% Update weights
map.weights = map.weights + (alphas .* eta .* (patt_in - map.weights));