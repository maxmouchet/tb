function map = SOM_Create2D (N , learning_rate , neighborhood_spread , size_pattern)

    map = SOM_Create (N , 2 , learning_rate , neighborhood_spread , size_pattern) ;
    
    % Topologie de la carte
    map.n_dims = 2;
    map.n_nodesPerDim = N;
    map.n_nodes = map.n_nodesPerDim^2;
    map.learning_rate_i = learning_rate(1);
    map.learning_rate_f = learning_rate(2);
    map.neighborhood_spread_i = neighborhood_spread(1);
    map.neighborhood_spread_f = neighborhood_spread(2);
    map.weights = rand(map.n_nodes, size_pattern);
    [p,q] = meshgrid(1:map.n_nodesPerDim, 1:map.n_nodesPerDim);
    map.topology = [p(:) q(:)];