function [net , stat] = mlp_learning (net , dataset , learning_rate , mse , n_epochs)
% Assure l'apprentissage d'un MLP via une r?tropropagation classique
% MLP_LEARNING (net, pattern, class, n_epochs, learning_rate) utilise les
% patterns 'patterns' en entr?e et les classes 'classes' associ?es ? ces 
% patterns pour r?aliser un apprentissage supervis? du r?seau MLP 'net'.
% L'apprentissage est param?tr? par le nombre de pr?sentation du jeu
% complet de patterns ainsi que par le taux d'apprentissage initial.
% Param?tres :
%   * net     : R?seau MLP dont l'apprentissage est ? r?aliser
%   * dataset : Jeu de patterns servant ? l'apprentissage
%       - pattern  : Matrice contenant les patterns du jeu d'apprentissage
%                       [n_pattern x dim_pattern] avec dim_pattern = net.dim_input
%       - class    : Matrice contenant les classes correspondant aux patterns
%                       [n_pattern x n_class] avec n_class = net.dim_output
%   * learning_rate : Taux d'apprentisage initial
%   * mse           : Erreur quadratique moyenne ? attendre
%   * n_epochs      : Nombre maximum de pr?sentation du jeu d'apprentissage
% R?sultats :
%   net  : R?seau mis ? jour par l'apprentissage
%           - IH : Poids synaptiques entre couche d'entr?e et couche cach?e
%                   [dim_pattern+1 x n_hidden_neurons]
%           - HO : Poids synaptiques entre couche cach?e et couche de sortie
%                   [n_hidden_neurons+1 x n_class]
%   stat : Structure contenant les statistiques d'apprentissage de chaque
%   epoch
%       stat(epoch).mse         : Erreur quadratique moyenne
%       stat(epoch).err_pattern : Nombre de patterns mal classifi?s
%       stat(epoch).mat_conf    : Matrice de confusion

% V?rification de la taille des donn?es propos?es pour l'apprentissage
if dataset.dim_input ~= net.dim_input
    disp ('ERROR : Incoh?rence entre dimension des patterns et nombre de neurones de la couche d''entr?e') ;
    return ;
end   
if dataset.n_class ~= net.dim_output
    disp ('ERROR : Incoh?rence entre dimension des classes et nombre de neurones de la couche de sortie') ;
    return ;
end

% Stockage des param?tres d'apprentissage du r?seau
net.learning_rate = learning_rate ;

% Param?tres internes de l'algorithme
pattern_order = 'random' ;      % Ordre de pr?sentation des patterns ('random')

% Allocation & Initialisation al?atoire des poids synaptiques du r?seau
net.IH = rand([net.dim_input+1 net.dim_hidden]) ;
net.HO = rand([net.dim_hidden+1 net.dim_output]) ;

% Initialisation des statistiques d'apprentissage
stat = [] ;

% Boucle de pr?sentation du jeu d'apprentissage
% Crit?res d'arr?t : epoch > n_epochs ou stat_epoch.mse <= mse
for epoch = 1:n_epochs
   
    % Ordre de pr?sentation des patterns
    if (pattern_order == 'random')
        tab = randperm(dataset.n_pattern) ;
    else
        tab = 1:dataset.n_pattern ;
    end
    
    % Initialisation des statistiques pour la pr?sentation
    stat_epoch.mse          = 0.0 ;
    stat_epoch.err_pattern  = 0 ;
    stat_epoch.mat_conf     = zeros (net.dim_output,net.dim_output) ;
    
    %
    % Pr?sentation d'un pattern
    %
    for i = 1:dataset.n_pattern
    
        % S?lection du pattern ? pr?senter
        p = tab(i) ;
        
        % Pattern en entr?e & sortie souhait?e
        patt_in = dataset.pattern(p,:) ;
        patt_out = dataset.output(p,:) ;
       
        % Propagation compl?te du pattern (entr?e --> cach?e --> sortie)
        hidden_output = layer_propagation (patt_in , net.IH) ;
        net_output = layer_propagation (hidden_output , net.HO) ;

        % Calcul de l'erreur constat?e en sortie
        error_output = patt_out - net_output;
        
        % Calcul de l'ajustement des poids de HO (couche cach?e -> couche de sortie)
        delta_HO = error_output .* sech(net_output).^2;
        
        % Calcul de l'ajustement des poids de IH (couche d'entr?e -> couche cach?e)
        delta_IH = (delta_HO * net.HO(1:net.dim_hidden,:)') .* sech(hidden_output).^2;
        
        % Ajustement effectif des poids
        net.HO = net.HO + learning_rate * ([hidden_output 1]' * delta_HO);
        net.IH = net.IH + learning_rate * ([patt_in 1]' * delta_IH);

        %
        % Mise ? jour des statistiques
        %
        
        % Erreur quadratique moyenne
        stat_epoch.mse = stat_epoch.mse + sum(error_output.^2) ;
        
        % Erreur de classification
        [v , cl] = max(net_output) ;
        stat_epoch.err_pattern = stat_epoch.err_pattern + 1*(cl~=dataset.class(p)+1) ;
        
        % Matrice de confusion
        stat_epoch.mat_conf(dataset.class(p)+1,cl) = stat_epoch.mat_conf(dataset.class(p)+1,cl) + 1 ;

    end

    % Erreur quadratique moyenne
    stat_epoch.mse = stat_epoch.mse / dataset.n_pattern ;

    stat = [stat ; stat_epoch] ;

    % Affichage (pour un apprentissage long)
    if (mod(epoch,200) == 0)
        disp(sprintf('Epoch : %d/%d (%d/%d) %f\n' , epoch , n_epochs , stat_epoch.err_pattern , dataset.n_pattern , stat_epoch.mse)) ;
    end
    
    % Crit?re d'arr?t bas? sur l'erreur quadratique moyenne
    if (stat_epoch.mse <= mse)
        break ;
    end
    
end
    
% Stockage de l'?tat des param?tres relatifs aux crit?res d'arr?t
net.n_epochs = epoch ;
net.mse      = stat_epoch.mse ;
