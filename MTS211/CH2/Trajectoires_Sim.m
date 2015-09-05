% Axe des temps
duree = 10 ;
nEchantillon = 600 ;
temps = linspace (0 , duree , nEchantillon ) ; % Durée du signal ( s )
                                               % Nombre d ’ échantillons temporel
                                               % Calcul des instants d ’ échantillonnage
                                               % Axe des réalisations

nRealisation = 100 ; % Nombre de réalisations

% ( Q1 ) ( Q2 ) Initialisation des valeurs des processus
x1 = zeros ( nRealisation , nEchantillon ) ;
x2 = zeros ( nRealisation , nEchantillon ) ;

% ( Q3 ) ( Q4 ) Définition des signaux : x = a * cos (2* pi * nu * t + phi )
nu = 0.1 ;                                            % Fréquence du signal ( non aléatoire )
eta_a = 2 ;                                           % Moyenne de l ’ amplitude
sigma_a = 1 ;                                         % Ecart - type de l ’ amplitude
a = ( randn (1 , nRealisation ) ) * sigma_a + eta_a ; % Amplitude gaussienne ( aléatoire )
phi_1 = ones (1 , nRealisation ) * pi /4 ;            % Phase non aléatoire ( pour X1 )
phi_2 = rand (1 , nRealisation ) *2* pi ;             % Phase uniformément
                                                      % distribuée entre 0 et
                                                      % 2* pi ( pour X2 )

% ( Q5 ) Calcul de X (t , w ) : simulation de chaque trajectoire
% pour chaque instant
close all
figure;
for w = 1: 10
    for t = 1: nEchantillon
        x1 (w , t ) = a ( w ) * cos (2* pi * temps ( t ) + phi_1 ( ...
            w ) ) ;
        
        x2 (w , t ) = a ( w ) * cos (2* pi * temps ( t ) + phi_2 ( ...
            w ) ) ;
    
    end
    stairs(x1(w));
    hold on;
    %plot(1:nEchantillon, x2(w), 'r');
end
