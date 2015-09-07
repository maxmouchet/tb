% Axe des temps
duree = 10 ;
nEchantillon = 600 ;
temps = linspace (0 , duree , nEchantillon ) ; % Durée du signal ( s )
                                               % Nombre d ' échantillons temporel
                                               % Calcul des instants d ' échantillonnage
                                               % Axe des réalisations

nRealisation = 500 ; % Nombre de réalisations

% ( Q1 ) ( Q2 ) Initialisation des valeurs des processus
x1 = zeros ( nRealisation , nEchantillon ) ;
x2 = zeros ( nRealisation , nEchantillon ) ;

% ( Q3 ) ( Q4 ) Définition des signaux : x = a * cos (2* pi * nu * t + phi )
nu = 0.1 ;                                            % Fréquence du signal ( non aléatoire )
eta_a = 2 ;                                           % Moyenne de l ' amplitude
sigma_a = 1 ;                                         % Ecart - type de l ' amplitude
a = ( randn (1 , nRealisation ) ) * sigma_a + eta_a ; % Amplitude gaussienne ( aléatoire )
phi_1 = ones (1 , nRealisation ) * pi /4 ;            % Phase non aléatoire ( pour X1 )
phi_2 = rand (1 , nRealisation ) *2* pi ;             % Phase uniformément
                                                      % distribuée entre 0 et
                                                      % 2* pi ( pour X2 )

% ( Q5 ) Calcul de X (t , w ) : simulation de chaque trajectoire
% pour chaque instant
close all
figure;
for w = 1: nRealisation
    for t = 1: nEchantillon
        x1 (w , t ) = a ( w ) * cos (2* pi * temps ( t ) + phi_1 ( ...
            w ) ) ;
        
        x2 (w , t ) = a ( w ) * cos (2* pi * temps ( t ) + phi_2 ( ...
            w ) ) ;
    end
end

subplot(2,1,1)
plot(temps, x1(1:10,:),'b');
xlabel('temps (t)');
ylabel('X_1(t)');
title('Réalisation du processus X_1(t)');

subplot(2,1,2);
plot(temps, x2(1:10, :), 'r');
xlabel('temps (t)');
ylabel('X_2(t)');
title('Réalisation du processus X_2(t)');

% ( Q10 ) ( Q11 ) ( Q12 ) ( Q13 ) Moyenne d ' ensemble ( espérance ) estimée pour chaque instant
moy_stat1 = zeros (1, nEchantillon ) ;
moy_stat2 = zeros (1, nEchantillon ) ;
for i = 1: nEchantillon
moy_stat1 ( i ) = mean ( x1 (:, i ) ) ;
moy_stat2 ( i ) = mean ( x2 (:, i ) ) ;
end

% ( Q15 ) ( Q16 ) ( Q17 ) ( Q18 ) Moyenne temporelle pour chaque réalisation
moy_temp1 = zeros (1, nRealisation ) ;
moy_temp2 = zeros (1, nRealisation ) ;
for i = 1: nRealisation
moy_temp1 ( i ) = mean ( x1 (i,:) ) ;
moy_temp2 ( i ) = mean ( x2 (i,:) ) ;
end

% Affichage des moyennes de premier ordre
figure (21) ;
subplot (1,2 ,1) ;
plot ( temps, moy_stat1 , 'b ') ;
hold on ;
plot ( temps, moy_stat2 , 'r ') ;
title ( ' Moyennes  statistiques ') ;
xlabel ( ' temps  ( t ) ') ; ylabel ( ' m_X ( t ) ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ') ;
grid on ;

nRea = 500 ; % Nombre de réalisations à afficher
subplot (1,2 ,2) ;
plot ( moy_temp1 (1: nRea ), 1: nRea , 'b + ') ; hold on ;
plot ( moy_temp2 (1: nRea ), 1: nRea , 'r . ') ;
title ( ' Moyennes  temporelles ') ;
xlabel ( ' $$ \ overline  { X (\ omega ) } $$ ', 'interpreter' , 'latex') ;
ylabel ( ' numéro  réalisation ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ' , ' Location ' , ' NorthWest ') ;
grid on

% (Q30) Fonction d'autocorrélation statistique estimée pour chaque paire
% d'instants (e,e+p)
% L'écart 'p' entre  les  instants  varie  entre 0 et nPas -1
nPas = 100 ;
gamma1 = zeros (nEchantillon -nPas , nPas) ;
gamma2 = zeros (nEchantillon -nPas , nPas) ;
for e = 1: nEchantillon -nPas
 for p = 0:nPas -1
     gamma1(e,p+1) = mean( x1(:,e) .* x1(:,e+p) ) ;
     gamma2(e,p+1) = mean( x2(:,e) .* x2(:,e+p) ) ;
 end
end

%(Q35) Autocorrélation temporelle  estimée  sur  chaque  réalisation à l'aide de la
% fonction  xcorr  de  Matlab (signal  déterministe)
% xcorr  produit  des  autocorrélations  sur 2* nEchantillon +1, le  décalage  nul
% étant  en  position  ,nEchantillon '.
auto1 = zeros(nRealisation , 2* nEchantillon +1);
auto2 = zeros(nRealisation , 2* nEchantillon +1) ;
for traj = 1: nRealisation
    auto1(traj ,:) = xcorr (x1(traj ,:) , nEchantillon) ;
    auto2(traj ,:) = xcorr (x2(traj ,:) , nEchantillon) ;
end

% (Q31) Affichage  sous  forme d'images  via la  fonction  Matlab  'imagesc '
% La  couleur  dans l'image  renseigne  sur les  valeurs  des  autocorrélations
% estimées.
figure  (30) ;
set (gcf , 'Position' , [10 10 1400  500]) ;
subplot (1,2,1) ;
imagesc(gamma1) ;
xlabel('\tau') ;
ylabel('t (numéro échantillon)') ;
title('$$\Gamma_{X_1}(t,t+\tau)$$','interpreter','latex') ;
colormap(jet) ;
subplot (1,2,2) ;
imagesc(auto1 (1:50, nEchantillon:nEchantillon+nPas)) ;
title ('$$\overline {X_1(\omega ,t)X_1(\omega ,t+\tau)}$$ pour 50 r\''ealisations ','interpreter','latex') ;
xlabel('\tau') ;
ylabel('numéro réalisation ') 