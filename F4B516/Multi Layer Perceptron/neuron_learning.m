function [neuron , stat] = neuron_learning (neuron , dataset , learning_rate , mse , n_epochs)
% Assure l'apprentissage d'un neurone.
% NEURON_LEARNING (neuron, pattern, class, n_epochs, learning_rate) utilise le
% jeu de donn?es 'dataset' en entr?e pour r?aliser un apprentissage 
% supervis? du neurone 'neuron'.
% L'apprentissage est param?tr? par le nombre de pr?sentation du jeu
% complet de patterns ainsi que par le taux d'apprentissage initial.
% Param?tres :
%   * neuron  : Neurone dont l'apprentissage est ? r?aliser
%   * dataset : Jeu de patterns servant ? l'apprentissage
%       - pattern  : Matrice contenant les patterns du jeu d'apprentissage
%                       [n_pattern x dim_pattern] avec dim_pattern = net.dim_input
%       - class    : Matrice contenant les classes correspondant aux patterns
%                       [n_pattern x n_class] avec n_class = net.dim_output
%   * learning_rate : Taux d'apprentisage initial
%   * mse           : Erreur quadratique moyenne ? attendre
%   * n_epochs      : Nombre maximum de pr?sentation du jeu d'apprentissage
% R?sultats :
%   neuron : Neurone mis ? jour par l'apprentissage
%           - weights : Poids synaptiques du neurone
%                   [dim_pattern+1 x 1]
%   stat : Structure contenant les statistiques d'apprentissage de chaque
%   epoch
%       stat(epoch).mse         : Erreur quadratique moyenne
%       stat(epoch).err_pattern : Nombre de patterns mal classifi?s
%       stat(epoch).mat_conf    : Matrice de confusion

% Nombre de classes en sortie = 2 (neurone s?parateur)
n_class = 2 ;

% V?rification de la taille des donn?es propos?es pour l'apprentissage
if dataset.dim_input ~= neuron.dim_input
    disp ('ERROR : Incoh?rence de dimensions en entr?e') ;
    return ;
end   
if dataset.n_class ~= n_class
    disp ('ERROR : Incoh?rence de dimensions en sortie') ;
    return ;
end

% Stockage des param?tres d'apprentissage du r?seau
neuron.learning_rate = learning_rate ;

% Param?tres internes de l'algorithme
pattern_order = 'random' ;      % Ordre de pr?sentation des patterns ('random')

% Allocation & Initialisation al?atoire des poids synaptiques du r?seau
neuron.weights = rand([neuron.dim_input+1 1]) ;

% Initialisation des statistiques d'apprentissage
stat = [] ;

% Boucle de pr?sentation du jeu d'apprentissage
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
    stat_epoch.mat_conf     = zeros (n_class,n_class) ;
    
    %
    % Pr?sentation d'un pattern
    %
    for i = 1:dataset.n_pattern
    
        % S?lection du pattern ? pr?senter
        p = tab(i) ;
        
        % Pattern ? pr?senter en entr?e (ajout du biais)
        patt_in  = [dataset.pattern(p,:) 1] ;
        
        % Sortie d?sir?e (neurone s?parateur : classe 0 -> -1, classe 1 -> 1
        patt_out = dataset.class(p,:)*2-1 ;
       
        %
        % Propagation compl?te du pattern (entr?e --> sortie)
        %
        
        % Apprentissage avec tanh ou sigmoide ou seuil
        net_output = neuron_propagation(neuron, patt_in);
        
        % Calcul de l'erreur constat?e en sortie
        error_output = patt_out - net_output ;
               
        % Ajustement effectif des poids
        neuron.weights = neuron.weights + (learning_rate * error_output * patt_in)';
        
        %
        % Mise ? jour des statistiques
        %
        
        % Erreur quadratique moyenne
        stat_epoch.mse = stat_epoch.mse + sum(error_output.^2) ;
        
        % Erreur de classification
        cl = (net_output >= 0.0)*1 ;
        stat_epoch.err_pattern = stat_epoch.err_pattern + 1*(cl~=dataset.class(p)) ;
        
        % Matrice de confusion
        stat_epoch.mat_conf(dataset.class(p)+1,cl+1) = stat_epoch.mat_conf(dataset.class(p)+1,cl+1) + 1 ;

    end


    % Erreur quadratique moyenne
    stat_epoch.mse = stat_epoch.mse / dataset.n_pattern ;

    stat = [stat ; stat_epoch] ;

    % Affichage (pour un apprentissage long)
    if (mod(epoch,10) == 0)
        disp(sprintf('Epoch : %d/%d (%d/%d) %f\n' , epoch , n_epochs , stat_epoch.err_pattern , dataset.n_pattern , stat_epoch.mse)) ;
    end
    
    % Crit?re d'arr?t bas? sur l'erreur quadratique moyenne
    if (stat_epoch.mse <= mse)
        break ;
    end
    
end
    
% Stockage de l'?tat des param?tres relatifs aux crit?res d'arr?t
neuron.n_epochs = epoch ;
neuron.mse      = stat_epoch.mse ;
