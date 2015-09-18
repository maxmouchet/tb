close all;
clear all;

cmap1 = cbrewer('seq', 'YlGnBu', 100);
cmap2 = cbrewer('seq', 'YlOrBr', 100);

% Axe des temps
duree = 10 ;
nEchantillon = 600 ;
temps = linspace (0 , duree , nEchantillon ) ; % Dur??e du signal ( s )
                                               % Nombre d ' ??chantillons temporel
                                               % Calcul des instants d ' ??chantillonnage
                                               % Axe des r??alisations

nRealisation = 500 ; % Nombre de r??alisations

% ( Q1 ) ( Q2 ) Initialisation des valeurs des processus
x1 = zeros ( nRealisation , nEchantillon ) ;
x2 = zeros ( nRealisation , nEchantillon ) ;

% ( Q3 ) ( Q4 ) D??finition des signaux : x = a * cos (2* pi * nu * t + phi )
nu = 0.1 ;                                            % Fr??quence du signal ( non al??atoire )
eta_a = 2 ;                                           % Moyenne de l ' amplitude
sigma_a = 1 ;                                         % Ecart - type de l ' amplitude
%a = ( randn (1 , nRealisation ) ) * sigma_a + eta_a ; % Amplitude gaussienne ( al??atoire )
a = ones(1, nRealisation) * 2;
phi_1 = ones (1 , nRealisation ) * pi /4 ;            % Phase non al??atoire ( pour X1 )
phi_2 = rand (1 , nRealisation ) *2* pi ;             % Phase uniform??ment
                                                      % distribu??e entre 0 et
                                                      % 2* pi ( pour X2 )

% ( Q5 ) Calcul de X (t , w ) : simulation de chaque trajectoire
% pour chaque instant
for w = 1: nRealisation
    for t = 1: nEchantillon
        x1 (w , t ) = a ( w ) * cos (2* pi * temps ( t ) + phi_1 ( ...
            w ) ) ;
        
        x2 (w , t ) = a ( w ) * cos (2* pi * temps ( t ) + phi_2 ( ...
            w ) ) ;
    end
end


% Illustration trajectoire
figure
set(gcf, 'Color', 'w')
plot(temps, x1(1,:), 'color', cmap1((15+10)*3,:));
xlabel('temps (t)');
ylabel('X_1(t)');
title('Realisation du processus X_1(t)');
print('-deps','q1.eps')

figure
set(gcf, 'Color', 'w')

subplot(2,1,1)
hold on;

for i = 1:15
    plot(temps, x1(i,:), 'color', cmap1((i+10)*3,:));
end

xlabel('temps (t)');
ylabel('X_1(t)');
title('Realisations du processus X_1(t)');

subplot(2,1,2);
hold on;

for i = 1:10
    plot(temps, x2(i,:), 'color', cmap2((i+10)*3,:));
end
xlabel('temps (t)');
ylabel('X_2(t)');
title('Realisations du processus X_2(t)');
saveas(gcf,'q5','epsc');

% ( Q10 ) ( Q11 ) ( Q12 ) ( Q13 ) Moyenne d ' ensemble ( esp??rance ) estim??e pour chaque instant
moy_stat1 = zeros (1, nEchantillon ) ;
moy_stat2 = zeros (1, nEchantillon ) ;
for i = 1: nEchantillon
    moy_stat1 ( i ) = mean ( x1 (:, i ) ) ;
    moy_stat2 ( i ) = mean ( x2 (:, i ) ) ;
end

% ( Q15 ) ( Q16 ) ( Q17 ) ( Q18 ) Moyenne temporelle pour chaque r??alisation
moy_temp1 = zeros (1, nRealisation ) ;
moy_temp2 = zeros (1, nRealisation ) ;
for i = 1: nRealisation
    moy_temp1 ( i ) = mean ( x1 (i,:) ) ;
    moy_temp2 ( i ) = mean ( x2 (i,:) ) ;
end

% Affichage des moyennes de premier ordre
fig = figure;

plot ( temps, moy_stat1 , 'color', cmap1((10+10)*3,:)) ;
hold on ;
plot ( temps, moy_stat2 , 'color', cmap2((10+10)*3,:)) ;
title ( ' Moyennes  statistiques ') ;
xlabel ( ' temps  ( t ) ') ; ylabel ( ' m_X ( t ) ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ') ;
grid on ;

fig.PaperUnits = 'centimeters';
fig.PaperPosition = [0 0 13 1];
fig.PaperPositionMode = 'manual';
%saveas(fig,'q11','epsc');

figure;
nRea = 500 ; % Nombre de r??alisations ?? afficher
plot ( moy_temp1 (1: nRea ), 1: nRea , '+', 'color', cmap1((10+10)*3,:)) ; hold on ;
plot ( moy_temp2 (1: nRea ), 1: nRea , '.', 'color', cmap2((10+10)*3,:)) ;
title ( ' Moyennes  temporelles ') ;
xlabel ( '$$ \overline{ X(\omega)} $$ ', 'interpreter' , 'latex') ;
ylabel ( ' numero  realisation ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ' , ' Location ' , ' NorthWest ') ;
grid on

% ( Q10 ) ( Q11 ) ( Q12 ) ( Q13 ) Moyenne d ' ensemble ( esp??rance ) estim??e pour chaque instant
moy_stat1 = zeros (1, nEchantillon ) ;
moy_stat2 = zeros (1, nEchantillon ) ;
for i = 1: nEchantillon
    moy_stat1 ( i ) = mean ( x1 (:, i ) ) ;
    moy_stat2 ( i ) = mean ( x2 (:, i ) ) ;
end

% ( Q15 ) ( Q16 ) ( Q17 ) ( Q18 ) Moyenne temporelle pour chaque r??alisation
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

nRea = 500 ; % Nombre de r??alisations ?? afficher
subplot (1,2 ,2) ;
plot ( moy_temp1 (1: nRea ), 1: nRea , 'b + ') ; hold on ;
plot ( moy_temp2 (1: nRea ), 1: nRea , 'r . ') ;
title ( ' Moyennes  temporelles ') ;
xlabel ( ' $$ \ overline  { X (\ omega ) } $$ ', 'interpreter' , 'latex') ;
ylabel ( ' num??ro  r??alisation ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ' , ' Location ' , ' NorthWest ') ;
grid on

% (Q30) Fonction d'autocorr??lation statistique estim??e pour chaque paire
% d'instants (e,e+p)
% L'??cart 'p' entre  les  instants  varie  entre 0 et nPas -1
nPas = 100 ;
gamma1 = zeros (nEchantillon -nPas , nPas) ;
gamma2 = zeros (nEchantillon -nPas , nPas) ;
for e = 1: nEchantillon -nPas
 for p = 0:nPas -1
     gamma1(e,p+1) = mean( x1(:,e) .* x1(:,e+p) ) ;
     gamma2(e,p+1) = mean( x2(:,e) .* x2(:,e+p) ) ;
 end
end

%(Q35) Autocorr??lation temporelle  estim??e  sur  chaque  r??alisation ?? l'aide de la
% fonction  xcorr  de  Matlab (signal  d??terministe)
% xcorr  produit  des  autocorr??lations  sur 2* nEchantillon +1, le  d??calage  nul
% ??tant  en  position  ,nEchantillon '.
auto1 = zeros(nRealisation , 2* nEchantillon +1);

xlabel ( ' $$ \ overline  { X (\ omega ) } $$ ', 'interpreter' , 'latex') ;
ylabel ( ' num??ro  r??alisation ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ' , ' Location ' , ' NorthWest ') ;
grid on

% (Q30) Fonction d'autocorr??lation statistique estim??e pour chaque paire
% d'instants (e,e+p)
% L'??cart 'p' entre  les  instants  varie  entre 0 et nPas -1
nPas = 100 ;
gamma1 = zeros (nEchantillon -nPas , nPas) ;
gamma2 = zeros (nEchantillon -nPas , nPas) ;
for e = 1: nEchantillon -nPas
 for p = 0:nPas -1
     gamma1(e,p+1) = mean( x1(:,e) .* x1(:,e+p) ) ;
     gamma2(e,p+1) = mean( x2(:,e) .* x2(:,e+p) ) ;
 end
end

%(Q35) Autocorr??lation temporelle  estim??e  sur  chaque  r??alisation ?? l'aide de la
% fonction  xcorr  de  Matlab (signal  d??terministe)
% xcorr  produit  des  autocorr??lations  sur 2* nEchantillon +1, le  d??calage  nul
% ??tant  en  position  ,nEchantillon '.
auto1 = zeros(nRealisation , 2* nEchantillon +1);
auto2 = zeros(nRealisation , 2* nEchantillon +1) ;
for traj = 1: nRealisation
    auto1(traj ,:) = xcorr (x1(traj ,:) , nEchantillon) ;
    auto2(traj ,:) = xcorr (x2(traj ,:) , nEchantillon) ;
end

% (Q31) Affichage  sous  forme d'images  via la  fonction  Matlab  'imagesc '
% La  couleur  dans l'image  renseigne  sur les  valeurs  des  autocorr??lations
% estim??es.
figure  (30) ;
set (gcf , 'Position' , [10 10 1400  500]) ;
subplot (1,2,1) ;
imagesc(gamma1) ;
xlabel('\tau') ;
ylabel('t (numero echantillon)') ;
title('$$\Gamma_{X_1}(t,t+\tau)$$','interpreter','latex') ;
subplot (1,2,2) ;
imagesc(gamma2) ;
xlabel('\tau') ;
ylabel('t (numero echantillon)') ;
title('$$\Gamma_{X_2}(t,t+\tau)$$','interpreter','latex') ;
colormap(jet) ;

% (Q35)
figure  (31) ;
set (gcf , 'Position' , [10 10 1400  500]) ;
subplot (1,2,1) ;
imagesc(auto1 (1:50, nEchantillon:nEchantillon+nPas)) ;
title ('$$\overline {X_1(\omega ,t)X_1(\omega ,t+\tau)}$$ pour 50 r\''ealisations ','interpreter','latex') ;
xlabel('\tau') ;
ylabel('numero realisation ') 
subplot (1,2,2) ;
imagesc(auto2 (1:50, nEchantillon:nEchantillon+nPas)) ;
title ('$$\overline {X_2(\omega ,t)X_2(\omega ,t+\tau)}$$ pour 50 r\''ealisations ','interpreter','latex') ;
xlabel('\tau') ;
ylabel('numero realisation ') 