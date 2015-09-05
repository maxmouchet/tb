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
xlabel ( ' $$ \ overline  { X (\ omega ) } $$ ', ' interpreter ' , ' latex ') ;
ylabel ( ' numéro  réalisation ') ;
legend ( ' X_1 ( t ) ', ' X_2 ( t ) ' , ' Location ' , ' NorthWest ') ;
grid on